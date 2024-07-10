package controller;

import model.classes.TicketTransaction;
import model.enums.RescheduleEnum;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RescheduleController {
    ConnectionHandler conn = new ConnectionHandler();

    public boolean requestReschedule(int userID, TicketTransaction transaction, int requestedSchedule) {
        conn.connect();

        String query = "INSERT INTO reschedule_request(transaction_id) VALUES (?)";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, transaction.getTransactionID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return true;
    }

    public boolean respondRescheduleRequest(int transactionID, RescheduleEnum status, int adminID) {
        conn.connect();

        String query = "UPDATE reschedule SET status = ?, admin_id = ? WHERE transactionID = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, status.toString());
            stmt.setInt(2, adminID);
            stmt.setInt(3, transactionID);
            stmt.executeUpdate();

            //Kirim notif ke
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return true;
    }

    private boolean changeTransactionSchedule(int transactionID, int requestedSchedule) {
        conn.connect();

        String query = "UPDATE ticket_transaction SET schedule_id = ?, rescheduled = 1 WHERE transaction_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, requestedSchedule);
            stmt.setInt(2, transactionID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return true;
    }
}
