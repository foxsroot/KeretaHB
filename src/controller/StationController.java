package controller;

import model.classes.Schedule;
import model.classes.Station;
import model.classes.Train;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StationController {

    public String getStationNameById(Integer station_id){
        String query = "SELECT * FROM station WHERE station_id = '" + station_id + "'";
        String name = "";
        try{
            ConnectionHandler conn = new ConnectionHandler();
            conn.connect();
            java.sql.PreparedStatement st = conn.con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                name = rs.getString("name");
            }
            return name;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Station getStationById(int station_id) {
        Station station = null;
        TrainController trainController = new TrainController();
        ScheduleController scheduleController = new ScheduleController();
        ConnectionHandler conn = new ConnectionHandler();
        String query = "SELECT * FROM station WHERE station_id = '" + station_id + "'";
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            st.executeQuery(query);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                ArrayList<Schedule> schedules = scheduleController.getSchedulesByStationId(station_id);
                String name = rs.getString("name");
                String location = rs.getString("location");
                List<Train> trainList = trainController.getTrainByStationId(station_id);
                double income = rs.getDouble("income");

                station = new Station(schedules, name, station_id, location, (ArrayList<Train>) trainList, income);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return station;
    }
}
