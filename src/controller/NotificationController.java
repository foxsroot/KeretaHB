package controller;

import model.classes.Notification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotificationController {
    ConnectionHandler conn = new ConnectionHandler();

    public boolean sendNotification(int user_id, String message) {
        conn.connect();

        String query = "INSERT INTO notification(recipient_id, message, received_date) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, user_id);
            stmt.setString(2, message);
            stmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));

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

        String query = "SELECT * FROM notification WHERE recipient_id=?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Notification notification = new Notification(rs.getInt("notification_id"), rs.getInt("recipient_id"), rs.getString("message"), rs.getDate("received_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return notifications;
    }
}
