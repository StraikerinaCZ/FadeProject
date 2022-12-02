package sk.m3ii0.fadeproject.code.shared.permissions;

public class PermissionWorth {

    private final boolean enabled;
    private final PermissionTime permissionTime;
    private final String world;

    public static PermissionWorth create(boolean enabled) {
        return new PermissionWorth(enabled, new PermissionTime(0, 0, 0, 0, 0, 0), "");
    }

    public static PermissionWorth create(PermissionTime permissionTime) {
        return new PermissionWorth(true, permissionTime, "");
    }

    public static PermissionWorth create(String world) {
        return new PermissionWorth(true, new PermissionTime(0, 0, 0, 0, 0, 0), world);
    }

    public static PermissionWorth create(PermissionTime permissionTime, String world) {
        return new PermissionWorth(true, permissionTime, world);
    }

    public static PermissionWorth create(boolean enabled, PermissionTime permissionTime) {
        return new PermissionWorth(enabled, permissionTime, "");
    }

    public static PermissionWorth create(boolean enabled, String world) {
        return new PermissionWorth(enabled, new PermissionTime(0, 0, 0, 0, 0, 0), world);
    }

    public static PermissionWorth create(boolean enabled, PermissionTime permissionTime, String world) {
        return new PermissionWorth(enabled, permissionTime, world);
    }

    private PermissionWorth(boolean enabled, PermissionTime permissionTime, String world) {
        this.enabled = enabled;
        this.permissionTime = permissionTime;
        this.world = world;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public PermissionTime getPermissionTime() {
        return permissionTime;
    }

    public String getWorld() {
        return world;
    }

    @Override
    public String toString() {
        return enabled + "<time=" + permissionTime.getFormatted() + ";world=" + world + ">";
    }

    public static PermissionWorth fromString(String var, boolean var1) {
        String data = var.replace("<", "").replace(">", "");
        String world = "";
        PermissionTime time = new PermissionTime(0, 0, 0, 0, 0, 0);
        for (String loop : data.split(";")) {
            String key = loop.split("=")[0];
            String value = loop.split("=")[1];
            if (key.equals("time")) {
                time = PermissionTime.fromFormatted(value);
            }
            if (key.equals("world")) {
                world = value;
            }
        }
        return PermissionWorth.create(var1, time, world);
    }

}
