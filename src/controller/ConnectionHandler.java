package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHandler {
    private static String server = "jdbc:mysql://localhost/ktp_db";
    private static String username = "root";
    private static String password = "";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            connection = logOn();
        }
        return connection;
    }

    private static Connection logOn() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connection Success");
            return DriverManager.getConnection(server, username, password);
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            System.out.println("Connection Failed" + e.toString());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(System.err);
            System.out.println("JDBC.ODBC driver not found");
        }
        return null;
    }

    private static void logOff() {
        try {
            connection.close();
            System.out.println("Connection Closed");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}

