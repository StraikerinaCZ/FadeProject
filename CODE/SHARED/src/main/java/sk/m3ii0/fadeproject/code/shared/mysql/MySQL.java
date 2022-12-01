package sk.m3ii0.fadeproject.code.shared.mysql;

import sk.m3ii0.fadeproject.code.shared.user.User;

import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

public class MySQL {

    private static Timer task;
    private static Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/fade";
    private static final String USERNAME = "fade";
    private static final String PASSWORD = "fade";

    public static void prepareConnection() {

        task = new Timer();

        task.schedule(new TimerTask() {

            @Override
            public void run() {

                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                try {
                    connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }

        }, 0, 1000*60*30);

    }

    public static void prepareTables() {

        try (

                PreparedStatement users = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `users` (" +
                        "`id` INT NOT NULL AUTO_INCREMENT," +
                        "`uuid` TEXT NOT NULL," +
                        "`fuid` TEXT NOT NULL," +
                        "PRIMARY KEY (`id`)" +
                        ");");

                PreparedStatement data = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `data` (" +
                        "`id` INT NOT NULL AUTO_INCREMENT," +
                        "`fuid` TEXT NOT NULL," +
                        "`data` TEXT NOT NULL," +
                        "PRIMARY KEY (`id`)" +
                        ");");

                PreparedStatement perms = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `permissions` (" +
                        "`id` INT NOT NULL AUTO_INCREMENT," +
                        "`fuid` TEXT," +
                        "`permissions` TEXT," +
                        "PRIMARY KEY (`id`)" +
                        ");");

                PreparedStatement groups = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `groups` (" +
                        "`id` INT NOT NULL AUTO_INCREMENT," +
                        "`fuid` TEXT," +
                        "`group` TEXT," +
                        "PRIMARY KEY (`id`)" +
                        ");");

                PreparedStatement groupRegister = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `groupregister` (" +
                        "`weight` INT DEFAULT '0'," +
                        "`rawname` TEXT DEFAULT 'New group'," +
                        "`name` TEXT DEFAULT 'None'," +
                        "`prefix` TEXT DEFAULT '&7'," +
                        "`suffix` TEXT DEFAULT '&7'," +
                        "`permissions` TEXT DEFAULT '{}'," +
                        "PRIMARY KEY (`weight`)" +
                        ");")

        ) {

            users.execute();
            data.execute();
            perms.execute();
            groups.execute();
            groupRegister.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {

        if (task != null) {
            task.cancel();
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static class Tables {

        public enum Users {

            UUID("uuid"),
            KEY("key");

            Users(String a) {
                this.a = a;
            }

            private final String a;

            public String a() {
                return a;
            }

        }

        public enum Data {

            KEY("key"),
            DATA("data");

            Data(String a) {
                this.a = a;
            }

            private final String a;

            public String a() {
                return a;
            }

        }

        public enum Permissions {

            FUID("fuid"),
            PERMISSIONS("permissions");

            Permissions(String a) {
                this.a = a;
            }

            private final String a;

            public String a() {
                return a;
            }

        }

        public enum Groups {

            FUID("fuid"),
            GROUP("group");

            Groups(String a) {
                this.a = a;
            }

            private final String a;

            public String a() {
                return a;
            }

        }

        public enum GroupRegister {

            WEIGHT("weight"),
            RAW_NAME("rawname"),
            NAME("name"),
            PREFIX("prefix"),
            SUFFIX("suffix"),
            PERMISSIONS("permissions");

            GroupRegister(String a) {
                this.a = a;
            }

            private final String a;

            public String a() {
                return a;
            }

        }

    }

}