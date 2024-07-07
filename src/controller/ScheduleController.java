package controller;

import model.classes.*;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleController {
    public boolean deleteSchedule(Schedule schedule) {
        ConnectionHandler conn = new ConnectionHandler();
        String query = "DELETE FROM schedule WHERE schedule_id = ?";

        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            st.setInt(1, schedule.getScheduleID());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        } finally {
            conn.disconnect();
        }
    }

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

                Schedule schedule = new Schedule(train, departure, arrival, departureDate, fee).addScheduleID(scheduleId);
                schedules.add(schedule);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            conn.disconnect();
        }
        return schedules;
    }

    public Schedule getSchedulesById(int schedule_Id) {
        ConnectionHandler conn = new ConnectionHandler();

        String query = "SELECT * FROM schedule WHERE schedule_id = ?";
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            st.setInt(1, schedule_Id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int scheduleId = rs.getInt("schedule_id");
                Integer train = rs.getInt("train_id");
                Integer departure = rs.getInt("departure_station_id");
                Integer arrival = rs.getInt("arrival_station_id");
                Date departureDate = rs.getDate("departure_date");
                double fee = rs.getDouble("fee");

                return new Schedule(train, departure, arrival, departureDate, fee).addScheduleID(scheduleId);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            conn.disconnect();
        }
        return null;
    }

    public ArrayList<Schedule> getSchedulesByStationId(int stationId) {
        ConnectionHandler conn = new ConnectionHandler();
        ArrayList<Schedule> schedules = new ArrayList<>();
        String query = "SELECT * FROM schedule WHERE station_id = ?";
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

                schedules.add(new Schedule(train, departure, arrival, departureDate, fee).addScheduleID(scheduleId));
            }
            return schedules;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            conn.disconnect();
        }
        return null;
    }

    public boolean validateScheduleForm(Integer train_id, Integer departureStationID, Integer arrivalStationID, Date departureDate, double fee) {
        boolean valid = true;
        if (train_id == null) {
            JOptionPane.showMessageDialog(null, "Please select a train.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            valid = false;
        }
        if (departureStationID == null) {
            JOptionPane.showMessageDialog(null, "Departure Station ID must not be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            valid = false;
        }
        if (arrivalStationID == null) {
            JOptionPane.showMessageDialog(null, "Arrival Station ID must not be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            valid = false;
        }
        if (departureDate == null) {
            JOptionPane.showMessageDialog(null, "Departure Date must not be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            valid = false;
        }
        if (fee < 0) {
            JOptionPane.showMessageDialog(null, "Fee must not be negative.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            valid = false;
        }
        return valid;
    }

    public boolean addSchedule(Schedule schedule, boolean add) {
        ConnectionHandler conn = new ConnectionHandler();
        String query = "";
        if (add) {
            query = "INSERT INTO schedule (train_id, departure_station_id, arrival_station_id, departure_date, fee)" +
                    " VALUES (?,?,?,?,?)";
        }else{
            query = "UPDATE schedule SET train_id =?, departure_station_id =?, arrival_station_id =?, departure_date =?, fee =?" +
                    " WHERE schedule_id = '" + schedule.getScheduleID() + "'";
        }
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            st.setInt(1, schedule.getTrainID());
            st.setInt(2, schedule.getDeparture());
            st.setInt(3, schedule.getArrival());
            st.setDate(4, new java.sql.Date(schedule.getDepartureDate().getTime()));
            st.setDouble(5, schedule.getFee());
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
