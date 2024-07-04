package view;

import controller.ScheduleController;
import controller.StationController;
import model.classes.Schedule;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListScheduleScreen extends JFrame {
    public ListScheduleScreen() {
        this.setTitle("List Schedule");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // Main panel with vertical layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Create tables and labels
        JTable stationBandung = createTable();
        JTable stationBekasi = createTable();
        JTable stationBogor = createTable();
        JTable stationCirebon = createTable();
        JTable stationDepok = createTable();

        JLabel labelBandung = createLabel("Bandung Station Schedule");
        JLabel labelBekasi = createLabel("Bekasi Station Schedule");
        JLabel labelBogor = createLabel("Bogor Station Schedule");
        JLabel labelCirebon = createLabel("Cirebon Station Schedule");
        JLabel labelDepok = createLabel("Depok Station Schedule");

        DefaultTableModel modelBandung = createTableModel();
        DefaultTableModel modelBekasi = createTableModel();
        DefaultTableModel modelBogor = createTableModel();
        DefaultTableModel modelCirebon = createTableModel();
        DefaultTableModel modelDepok = createTableModel();

        // Set models to tables
        stationBandung.setModel(modelBandung);
        stationBekasi.setModel(modelBekasi);
        stationBogor.setModel(modelBogor);
        stationCirebon.setModel(modelCirebon);
        stationDepok.setModel(modelDepok);

        // Add data to the models
        populateTable(modelBandung, 1);
        populateTable(modelBekasi, 2);
        populateTable(modelBogor, 3);
        populateTable(modelCirebon, 4);
        populateTable(modelDepok, 5);

        // Add labels and tables to the main panel
        mainPanel.add(labelBandung);
        mainPanel.add(new JScrollPane(stationBandung));
        mainPanel.add(labelBekasi);
        mainPanel.add(new JScrollPane(stationBekasi));
        mainPanel.add(labelBogor);
        mainPanel.add(new JScrollPane(stationBogor));
        mainPanel.add(labelCirebon);
        mainPanel.add(new JScrollPane(stationCirebon));
        mainPanel.add(labelDepok);
        mainPanel.add(new JScrollPane(stationDepok));

        // Make the main panel scrollable
        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        this.add(mainScrollPane);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

    private JTable createTable() {
        JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(850, 100));
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Center align the table header
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        return table;
    }

    private DefaultTableModel createTableModel() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] columns = {"Schedule ID", "Train ID", "Departure Station", "Arrival Station", "Departure Date", "Fee"};
        for (String column : columns) {
            model.addColumn(column);
        }
        return model;
    }

    private void populateTable(DefaultTableModel model, int stationId) {
        ScheduleController scheduleController = new ScheduleController();
        StationController stationController = new StationController();
        List<Schedule> schedules = scheduleController.getListSchedules(stationId);
        for (Schedule schedule : schedules) {
            model.addRow(new Object[]{
                    schedule.getScheduleID(),
                    schedule.getTrainID(),
                    stationController.getStationNameById(schedule.getDeparture()),
                    stationController.getStationNameById(schedule.getArrival()),
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
