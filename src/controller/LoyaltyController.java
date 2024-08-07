package controller;

import model.enums.LoyaltyEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoyaltyController {
    public ArrayList<LoyaltyEnum> getLoyalties() {
        ConnectionHandler.getInstance().connect();

        String query = "SELECT * FROM loyalty";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
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
            ConnectionHandler.getInstance().disconnect();
        }
    }

//    public double getMinimumTransaction(LoyaltyEnum loyalty) {
//        ConnectionHandler.getInstance().connect();
//
//        String query = "SELECT minimum_transaction FROM loyalty WHERE loyalty_type = ?";
//
//        try {
//            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
//            stmt.setString(1, loyalty.toString());
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                return rs.getDouble("minimum_transaction");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            ConnectionHandler.getInstance().disconnect();
//        }
//
//        return 0;
//    }

    public double getLoyaltyDiscount(LoyaltyEnum loyalty) {
        ConnectionHandler.getInstance().connect();

        String query = "SELECT discount FROM loyalty WHERE loyalty_type = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setString(1, loyalty.toString());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("discount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return 0;
    }

    public LoyaltyEnum getLoyalty(int userID) {
        ConnectionHandler.getInstance().connect();

        String query = "SELECT loyalty.loyalty_type FROM loyalty INNER JOIN passenger ON passenger.loyalty = loyalty.loyalty_type WHERE passenger.user_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return LoyaltyEnum.valueOf(rs.getString("loyalty_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return null;
    }

    public boolean updateLoyaltyDiscount(LoyaltyEnum loyalty, String discount) {
        double discountValue;

        try {
            discountValue = Double.parseDouble(discount);
        } catch (NumberFormatException e) {
            return false;
        }

        ConnectionHandler.getInstance().connect();

        String query = "UPDATE loyalty SET discount = ? WHERE loyalty_type = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setDouble(1, discountValue);
            stmt.setString(2, loyalty.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
    }

    public boolean updateLoyalty(int userID) {
        int updatedLoyalty = checkLoyalty(getTotalPaid(userID));
        ConnectionHandler.getInstance().connect();

        String query = "UPDATE passenger SET loyalty = ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, updatedLoyalty);
            stmt.setInt(2, userID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
    }

    public boolean addTotalPaid(int userID, double total) {
        ConnectionHandler.getInstance().connect();

        String query = "UPDATE passenger SET total_paid = total_paid + ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setDouble(1, total);
            stmt.setInt(2, userID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return updateLoyalty(userID);
    }

    private double getTotalPaid(int userID) {
        ConnectionHandler.getInstance().connect();

        String query = "SELECT total_paid FROM passenger WHERE user_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
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

        ConnectionHandler.getInstance().connect();

        String query = "SELECT * FROM loyalty";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (totalTransaction > rs.getDouble("minimum_transaction")) {
                    currentLoyalty = rs.getInt("loyalty_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return currentLoyalty;
    }
}
