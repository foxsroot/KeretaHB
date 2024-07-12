package controller;

import model.classes.Passenger;
import model.classes.RescheduleRequest;
import model.classes.TicketTransaction;
import model.enums.RescheduleEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RescheduleController {
    public boolean checkRequestReschedule(int transactionID) {
        ConnectionHandler.getInstance().connect();

        String query = "SELECT * FROM reschedule_request WHERE transaction_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, transactionID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return false;
    }

    public ArrayList<RescheduleRequest> getRescheduleRequestList() {
        ConnectionHandler.getInstance().connect();
        ArrayList<RescheduleRequest> rescheduleRequestList = new ArrayList<>();

        String query = "SELECT * FROM reschedule_request WHERE status = 'PENDING'";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RescheduleRequest rescheduleRequest = new RescheduleRequest(rs.getInt("reschedule_id"), rs.getInt("transaction_id"), rs.getInt("requested_schedule_id"), RescheduleEnum.valueOf(rs.getString("status")));
                rescheduleRequestList.add(rescheduleRequest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return rescheduleRequestList;
    }

    public boolean requestReschedule(int transactionID, int requestedSchedule) {
        ConnectionHandler.getInstance().connect();

        String query = "INSERT INTO reschedule_request(transaction_id, requested_schedule_id) VALUES (?, ?)";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, transactionID);
            stmt.setInt(2, requestedSchedule);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
    }

    public boolean respondRescheduleRequest(TicketTransaction transaction, String email, RescheduleEnum status, int adminID, int requestedScheduleID) {
        ConnectionHandler.getInstance().connect();

        String query = "UPDATE reschedule_request SET status = ?, admin_id = ? WHERE transaction_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setString(1, status.toString());
            stmt.setInt(2, adminID);
            stmt.setInt(3, transaction.getTransactionID());
            stmt.executeUpdate();

            NotificationController controller = new NotificationController();
            if (status.equals(RescheduleEnum.SUCCESS)) {
                if (!updateSeatCapacity(transaction, requestedScheduleID)) {
                    return false;
                }

                if (changeTransactionSchedule(transaction, requestedScheduleID)) {
                    controller.sendNotification(email, "Reschedule Request Approved", "Hi there, We are pleased to inform you that your request to reschedule your appointment has been approved");
                }
            } else {
                controller.sendNotification(email, "Reschedule Request Declined", "We regret to inform you that your request to reschedule your appointment has been declined. Due to several reasons, we are unable to accommodate your requested change at this time.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
    }

    private int getCarriageID(String classType, int scheduleID) {
        ConnectionHandler.getInstance().connect();

        String query = "SELECT schedule_capacity.carriage_id FROM schedule_capacity JOIN carriage on schedule_capacity.carriage_id = carriage.carriage_id WHERE carriage.class = ? AND schedule_capacity.schedule_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setString(1, classType);
            stmt.setInt(2, scheduleID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("carriage_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private boolean updateSeatCapacity(TicketTransaction transaction, int requestedScheduleID) {
        int oldCarriageID = getCarriageID(transaction.getType().toString(), transaction.getTransactionID());
        int newCarriageID = getCarriageID(transaction.getType().toString(), requestedScheduleID);

        ConnectionHandler.getInstance().connect();

        String updateOldCarriageQuery = "UPDATE schedule_capacity SET occupied = occupied - ? WHERE schedule_id = ? AND carriage_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(updateOldCarriageQuery);
            stmt.setInt(1, transaction.getPassengers());
            stmt.setInt(2, transaction.getScheduleID());
            stmt.setInt(3, oldCarriageID);
            stmt.executeUpdate();

            String updateNewCarriageQuery = "UPDATE schedule_capacity SET occupied = occupied + ? WHERE schedule_id = ? AND carriage_id = ?";
            try {
                PreparedStatement stmt2 = ConnectionHandler.getInstance().con.prepareStatement(updateNewCarriageQuery);
                stmt2.setInt(1, transaction.getPassengers());
                stmt2.setInt(2, requestedScheduleID);
                stmt2.setInt(3, newCarriageID);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
    }

    private boolean changeTransactionSchedule(TicketTransaction transaction, int requestedScheduleID) {
        if (!updateSeatCapacity(transaction, requestedScheduleID)) {
            return false;
        }

        ConnectionHandler.getInstance().connect();

        String query = "UPDATE ticket_transaction SET schedule_id = ?, rescheduled = 1 WHERE transaction_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, requestedScheduleID);
            stmt.setInt(2, transaction.getTransactionID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
    }
}
