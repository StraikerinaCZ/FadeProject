package sk.m3ii0.fadeproject.code.shared.permissions;

public class PermissionTime {

    private final String formatted;

    public PermissionTime(int year, int month, int day, int hour, int minute, int second) {
        formatted = year + ":" + month + ":" + day + ":" + hour + ":" + minute + ":" + second;
    }

    public String getFormatted() {
        return formatted;
    }

    public static PermissionTime fromFormatted(String data) {
        String[] splitted = data.split(":");
        return new PermissionTime(i(splitted[0]), i(splitted[1]), i(splitted[2]), i(splitted[3]), i(splitted[4]), i(splitted[5]));
    }

    private static int i(String parse) {
        return Integer.parseInt(parse);
    }

}
