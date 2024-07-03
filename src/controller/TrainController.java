package controller;

import model.classes.Train;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrainController {
    public List<Train> getSchedulesForStation() {
        List<Train> trains = new ArrayList<>();
        ConnectionHandler conn = new ConnectionHandler();
        String query = "SELECT * FROM train";
        try {
            conn.connect();
            PreparedStatement st = conn.con.prepareStatement(query);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("train_id");
                int speed = rs.getInt("speed");

                Train train = new Train(id, ScheduleController.getCarriage(id), speed);
                trains.add(train);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return trains;
    }
}
