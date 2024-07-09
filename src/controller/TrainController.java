package controller;

import model.classes.Carriage;
import model.classes.Train;
import model.enums.CarriageType;
import model.enums.ClassType;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrainController {
    public int totalTrainCapacity(int train_id) {
        Carriage[] carriages = getCarriage(train_id);
        int totalCapacity = 0;
        if (carriages[0] != null) {
            for (int i = 0; i < carriages.length; i++) {
                totalCapacity += carriages[i].getCapacity();
            }
        }
        return totalCapacity;
    }

    public List<Train> getTrainByStationId(Integer station_id) {
        List<Train> trains = new ArrayList<>();
        ConnectionHandler conn = new ConnectionHandler();
        String query = "SELECT * FROM train WHERE station_id ='" + station_id + "'";
        return getTrains(trains, conn, query);
    }

    public List<Train> getTrainList() {
        List<Train> trains = new ArrayList<>();
        ConnectionHandler conn = new ConnectionHandler();
        String query = "SELECT * FROM train";
        return getTrains(trains, conn, query);
    }

    private List<Train> getTrains(List<Train> trains, ConnectionHandler conn, String query) {
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("train_id");
                Integer station_id = rs.getInt("station_id");
                int speed = rs.getInt("speed");

                Train train = new Train(station_id, getCarriage(id), speed).addId(id);
                trains.add(train);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            conn.disconnect();
        }
        return trains;
    }


    public Train getTrainById(int train_id) {
        Train train = null;
        ConnectionHandler conn = new ConnectionHandler();

        String query = "SELECT * FROM train WHERE train_id = '" + train_id + "'";
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            st.executeQuery(query);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Integer station_id = rs.getInt("station_id");
                Carriage[] carriages = getCarriage(train_id);
                int speed = rs.getInt("speed");

                train = new Train(station_id, carriages, speed).addId(train_id);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            conn.disconnect();
        }
        return train;
    }

    public Carriage[] getCarriage(int train_id) {
        Carriage[] carriages = new Carriage[5];
        ConnectionHandler conn = new ConnectionHandler();
        String query = "SELECT * FROM carriage where train_id = '" + train_id + "'";
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            int i = 0;
            while (rs.next() && i < 5) {
                Integer trainId = rs.getInt("train_id");
                String carriageType = rs.getString("type");
                int capacity = rs.getInt("capacity");
                String carriageClass = rs.getString("class");
                Integer baggage = rs.getInt("baggage_allowance");

                carriages[i] = new Carriage(trainId, capacity, CarriageType.valueOf(carriageType), baggage, ClassType.valueOf(carriageClass));
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            conn.disconnect();
        }
        return carriages;
    }

    public Carriage[] getUnassignedCarriages() {
        Carriage[] carriages = new Carriage[5];
        String query = "SELECT * FROM carriage WHERE train_id = '" + 0 + "'";
        ConnectionHandler conn = new ConnectionHandler();
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            for (int i = 0; i < carriages.length; i++) {
                String carriageType = rs.getString("type");
                int capacity = rs.getInt("capacity");
                String carriageClass = rs.getString("class");
                Integer baggage = rs.getInt("baggage_allowance");

                carriages[i] = new Carriage(0, capacity, CarriageType.valueOf(carriageType), baggage, ClassType.valueOf(carriageClass));
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            conn.disconnect();
        }
        return carriages;
    }

    public List<Train> getTrainsByStationId(int stationId) {
        List<Train> trains = new ArrayList<>();
        String query = "SELECT * FROM train WHERE station_id = ?";
        ConnectionHandler conn = new ConnectionHandler();
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            st.setInt(1, stationId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("train_id");
                Integer station_id = rs.getInt("station_id");
                int speed = rs.getInt("speed");
                Carriage[] carriages = getCarriage(id);

                trains.add(new Train(station_id, carriages, speed).addId(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return trains;
    }

    public boolean validateTrainForm(Integer stationId, int speed) {
        boolean valid = true;
        if (stationId == null) {
            JOptionPane.showMessageDialog(null, "Please insert Station ID.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            valid = false;
        }
        if (speed < 0) {
            JOptionPane.showMessageDialog(null, "Please insert the correct Train Speed.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            valid = false;
        }
        return valid;
    }

    public boolean addTrain(Train newTrain, boolean add) {
        String query = "";
        if (add) {
            query = "INSERT INTO train(station_id, speed) VALUES(?,?)";
        } else {
            query = "UPDATE train SET station_id =?, speed =? WHERE train_id =?";
        }
        ConnectionHandler conn = new ConnectionHandler();
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            st.setInt(1, newTrain.getStationId());
            st.setInt(2, newTrain.getSpeed());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return false;
    }

    public boolean deleteTrain(Train train) {
        String query = "DELETE FROM train WHERE train_id =?";
        ConnectionHandler conn = new ConnectionHandler();
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            st.setInt(1, train.getId());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return false;
    }
}
