package sk.m3ii0.fadeproject.code.shared.permissions;

import sk.m3ii0.fadeproject.code.shared.fuid.FUID;
import sk.m3ii0.fadeproject.code.shared.mysql.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Group {

    private static final Map<String, Group> cache = new HashMap<>();

    public static Group getByName(String group) {
        return cache.get(group);
    }

    public static Group getByPlayer(FUID fuid) {

        Group result = null;

        try (
                PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * FROM groups WHERE fuid='" + fuid + "'");
                ResultSet set = statement.executeQuery()
        ) {

            while (set.next()) {
                result = getByName(set.getString("group"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void refreshGroups() {

        cache.clear();

        try (
                PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * FROM groupregister");
                ResultSet set = statement.executeQuery()
        ) {

            while (set.next()) {
                int weight = set.getInt("weight");
                String rawname = set.getString("rawname");
                String name = set.getString("name");
                String prefix = set.getString("prefix");
                String suffix = set.getString("suffix");
                String permissions = set.getString("permissions");
                cache.put(rawname, new Group(weight, rawname, name, prefix, suffix, permissions));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
    *
    *
    */

    private final Map<String, Boolean> permissions;

    private final int weight;
    private final String rawName;
    private final String displayName;
    private final String prefix;
    private final String suffix;

    public Group(int weight, String rawName, String displayName, String prefix, String suffix, String rawPermissions) {

        this.weight = weight;
        this.rawName = rawName;
        this.displayName = displayName;
        this.prefix = prefix;
        this.suffix = suffix;

        rawPermissions = rawPermissions.replace("{", "").replace("}", "");

        permissions = new HashMap<>();
        if (rawPermissions.isEmpty()) {
            return;
        }

        for (String rawData : rawPermissions.split(",")) {
            String dataKey = rawData.split("->")[0];
            boolean dataValue = Boolean.parseBoolean(rawData.split("->")[1]);
            if (dataKey.contains("group.")) {
                String group = dataKey.split("group.")[0];
                Group var = Group.getByName(group);
                for (String perm : var.getPermissions().keySet()) {
                    if (dataValue) {
                        permissions.put(perm, var.getPermissions().get(perm));
                    } else {
                        permissions.put(perm, false);
                    }
                }
            }
            permissions.put(dataKey, dataValue);
        }

    }

    public Map<String, Boolean> getPermissions() {
        return permissions;
    }

    public String permissionsToString() {

        String value = "{";

        for (String key : permissions.keySet()) {
            boolean var = permissions.get(key);
            String finalData = key + "->" + var;
            value += finalData + ",";
        }

        value = value.replaceAll(",$", "");
        value += "}";

        return value;
    }

    public int getWeight() {
        return weight;
    }

    public String getRawName() {
        return rawName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

}
