package view.admin.schedule;

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
    }

    public void displayBandungSchedule(JPanel mainPanel) {
        addBandungTable(mainPanel);
    }

    public void displayBekasiSchedule(JPanel mainPanel) {
        addBekasiTable(mainPanel);
    }

    public void displayBogorSchedule(JPanel mainPanel) {
        addBogorTable(mainPanel);
    }

    public void displayCirebonSchedule(JPanel mainPanel) {
        addCirebonTable(mainPanel);
    }

    public void displayDepokSchedule(JPanel mainPanel) {
        addDepokTable(mainPanel);
    }

    private void addBandungTable(JPanel panel) {
        JLabel label = createLabel("Bandung Station Schedule");
        JTable table = createTable();
        DefaultTableModel model = createTableModel();
        table.setModel(model);
        populateTable(model, 1);

        panel.add(label);
        panel.add(new JScrollPane(table));
    }

    private void addBekasiTable(JPanel panel) {
        JLabel label = createLabel("Bekasi Station Schedule");
        JTable table = createTable();
        DefaultTableModel model = createTableModel();
        table.setModel(model);
        populateTable(model, 2);

        panel.add(label);
        panel.add(new JScrollPane(table));
    }

    private void addBogorTable(JPanel panel) {
        JLabel label = createLabel("Bogor Station Schedule");
        JTable table = createTable();
        DefaultTableModel model = createTableModel();
        table.setModel(model);
        populateTable(model, 3);

        panel.add(label);
        panel.add(new JScrollPane(table));
    }

    private void addCirebonTable(JPanel panel) {
        JLabel label = createLabel("Cirebon Station Schedule");
        JTable table = createTable();
        DefaultTableModel model = createTableModel();
        table.setModel(model);
        populateTable(model, 4);

        panel.add(label);
        panel.add(new JScrollPane(table));
    }

    private void addDepokTable(JPanel panel) {
        JLabel label = createLabel("Depok Station Schedule");
        JTable table = createTable();
        DefaultTableModel model = createTableModel();
        table.setModel(model);
        populateTable(model, 5);

        panel.add(label);
        panel.add(new JScrollPane(table));
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

    private JTable createTable() {
        JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(850, 100));
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setFont(new Font("Calibri", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 14));
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
                    schedule.getDepartureDate(),
                    schedule.getFee()
            });
        }
    }

    // Testing
    public static void test() {
        ListScheduleScreen listScheduleScreen = new ListScheduleScreen();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBounds(20, 70, 850, 300);

        listScheduleScreen.displayBandungSchedule(mainPanel);
        listScheduleScreen.displayBekasiSchedule(mainPanel);
        listScheduleScreen.displayBogorSchedule(mainPanel);
        listScheduleScreen.displayCirebonSchedule(mainPanel);
        listScheduleScreen.displayDepokSchedule(mainPanel);

        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        listScheduleScreen.add(mainScrollPane);
        JScrollBar verticalScrollBar = mainScrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setBlockIncrement(75);

        listScheduleScreen.setVisible(true);
    }
}
