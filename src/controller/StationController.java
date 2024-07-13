package controller;

import model.classes.Schedule;
import model.classes.Station;
import model.classes.Train;
import model.classes.Victual;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StationController {

    public List<Station> getlistStations() {
        List<Station> stations = new ArrayList<>();
        ScheduleController scheduleController = new ScheduleController();
        TrainController trainController = new TrainController();
        VictualController victualController = new VictualController();

        String query = "SELECT * FROM station";
        
        try {
            ConnectionHandler.getInstance().connect();
            java.sql.PreparedStatement st = ConnectionHandler.getInstance().con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("station_id");
                ArrayList<Schedule> schedules = (ArrayList<Schedule>) scheduleController.getListSchedules(id);
                HashMap<Victual, Integer> victual = victualController.listVictual(id);
                String name = rs.getString("name");
                String location = rs.getString("location");
                ArrayList<Train> trains = (ArrayList<Train>) trainController.getTrainList();
                double income = rs.getDouble("income");
                String picture = rs.getString("picture");
                Station station = new Station(id, schedules, victual, name, location, trains, income, picture);
                stations.add(station);
            }
            return stations;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return null;
    }

    public boolean addStationIncome(int stationID, double total) {
        ConnectionHandler.getInstance().connect();

        String query = "UPDATE station SET income = income + ? WHERE station_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setDouble(1, total);
            stmt.setInt(2, stationID);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
    }

    public String getStationNameById(Integer station_id) {
        String query = "SELECT * FROM station WHERE station_id = '" + station_id + "'";
        String name = "";
        
        try {
            ConnectionHandler.getInstance().connect();
            java.sql.PreparedStatement st = ConnectionHandler.getInstance().con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
            }
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
    }

    public Station getStationById(int station_id) {
        Station station = null;
        TrainController trainController = new TrainController();
        ScheduleController scheduleController = new ScheduleController();
        VictualController victualController = new VictualController();
        

        String query = "SELECT * FROM station WHERE station_id = '" + station_id + "'";
        try {
            ConnectionHandler.getInstance().connect();
            PreparedStatement st = ConnectionHandler.getInstance().con.prepareStatement(query);
            st.executeQuery(query);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Integer id = rs.getInt("station_id");
                ArrayList<Schedule> schedules = (ArrayList<Schedule>) scheduleController.getListSchedules(id);
                HashMap<Victual, Integer> victual = victualController.listVictual(id);
                String name = rs.getString("name");
                String location = rs.getString("location");
                ArrayList<Train> trains = (ArrayList<Train>) trainController.getTrainByStationId(id);
                double income = rs.getDouble("income");
                String picture = rs.getString("picture");
                station = new Station(id, schedules, victual, name, location, trains, income, picture);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return station;
    }
}
