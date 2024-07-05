package controller;

import model.classes.Station;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
}
