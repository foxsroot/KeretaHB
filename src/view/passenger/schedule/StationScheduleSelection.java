package view.passenger.schedule;

import config.DirectoryConfig;
import controller.AuthenticationHelper;
import controller.ImageController;
import controller.StationController;
import model.classes.Station;
import view.guest.Login;
import view.passenger.PassengerMenu;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StationScheduleSelection extends JFrame {
    public StationScheduleSelection() {
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Station Schedule");

        JLabel screenTitle = new JLabel("View Station Schedule");
        screenTitle.setFont(new Font("Calibri", Font.BOLD, 20));
        screenTitle.setBounds(380, 10, 200, 30);

        JPanel stationListPanel = new JPanel();
        stationListPanel.setLayout(null);
        stationListPanel.setBounds(30, 60, 880, 550);

        StationController stationController = new StationController();
        List<Station> stations = stationController.getlistStations();

        int yOffset = 10;
        int xOffset = 10;
        int counter = 0;


        for (Station station : stations) {
            if (counter != 0 && counter % 2 == 0) {
                xOffset = 10;
                yOffset += 160;
            }

            stationListPanel.add(createStationPanel(station, xOffset, yOffset));
            counter++;
            xOffset += 410;
        }

        stationListPanel.setPreferredSize(new Dimension(800, yOffset + 160));

        JScrollPane scrollPane = new JScrollPane(stationListPanel);
        scrollPane.setBounds(20, 50, 850, 530);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton exitButton = new JButton("Exit to Main Menu");
        exitButton.setBounds(20, 600, 150, 30);
        exitButton.addActionListener(e -> {
            new PassengerMenu();
            this.dispose();
        });

        add(screenTitle);
        add(scrollPane);
        add(exitButton);

        this.setVisible(true);
    }

    private JPanel createStationPanel(Station station, int xOffset, int yOffset) {
        JPanel stationPanel = new JPanel();
        stationPanel.setLayout(null);
        stationPanel.setBackground(Color.WHITE);
        stationPanel.setBounds(xOffset, yOffset, 400, 150);

        JLabel image = new JLabel();
        image.setBounds(10, 0, 120, 150);
        image.setIcon(new ImageIcon(ImageController.resizeImage(DirectoryConfig.STATION_IMAGES + station.getPicture(), 150, 120)));
        stationPanel.add(image);

        JLabel name = new JLabel(station.getName());
        name.setFont(new Font("Calibri", Font.BOLD, 20));
        name.setBounds(140, 10, 200, 30);
        stationPanel.add(name);

        JLabel location = new JLabel(station.getLocation());
        location.setFont(new Font("Calibri", Font.PLAIN, 18));
        location.setBounds(140, 30, 200, 30);
        stationPanel.add(location);


        JButton viewButton = new JButton("View Schedule");
        viewButton.setBounds(140, 90, 130, 30);
        stationPanel.add(viewButton);

        viewButton.addActionListener(e -> {
            switchToScheduleScreen(station.getName().replace(" Station", ""));
        });

        return stationPanel;
    }

    private void switchToScheduleScreen(String station) {
        this.dispose();
        StationScheduleListScreen scheduleDetailScreen = new StationScheduleListScreen(station);
        scheduleDetailScreen.setVisible(true);
    }

    public static void main(String[] args) {
        new StationScheduleSelection();
    }
}
