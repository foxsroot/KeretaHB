package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
        String[] columns = {"Schedule ID", "Train ID", "Departure Station ID", "Arrival Station ID", "Departure Date", "Fee"};
        for (String column : columns) {
            model1.addColumn(column);
            model2.addColumn(column);
            model3.addColumn(column);
        }
    }

    public static void main(String[] args) {
        ListScheduleScreen listScheduleScreen = new ListScheduleScreen();
        listScheduleScreen.setVisible(true);
    }
}
