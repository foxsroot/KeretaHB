package view;

import controller.ConnectionHandler;
import controller.ScheduleController;
import model.classes.Schedule;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListScheduleScreen extends JFrame {
    public ListScheduleScreen() {
        this.setTitle("List Schedule");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JTable station1 = new JTable();
        JTable station2 = new JTable();
        JTable station3 = new JTable();

        DefaultTableModel model1 = new DefaultTableModel();
        DefaultTableModel model2 = new DefaultTableModel();
        DefaultTableModel model3 = new DefaultTableModel();

        // Add columns to each model
        String[] columns = {"Schedule ID", "Train ID", "Departure Station", "Arrival Station", "Departure Date", "Fee"};
        for (String column : columns) {
            model1.addColumn(column);
            model2.addColumn(column);
            model3.addColumn(column);
        }

        // Set models to tables
        station1.setModel(model1);
        station2.setModel(model2);
        station3.setModel(model3);

        // Add data to the models
        populateTable(model1, 1);
        populateTable(model2, 2);
        populateTable(model3, 3);

        // Make tables scrollable
        JScrollPane scrollPane1 = new JScrollPane(station1);
        JScrollPane scrollPane2 = new JScrollPane(station2);
        JScrollPane scrollPane3 = new JScrollPane(station3);

        // Set layout and add scroll panes to frame
        this.setLayout(new GridLayout(3, 1));
        this.add(scrollPane1);
        this.add(scrollPane2);
        this.add(scrollPane3);
    }

    private void populateTable(DefaultTableModel model, int stationId) {
        List<Schedule> schedules = ScheduleController.getSchedulesForStation(stationId);
        for (Schedule schedule : schedules) {
            model.addRow(new Object[]{
                    schedule.getScheduleID(),
                    schedule.getTrainID(),
                    schedule.getDeparture(),
                    schedule.getArrival(),
                    schedule.getDepartureDate(),
                    schedule.getFee()
            });
        }
    }

    public static void main(String[] args) {
        ListScheduleScreen listScheduleScreen = new ListScheduleScreen();
        listScheduleScreen.setVisible(true);
    }
}