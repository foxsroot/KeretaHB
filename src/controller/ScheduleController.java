package controller;

import model.classes.*;
import model.enums.CarriageType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleController {
    public boolean deleteSchedule(Schedule schedule) {
        String query = "DELETE FROM schedule WHERE schedule_id = ?";

        try {
            ConnectionHandler.getInstance().connect();
            PreparedStatement st = ConnectionHandler.getInstance().con.prepareStatement(query);
            st.setInt(1, schedule.getScheduleID());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
    }

    public List<Schedule> getListSchedules(int stationId) {
        List<Schedule> schedules = new ArrayList<>();

        String query = "SELECT * FROM schedule WHERE departure_station_id = ?";
        try {
            ConnectionHandler.getInstance().connect();
            PreparedStatement st = ConnectionHandler.getInstance().con.prepareStatement(query);
            st.setInt(1, stationId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int scheduleId = rs.getInt("schedule_id");
                Integer train = rs.getInt("train_id");
                Integer departure = rs.getInt("departure_station_id");
                Integer arrival = rs.getInt("arrival_station_id");
                Timestamp departureDate = rs.getTimestamp("departure_date");
                double fee = rs.getDouble("fee");

                Schedule schedule = new Schedule(train, departure, arrival, departureDate, fee).addScheduleID(scheduleId);
                schedules.add(schedule);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return schedules;
    }

    public Schedule getSchedulesById(int schedule_Id) {
        String query = "SELECT * FROM schedule WHERE schedule_id = ?";
        try {
            ConnectionHandler.getInstance().connect();
            PreparedStatement st = ConnectionHandler.getInstance().con.prepareStatement(query);
            st.setInt(1, schedule_Id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int scheduleId = rs.getInt("schedule_id");
                Integer train = rs.getInt("train_id");
                Integer departure = rs.getInt("departure_station_id");
                Integer arrival = rs.getInt("arrival_station_id");
                Timestamp departureDate = rs.getTimestamp("departure_date");
                double fee = rs.getDouble("fee");

                return new Schedule(train, departure, arrival, departureDate, fee).addScheduleID(scheduleId);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return null;
    }

    public ArrayList<Schedule> getSchedulesByStationId(int stationId) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        String query = "SELECT * FROM schedule WHERE station_id = ?";
        try {
            ConnectionHandler.getInstance().connect();
            PreparedStatement st = ConnectionHandler.getInstance().con.prepareStatement(query);
            st.setInt(1, stationId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int scheduleId = rs.getInt("schedule_id");
                Integer train = rs.getInt("train_id");
                Integer departure = rs.getInt("departure_station_id");
                Integer arrival = rs.getInt("arrival_station_id");
                Timestamp departureDate = rs.getTimestamp("departure_date");
                double fee = rs.getDouble("fee");

                schedules.add(new Schedule(train, departure, arrival, departureDate, fee).addScheduleID(scheduleId));
            }
            return schedules;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return null;
    }

    public boolean validateScheduleForm(Integer train_id, Integer departureStationID, Integer arrivalStationID, Timestamp departureTimestamp, double fee) {
        return train_id != null && departureStationID != null && arrivalStationID != null && departureTimestamp != null && fee > 0;
    }

    public boolean addNewSchedule(Schedule schedule) {
        String query = "INSERT INTO schedule (train_id, departure_station_id, arrival_station_id, departure_date, fee)" +
                " VALUES (?,?,?,?,?)";
        try {
            ConnectionHandler.getInstance().connect();
            PreparedStatement st = ConnectionHandler.getInstance().con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, schedule.getTrainID());
            st.setInt(2, schedule.getDeparture());
            st.setInt(3, schedule.getArrival());
            st.setTimestamp(4, new java.sql.Timestamp(schedule.getDepartureDate().getTime()));
            st.setDouble(5, schedule.getFee());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();

            CarriageController controller = new CarriageController();
            if (rs.next()) {
                for (Carriage c : controller.getCarriage(schedule.getTrainID())) {
                    if (CarriageType.valueOf(c.getType().toString()).equals(CarriageType.SEATING)) {
                        if (!addScheduleCapacity(c.getId(), rs.getInt(1))) {
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return false;
    }

    public boolean updateSchedule(Schedule schedule) {
        String query = "UPDATE schedule SET departure_date = ? WHERE schedule_id = ?";
        try {
            ConnectionHandler.getInstance().connect();
            PreparedStatement st = ConnectionHandler.getInstance().con.prepareStatement(query);
            st.setTimestamp(1, schedule.getDepartureDate());
            st.setInt(2, schedule.getScheduleID());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return false;
    }

    private boolean addScheduleCapacity(int carriage_id, int schedule_id) {
        String capacityQuery = "INSERT INTO schedule_capacity (carriage_id, schedule_id) VALUES (?, ?)";
        try {
            ConnectionHandler.getInstance().connect();
            PreparedStatement st = ConnectionHandler.getInstance().con.prepareStatement(capacityQuery);
            st.setInt(1, carriage_id);
            st.setInt(2, schedule_id);
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return false;
    }
}
