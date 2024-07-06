package view.passenger.station;

import controller.StationController;
import model.classes.Station;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StationListScreen extends JFrame {
    public StationListScreen() {
        this.setTitle("Station List");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBounds(20, 70, 850, 300);

        JLabel viewStationList = createLabel("View Station List");
        mainPanel.add(viewStationList);

        addStationList(mainPanel);


        JLabel showScheduleById = createLabel("Input Station ID for Detail");
        mainPanel.add(showScheduleById);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JTextField showScheduleByIdField = new JTextField();
        showScheduleByIdField.setFont(new Font("Calibri", Font.BOLD, 16));
        showScheduleByIdField.setPreferredSize(new Dimension(600, 30));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Calibri", Font.BOLD, 16));
        searchButton.setPreferredSize(new Dimension(100, 29));

        JButton backButton = new JButton("Back to Station Menu");
        backButton.setFont(new Font("Calibri", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(250, 30));
        backButton.addActionListener(e -> {
            //Back to Main Menu
            this.dispose();
        });

        searchButton.addActionListener(e -> {
            String searchID = showScheduleByIdField.getText();
            StationController stController = new StationController();
            if (searchID.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill the Station ID", "Empty Field", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    Station searchedStation = stController.getStationById(Integer.parseInt(searchID));
                    if (searchedStation == null) {
                        JOptionPane.showMessageDialog(null, "Station ID not found", "Station Not Found", JOptionPane.ERROR_MESSAGE);
                    } else {
                        StationDetailScreen scheduleDetailScreen = new StationDetailScreen(searchedStation);
                        this.dispose();
                        scheduleDetailScreen.setVisible(true);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Station ID format", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        searchPanel.add(showScheduleByIdField);
        searchPanel.add(searchButton);
        mainPanel.add(searchPanel);

        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        this.add(mainScrollPane);
        this.add(backButton, BorderLayout.SOUTH);
    }

    private void addStationList(JPanel panel) {
        JTable table = createTable();
        DefaultTableModel model = createTableModel();
        table.setModel(model);
        populateTable(model);

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
        String[] columns = {"Station ID", "Station Name", "Location"};
        for (String column : columns) {
            model.addColumn(column);
        }
        return model;
    }

    private void populateTable(DefaultTableModel model) {
        StationController stationController = new StationController();
        List<Station> stations = stationController.getlistStations();
        for (Station station : stations) {
            model.addRow(new Object[]{
                    station.getId(),
                    station.getName(),
                    station.getLocation()
            });
        }
    }

    // Testing
    public static void main(String[] args) {
        StationListScreen stationListScreen = new StationListScreen();
        stationListScreen.setVisible(true);
    }
}
