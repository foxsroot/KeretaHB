package controller;

import model.classes.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleController {
    public List<Schedule> getListSchedules(int stationId) {
        List<Schedule> schedules = new ArrayList<>();
        ConnectionHandler conn = new ConnectionHandler();

        String query = "SELECT * FROM schedule WHERE departure_station_id = ?";
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            st.setInt(1, stationId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int scheduleId = rs.getInt("schedule_id");
                Integer train = rs.getInt("train_id");
                Integer departure = rs.getInt("departure_station_id");
                Integer arrival = rs.getInt("arrival_station_id");
                Date departureDate = rs.getDate("departure_date");
                double fee = rs.getDouble("fee");

                Schedule schedule = new Schedule(scheduleId, train, departure, arrival, departureDate, fee);
                schedules.add(schedule);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            conn.disconnect();
        }
        return schedules;
    }

    public Schedule getSchedulesById(int stationId) {
        ConnectionHandler conn = new ConnectionHandler();

        String query = "SELECT * FROM schedule WHERE schedule_id = ?";
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            st.setInt(1, stationId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int scheduleId = rs.getInt("schedule_id");
                Integer train = rs.getInt("train_id");
                Integer departure = rs.getInt("departure_station_id");
                Integer arrival = rs.getInt("arrival_station_id");
                Date departureDate = rs.getDate("departure_date");
                double fee = rs.getDouble("fee");

                return new Schedule(scheduleId, train, departure, arrival, departureDate, fee);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            conn.disconnect();
        }
        return null;
    }
//
//    private static Train getTrainById(int train_id) {
//        Train train = null;
//        ConnectionHandler conn = new ConnectionHandler();
//
//        String query = "SELECT * FROM train WHERE train_id = '" + train_id + "'";
//        try {
//            conn.connect();
//            PreparedStatement st = conn.con.prepareStatement(query);
//            st.executeQuery(query);
//            ResultSet rs = st.executeQuery();
//
//            if (rs.next()) {
//                Carriage[] carriages = getCarriage(train_id);
//                int speed = rs.getInt("speed");
//
//                train = new Train(train_id, carriages, speed);
//            }
//        } catch (Exception e) {
//            e.printStackTrace(System.err);
//        }
//        return train;
//    }
//
//    private static Station getStationById(int station_id) {
//        Station station = null;
//        ConnectionHandler conn = new ConnectionHandler();
//        String query = "SELECT * FROM station WHERE station_id = '" + station_id + "'";
//        try {
//            conn.connect();
//            PreparedStatement st = conn.con.prepareStatement(query);
//            st.executeQuery(query);
//            ResultSet rs = st.executeQuery();
//
//            if (rs.next()) {
//                ArrayList<Integer> schedules = null;
//                String name = rs.getString("name");
//                String location = rs.getString("location");
//                ArrayList<String> trainList = null;
//                double income = rs.getDouble("income");
//
//                station = new Station(schedules, name, station_id, location, trainList, income);
//            }
//        } catch (Exception e) {
//            e.printStackTrace(System.err);
//        }
//        return station;
//    }
//
}
