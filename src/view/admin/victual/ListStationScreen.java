package view.admin.victual;

import controller.StationController;
import model.classes.Station;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ListStationScreen extends JFrame {
    public ListStationScreen() {
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Station Stock Manager");

        JLabel screenTitle = new JLabel("Station Victual Stock");
        screenTitle.setFont(new Font("calibri", Font.BOLD, 20));
        screenTitle.setBounds(360, 20, 200, 30);

        JPanel stationPanel = new JPanel();
        stationPanel.setLayout(null);
        stationPanel.setBounds(35, 85, 850, 550);

        StationController stationController = new StationController();
        List<Station> stations = stationController.getlistStations();

        int xOffset = 10;
        int yOffset = 10;
        int counter = 0;

        for (Station station : stations) {
            if (counter != 0 && counter % 2 == 0) {
                yOffset += 160;
                xOffset = 10;
            }

            stationPanel.add(createStationPanel(station, xOffset, yOffset));
            xOffset += 410;
            counter++;
        }

        stationPanel.setPreferredSize(new Dimension(850, yOffset + 160));

        JScrollPane scrollPane = new JScrollPane(stationPanel);
        scrollPane.setBounds(20, 80, 850, 530);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane);
        add(screenTitle);
        revalidate();
        repaint();
    }

    private JPanel createStationPanel(Station station, int xOffset, int yOffset) {
        JPanel stationPanel = new JPanel();
        stationPanel.setLayout(null);
        stationPanel.setBackground(Color.WHITE);
        stationPanel.setBounds(xOffset, yOffset, 400, 150);

        JLabel stationName = new JLabel(station.getName());
        stationName.setFont(new Font("calibri", Font.BOLD, 17));
        stationName.setBounds(20, 40, 200, 30);
        stationPanel.add(stationName);

        JLabel location = new JLabel(station.getLocation());
        location.setFont(new Font("calibri", Font.PLAIN, 17));
        location.setBounds(20, 60, 200, 30);
        stationPanel.add(location);

        JButton editStockButton = new JButton("Edit Stock");
        editStockButton.setFont(new Font("calibri", Font.BOLD, 17));
        editStockButton.setBounds(130, 95, 150, 30);
        stationPanel.add(editStockButton);

        editStockButton.addActionListener(e -> {
            dispose();
            new UpdateStockScreen(station);
        });

        return stationPanel;
    }

    public static void main(String[] args) {
        new ListStationScreen();
    }
}
