package controller;

import model.classes.Passenger;
import model.enums.LoyaltyEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoyaltyController {
    ConnectionHandler conn = new ConnectionHandler();

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

    public boolean updateLoyaltyDiscount(LoyaltyEnum loyalty, boolean discount) {
        conn.connect();

        String query = "UPDATE loyalty SET discount = ? WHERE loyalty_type = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setBoolean(1, discount);
            stmt.setString(2, loyalty.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return true;
    }

    public boolean updateLoyalty(Passenger passenger) {
        int updatedLoyalty = checkLoyalty(passenger.getTotalPaid());
        conn.connect();

        String query = "UPDATE passenger SET loyalty = ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, updatedLoyalty);
            stmt.setInt(2, passenger.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return true;
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
