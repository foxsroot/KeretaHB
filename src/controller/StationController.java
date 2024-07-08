package controller;

import model.classes.Schedule;
import model.classes.Station;
import model.classes.Train;
import model.classes.Victual;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        ConnectionHandler conn = new ConnectionHandler();
        try {
            conn.connect();
            java.sql.PreparedStatement st = conn.con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("station_id");
                ArrayList<Schedule> schedules = (ArrayList<Schedule>) scheduleController.getListSchedules(id);
                HashMap<Victual, Integer> victual = victualController.listVictual(id);
                String name = rs.getString("name");
                String location = rs.getString("location");
                ArrayList<Train> trains = (ArrayList<Train>) trainController.getTrainList();
                double income = rs.getDouble("income");
                Station station = new Station(id, schedules, victual, name, location, trains, income);
                stations.add(station);
            }
            return stations;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return null;
    }

    public String getStationNameById(Integer station_id) {
        String query = "SELECT * FROM station WHERE station_id = '" + station_id + "'";
        String name = "";
        ConnectionHandler conn = new ConnectionHandler();
        try {
            conn.connect();
            java.sql.PreparedStatement st = conn.con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
            }
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            conn.disconnect();
        }
    }

    public Station getStationById(int station_id) {
        Station station = null;
        TrainController trainController = new TrainController();
        ScheduleController scheduleController = new ScheduleController();
        VictualController victualController = new VictualController();
        ConnectionHandler conn = new ConnectionHandler();

        String query = "SELECT * FROM station WHERE station_id = '" + station_id + "'";
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            st.executeQuery(query);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Integer id = rs.getInt("station_id");
                ArrayList<Schedule> schedules = (ArrayList<Schedule>) scheduleController.getListSchedules(id);
                HashMap<Victual, Integer> victual = victualController.listVictual(id);
                String name = rs.getString("name");
                String location = rs.getString("location");
                ArrayList<Train> trains = (ArrayList<Train>) trainController.getTrainsByStationId(id);

                double income = rs.getDouble("income");
                station = new Station(id, schedules, victual, name, location, trains, income);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            conn.disconnect();
        }
        return station;
    }
}
