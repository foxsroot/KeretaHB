package controller;

import model.classes.Carriage;
import model.enums.CarriageType;
import model.enums.ClassType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CarriageController {
    public boolean revokeCarriageFromTrain(Integer carriageId, Integer trainId) {
        String query = "UPDATE carriage SET train_id = NULL WHERE carriage_id =? AND train_id =?";
        try {
            ConnectionHandler.getInstance().connect();
            PreparedStatement st = ConnectionHandler.getInstance().con.prepareStatement(query);
            st.setInt(1, carriageId);
            st.setInt(2, trainId);
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return false;
    }

    public boolean assignCarriageToTrain(Integer carriageId, Integer trainId) {
        String query = "UPDATE carriage SET train_id =? WHERE carriage_id =?";
        try {
            ConnectionHandler.getInstance().connect();
            PreparedStatement st = ConnectionHandler.getInstance().con.prepareStatement(query);
            st.setInt(1, trainId);
            st.setInt(2, carriageId);
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return false;
    }

    public boolean validateCarriageForm(String type, Integer capacity, String carriageClass, Integer baggageAllowance) {
        return !type.isEmpty() && capacity != null && !carriageClass.isEmpty() && baggageAllowance != null;
    }

    public boolean addCarriage(Carriage carriage, boolean add) {
        String query;
        if (add) {
            query = "INSERT INTO carriage (type, capacity, `class`, baggage_allowance) VALUES (?,?,?,?)";
        } else {
            query = "UPDATE carriage SET type = ?, capacity = ?, `class` = ?, baggage_allowance =? WHERE carriage_id =?";
        }
        try {
            ConnectionHandler.getInstance().connect();
            PreparedStatement st = ConnectionHandler.getInstance().con.prepareStatement(query);
            st.setString(1, carriage.getType().toString());
            st.setInt(2, carriage.getCapacity());
            st.setString(3, carriage.getCarriageClass().toString());
            st.setInt(4, carriage.getBaggageAllowance());
            if (!add) {
                st.setInt(5, carriage.getId());
            }
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return false;
    }

    public Carriage[] getSeatingCarriage(int train_id) {
        List<Carriage> carriagesList = new ArrayList<>();
        String query = "SELECT * FROM carriage WHERE train_id = ? AND type = 'SEATING'";
        try {
            ConnectionHandler.getInstance().connect();
            PreparedStatement st = ConnectionHandler.getInstance().con.prepareStatement(query);
            st.setInt(1, train_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("carriage_id");
                String carriageType = rs.getString("type");
                int capacity = rs.getInt("capacity");
                String carriageClass = rs.getString("class");
                Integer baggage = rs.getInt("baggage_allowance");

                Carriage carriage = new Carriage(capacity, CarriageType.valueOf(carriageType), baggage, ClassType.valueOf(carriageClass))
                        .addTrainId(train_id)
                        .addId(id);
                carriagesList.add(carriage);
            }
            Carriage[] carriagesArray = new Carriage[carriagesList.size()];
            return carriagesList.toArray(carriagesArray);

        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return null;
    }

    public Carriage[] getCarriage(int train_id) {
        List<Carriage> carriagesList = new ArrayList<>();
        String query = "SELECT * FROM carriage WHERE train_id = ?";
        try {
            ConnectionHandler.getInstance().connect();
            PreparedStatement st = ConnectionHandler.getInstance().con.prepareStatement(query);
            st.setInt(1, train_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("carriage_id");
                String carriageType = rs.getString("type");
                int capacity = rs.getInt("capacity");
                String carriageClass = rs.getString("class");
                Integer baggage = rs.getInt("baggage_allowance");

                Carriage carriage = new Carriage(capacity, CarriageType.valueOf(carriageType), baggage, ClassType.valueOf(carriageClass))
                        .addTrainId(train_id)
                        .addId(id);
                carriagesList.add(carriage);
            }
            Carriage[] carriagesArray = new Carriage[carriagesList.size()];
            return carriagesList.toArray(carriagesArray);

        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return null;
    }


    public List<Carriage> getUnassignedCarriages() {
        List<Carriage> carriages = new ArrayList<>();
        String query = "SELECT * FROM carriage WHERE train_id IS NULL";
        try {
            ConnectionHandler.getInstance().connect();
            PreparedStatement st = ConnectionHandler.getInstance().con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("carriage_id");
                String carriageType = rs.getString("type");
                int capacity = rs.getInt("capacity");
                String carriageClass = rs.getString("class");
                Integer baggage = rs.getInt("baggage_allowance");

                Carriage carriage = new Carriage(capacity, CarriageType.valueOf(carriageType), baggage, ClassType.valueOf(carriageClass));
                carriage.setId(id);
                carriage.setTrain_id(0);
                carriages.add(carriage);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return carriages;
    }

}
