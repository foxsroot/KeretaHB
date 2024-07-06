package controller;

import model.classes.Carriage;
import model.classes.Train;
import model.enums.CarriageType;
import model.enums.ClassType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrainController {
    public int totalTrainCapacity(int train_id) {
        Carriage[] carriages = getCarriage(train_id);
        System.out.println(carriages.length);
        int totalCapacity = 0;
        for (int i = 0; i < carriages.length; i++) {
            totalCapacity += carriages[i].getCapacity();
            System.out.println(carriages[i].getCapacity());
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

                Train train = new Train(id, getCarriage(id), speed);
                trains.add(train);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return trains;
    }


    private Train getTrainById(int train_id) {
        Train train = null;
        ConnectionHandler conn = new ConnectionHandler();

        String query = "SELECT * FROM train WHERE train_id = '" + train_id + "'";
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            st.executeQuery(query);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Carriage[] carriages = getCarriage(train_id);
                int speed = rs.getInt("speed");

                train = new Train(train_id, carriages, speed);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
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
            while (rs.next()) {
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
        }
        return carriages;
    }

    public List<Train> getTrainsByStationId(int stationId) {
        List<Train> trains = new ArrayList<>();
        String query = "SELECT * FROM train WHERE station_id = ?";
        try {
            ConnectionHandler conn = new ConnectionHandler();
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            st.setInt(1, stationId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int trainId = rs.getInt("train_id");
                int speed = rs.getInt("speed");
                Carriage[] carriages = getCarriage(trainId);

                trains.add(new Train(trainId, carriages, speed));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trains;
    }
}
