package controller;

import model.classes.Carriage;
import model.classes.Train;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrainController {
    public int totalTrainCapacity(int train_id) {
        CarriageController carriageController = new CarriageController();
        Carriage[] carriages = carriageController.getCarriage(train_id);
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
            CarriageController carriageController = new CarriageController();
            while (rs.next()) {
                Integer id = rs.getInt("train_id");
                Integer station_id = rs.getInt("station_id");
                int speed = rs.getInt("speed");

                Train train = new Train(station_id, carriageController.getCarriage(id), speed).addId(id);
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
            CarriageController carriageController = new CarriageController();
            if (rs.next()) {
                Integer station_id = rs.getInt("station_id");
                Carriage[] carriages = carriageController.getCarriage((train_id));
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


    public List<Train> getTrainsByStationId(int stationId) {
        List<Train> trains = new ArrayList<>();
        String query = "SELECT * FROM train WHERE station_id = ?";
        ConnectionHandler conn = new ConnectionHandler();
        CarriageController carriageController = new CarriageController();
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            st.setInt(1, stationId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("train_id");
                Integer station_id = rs.getInt("station_id");
                int speed = rs.getInt("speed");
                Carriage[] carriages = carriageController.getCarriage(id);

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
        return stationId != null && speed > 0;
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
        if (revokeCarriagesByTrainId(train.getId())) {
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
        }
        return false;
    }

    private boolean revokeCarriagesByTrainId(Integer trainId) {
        ConnectionHandler conn = new ConnectionHandler();
        String query = "UPDATE carriage SET train_id = NULL WHERE train_id = ?";
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            st.setInt(1, trainId);
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            conn.disconnect();
        }
        return false;
    }

}
