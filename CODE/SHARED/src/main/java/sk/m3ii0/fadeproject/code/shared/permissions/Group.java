package sk.m3ii0.fadeproject.code.shared.permissions;

import java.util.HashMap;
import java.util.Map;

public class Group {

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
