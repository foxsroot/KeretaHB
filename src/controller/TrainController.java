package controller;

import model.classes.Train;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrainController {
    public List<Train> getSchedulesForStation() {
        List<Train> trains = new ArrayList<>();

        String query = "SELECT * FROM train";
        try {
            PreparedStatement st = ConnectionHandler.getConnection().prepareStatement(query);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("train_id");
                int speed = rs.getInt("speed");

                Train train = new Train(id, ScheduleController.getCarriage(), speed);
                trains.add(train);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return trains;
    }
}
