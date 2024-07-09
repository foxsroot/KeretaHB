package controller;

import model.classes.Passenger;
import model.enums.LoyaltyEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoyaltyController {
    ConnectionHandler conn = new ConnectionHandler();

    public ArrayList<LoyaltyEnum> getLoyalties() {
        conn.connect();

        String query = "SELECT * FROM loyalty";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            ArrayList<LoyaltyEnum> loyalties = new ArrayList<>();

            while (rs.next()) {
                loyalties.add(LoyaltyEnum.valueOf(rs.getString("loyalty_type")));
            }

            return loyalties;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            conn.disconnect();
        }
    }

//    public double getMinimumTransaction(LoyaltyEnum loyalty) {
//        conn.connect();
//
//        String query = "SELECT minimum_transaction FROM loyalty WHERE loyalty_type = ?";
//
//        try {
//            PreparedStatement stmt = conn.con.prepareStatement(query);
//            stmt.setString(1, loyalty.toString());
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                return rs.getDouble("minimum_transaction");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            conn.disconnect();
//        }
//
//        return 0;
//    }

    public double getLoyaltyDiscount(LoyaltyEnum loyalty) {
        conn.connect();

        String query = "SELECT discount FROM loyalty WHERE loyalty_type = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, loyalty.toString());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("discount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return 0;
    }

    public LoyaltyEnum getLoyalty(int userID) {
        conn.connect();

        String query = "SELECT loyalty.loyalty_type FROM loyalty INNER JOIN user ON user.loyalty = loyalty.loyalty_type WHERE user.user_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return LoyaltyEnum.valueOf(rs.getString("loyalty_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return null;
    }

    public boolean updateLoyaltyRules(LoyaltyEnum loyalty, double discount, double minimumTransaction) {
        conn.connect();

        String query = "UPDATE loyalty SET discount = ?, minimum_transaction = ? WHERE loyalty_type = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setDouble(1, discount);
            stmt.setDouble(2, minimumTransaction);
            stmt.setString(3, loyalty.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return true;
    }

    public boolean updateLoyalty(int userID) {
        int updatedLoyalty = checkLoyalty(getTotalPaid(userID));
        conn.connect();

        String query = "UPDATE passenger SET loyalty = ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, updatedLoyalty);
            stmt.setInt(2, userID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return true;
    }

    public boolean addTotalPaid(int userID, double total) {
        conn.connect();

        String query = "UPDATE passenger SET total_paid = total_paid + ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setDouble(1, total);
            stmt.setInt(2, userID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return updateLoyalty(userID);
    }

    private double getTotalPaid(int userID) {
        conn.connect();

        String query = "SELECT total_paid FROM passenger WHERE user_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_paid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int checkLoyalty(double totalTransaction) {
        int currentLoyalty = -1;

        conn.connect();

        String query = "SELECT * FROM loyalty";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (totalTransaction > rs.getDouble("minimum_transaction")) {
                    currentLoyalty = rs.getInt("loyalty_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return currentLoyalty;
    }
}
