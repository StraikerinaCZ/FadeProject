package sk.m3ii0.fadeproject.code.shared.mysql;

import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

public class MySQL {

    private static Timer task;
    private static Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/database";
    private static final String USERNAME = "name";
    private static final String PASSWORD = "password";

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

        public enum Users { // || UUID | KEY ||

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

    }

}