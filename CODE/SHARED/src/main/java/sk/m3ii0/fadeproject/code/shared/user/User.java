package sk.m3ii0.fadeproject.code.shared.user;

import sk.m3ii0.fadeproject.code.shared.fuid.FUID;
import sk.m3ii0.fadeproject.code.shared.mysql.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {

    private static final Map<UUID, User> cache = new HashMap<>();

    public static User parseLogin(UUID uuid) {

        String backup = FUID.getNew().get();

        try (
                PreparedStatement createUser = MySQL.getConnection().prepareStatement("INSERT INTO users VALUES ('0', '" + uuid + "', '" + backup + "');");
                PreparedStatement result = MySQL.getConnection().prepareStatement("SELECT * FROM users WHERE uuid='" + uuid + "';");
                ResultSet set = result.executeQuery()) {

            String key;

            if (!set.next()) {
                key = backup;
                createUser.execute();
            } else {
                key = set.getString("fuid");
            }

            try (
                    PreparedStatement createRow = MySQL.getConnection().prepareStatement("INSERT INTO data (id, fuid, data) VALUES ('0', '" + key + "', '{}');");
                    PreparedStatement dataCommand = MySQL.getConnection().prepareStatement("SELECT * FROM data WHERE fuid='" + key + "';");
                    ResultSet data = dataCommand.executeQuery()) {

                if (data.next()) {

                    String raw = data.getString("data");

                    raw = raw.replace("{", "").replace("}", "");

                    if (raw.isEmpty()) {
                        return new User(uuid, FUID.getByExisting(key), new HashMap<>());
                    }

                    Map<String, String> formatted = new HashMap<>();

                    for (String rawData : raw.split(",")) {
                        String dataKey = rawData.split("->")[0];
                        String dataValue = rawData.split("->")[1];
                        formatted.put(dataKey, dataValue);
                    }

                    return new User(uuid, FUID.getByExisting(key), formatted);
                }

                createRow.execute();

                return new User(uuid, FUID.getByExisting(key), new HashMap<>());

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void parseQuit(UUID uuid, User user) {
        try (PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE data SET fuid='" + user.getFuid().get() + "', data='" + user.customDataToString() + "' WHERE fuid='" + user.getFuid().get() + "';")) {
            preparedStatement.execute();
            cache.remove(uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isOnline(UUID uuid) {
        return cache.containsKey(uuid);
    }

    public static User getUser(UUID uuid) {
        return cache.get(uuid);
    }

    /*
    *
    *
    *
    */

    private final FUID fuid;
    private final Map<String, String> customData;

    private User(UUID uuid, FUID fuid, Map<String, String> data) {

        this.fuid = fuid;
        this.customData = data;

        cache.put(uuid, this);

    }

    public FUID getFuid() {
        return fuid;
    }

    public void setData(String key, String value) {
        customData.put(key, value);
    }

    public boolean hasData(String key) {
        return customData.containsKey(key);
    }

    public String getData(String key) {
        return customData.get(key);
    }

    public String customDataToString() {

        String value = "{";

        for (String key : customData.keySet()) {
            String var = customData.get(key);
            String finalData = key + "->" + var;
            value += finalData + ",";
        }

        value = value.replaceAll(",$", "");
        value += "}";

        return value;
    }

}
