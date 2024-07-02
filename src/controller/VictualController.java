package controller;

import model.classes.Cart;
import model.classes.Victual;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VictualController {
    ConnectionHandler conn = new ConnectionHandler();

    public List<Victual> listVictual() {
        conn.connect();
        List<Victual> victuals = new ArrayList<>();
        String query = "SELECT * FROM victual";

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Victual victual = new Victual(rs.getInt("victual_id"), rs.getString("name"), rs.getDouble("price"), rs.getInt("stock"));
                victuals.add(victual);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return victuals;
    }

    public Victual getVictual(int victualId) {
        conn.connect();
        Victual victual = null;
        String query = "SELECT * FROM victual WHERE victual_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, victualId);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                victual = new Victual(rs.getInt("victual_id"), rs.getString("name"), rs.getDouble("price"), rs.getInt("stock"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return victual;
    }

    public boolean editVictual(Victual victual) {
        conn.connect();

        String query = "UPDATE victual SET name = ?, price = ?, stock = ? WHERE victual_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, victual.getName());
            stmt.setDouble(2, victual.getPrice());
            stmt.setInt(3, victual.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return true;
    }

    public boolean addVictual(Victual victual) {
        conn.connect();
        String query = "INSERT INTO victual (name, price, stock) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, victual.getName());
            stmt.setDouble(2, victual.getPrice());
            stmt.setInt(3, victual.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return true;
    }

    public boolean removeVictual(int victualId) {
        conn.connect();
        String query = "DELETE FROM victual WHERE victual_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, victualId);
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
