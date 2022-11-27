package sk.m3ii0.fadeproject.code.shared;

import sk.m3ii0.fadeproject.code.shared.mysql.MySQL;

public class CodeShared {

    public static void loadShared() {

        // Enable MySQL
        MySQL.prepareConnection();

    }

    public static void unloadShared() {

        // Disable MySQL
        MySQL.closeConnection();

    }

}
