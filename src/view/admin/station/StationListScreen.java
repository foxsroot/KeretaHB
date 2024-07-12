package view.admin.station;

import controller.StationController;
import model.classes.Station;
import view.admin.AdminMenu;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StationListScreen extends JFrame {
    public StationListScreen() {
        this.setTitle("Station List");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setLayout(null);

        JLabel screenTitle = new JLabel("Station List");
        screenTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        screenTitle.setBounds(350, 20, 200, 30);

        JPanel stationListPanel = new JPanel();
        stationListPanel.setLayout(null);
        stationListPanel.setBackground(Color.WHITE);

        // Display stations
        List<Station> stations = getStationList();
        int yOffset = 10;
        for (Station station : stations) {
            stationListPanel.add(createStationPanel(station, yOffset));
            yOffset += 110;
        }

        stationListPanel.setPreferredSize(new Dimension(870, yOffset + 120));

        JScrollPane scrollPane = new JScrollPane(stationListPanel);
        scrollPane.setBounds(10, 70, 870, 530);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton backButton = new JButton("Back to Station Menu");
        backButton.setBounds(30, 630, 250, 30);
        backButton.setFont(new Font("Calibri", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            new AdminMenu();
            this.dispose();

        });

        add(screenTitle);
        add(scrollPane);
        add(backButton);
    }

    private List<Station> getStationList() {
        StationController stationController = new StationController();
        return stationController.getlistStations();
    }

    private JPanel createStationPanel(Station station, int yOffset) {
        JPanel stationPanel = new JPanel();
        stationPanel.setLayout(null);
        stationPanel.setBounds(10, yOffset, 850, 100);
        stationPanel.setBackground(Color.WHITE);

        JLabel stationId = new JLabel("ID: " + station.getId());
        stationId.setFont(new Font("Calibri", Font.BOLD, 25));
        stationId.setBounds(10, 10, 100, 30);
        stationPanel.add(stationId);

        JLabel stationName = new JLabel("Name: " + station.getName());
        stationName.setFont(new Font("Calibri", Font.PLAIN, 25));
        stationName.setBounds(120, 10, 600, 30);
        stationPanel.add(stationName);

        JLabel stationLocation = new JLabel("Location: " + station.getLocation());
        stationLocation.setFont(new Font("Calibri", Font.PLAIN, 25));
        stationLocation.setBounds(120, 50, 600, 30);
        stationPanel.add(stationLocation);

        JButton viewDetailButton = new JButton("View Detail");
        viewDetailButton.setBounds(730, 30, 110, 30);
        viewDetailButton.addActionListener(e -> {
            new StationDetailScreen(station);
            this.dispose();
        });
        stationPanel.add(viewDetailButton);

        return stationPanel;
    }

    public static void main(String[] args) {
        new StationListScreen();
    }
}
