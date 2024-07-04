package controller;

import java.sql.ResultSet;

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
}
