package controller;

import model.classes.*;
import model.enums.CarriageType;
import model.enums.ClassType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleController {
    public static List<Schedule> getSchedulesForStation(int stationId) {
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

    private static Train getTrainById(int train_id) {
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

    private static Station getStationById(int station_id) {
        Station station = null;
        ConnectionHandler conn = new ConnectionHandler();
        String query = "SELECT * FROM station WHERE station_id = '" + station_id + "'";
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            st.executeQuery(query);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                ArrayList<Integer> schedules = null;
                String name = rs.getString("name");
                String location = rs.getString("location");
                ArrayList<String> trainList = null;
                double income = rs.getDouble("income");

                station = new Station(schedules, name, station_id, location, trainList, income);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return station;
    }

    public static Carriage[] getCarriage(int train_id) {
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
                int carriageId = rs.getInt("carriage_id");
                String carriageType = rs.getString("type");
                int capacity = rs.getInt("capacity");
                String carriageClass = rs.getString("class");
                Integer baggage = rs.getInt("baggage_allowance");

                carriages[i] = new Carriage(trainId, carriageId, CarriageType.valueOf(carriageType), capacity, baggage, ClassType.valueOf(carriageClass));
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return carriages;
    }
}
