package controller;

import model.classes.Notification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotificationController {
    

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

        ConnectionHandler.getInstance().connect();

        String query = "INSERT INTO notification(recipient_id, title, message) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, user_id);
            stmt.setString(2, title);
            stmt.setString(3, message);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
    }

    public ArrayList<Notification> getNotifications(int user_id) {
        ConnectionHandler.getInstance().connect();
        ArrayList<Notification> notifications = new ArrayList<>();

        String query = "SELECT * FROM notification WHERE recipient_id=? ORDER BY received_date DESC";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Notification notification = new Notification(rs.getInt("notification_id"), rs.getInt("recipient_id"), rs.getString("title"), rs.getString("message"), rs.getTimestamp("received_date"));
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return notifications;
    }

    public boolean sendNotificationByID(int userID, String title, String message) {
        ConnectionHandler.getInstance().connect();

        String query = "INSERT INTO notification(recipient_id, title, message) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, userID);
            stmt.setString(2, title);
            stmt.setString(3, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
    }

    private int getIdByEmail(String email) {
        ConnectionHandler.getInstance().connect();

        String query = "SELECT user_id FROM passenger WHERE email=?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return -1;
    }
}
