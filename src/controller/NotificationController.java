package controller;

import model.classes.Notification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotificationController {
    ConnectionHandler conn = new ConnectionHandler();

    public boolean validateNotificationForm(String email, String title, String message) {
        if (email == null || title == null || message == null) {
            return false;
        }

        return (!email.isEmpty() && !title.isEmpty() && !message.isEmpty());
    }

    public boolean sendNotification(String email, String title, String message) {
        int user_id = getIdByEmail(email);

        if (user_id == -1) {
            return false;
        }

        conn.connect();

        String query = "INSERT INTO notification(recipient_id, title, message, received_date) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, user_id);
            stmt.setString(2, title);
            stmt.setString(3, message);
            stmt.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return true;
    }

    public ArrayList<Notification> getNotifications(int user_id) {
        conn.connect();
        ArrayList<Notification> notifications = new ArrayList<>();

        String query = "SELECT * FROM notification WHERE recipient_id=? ORDER BY received_date DESC";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Notification notification = new Notification(rs.getInt("notification_id"), rs.getInt("recipient_id"), rs.getString("title"), rs.getString("message"), rs.getTimestamp("received_date"));
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return notifications;
    }

    private int getIdByEmail(String email) {
        conn.connect();

        String query = "SELECT user_id FROM passenger WHERE email=?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            conn.disconnect();
        }

        return -1;
    }
}
