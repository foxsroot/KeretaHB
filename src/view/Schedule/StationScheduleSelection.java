package view.Schedule;

import javax.swing.*;
import java.awt.*;

public class StationScheduleSelection extends JFrame {
    public StationScheduleSelection() {
        this.setTitle("Schedule Detail");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel viewScheduleDetail = createLabel("View Schedule Detail");
        mainPanel.add(viewScheduleDetail);

        JButton bandungStation = createStationButton("Bandung Station", new ListScheduleScreen(), "Bandung");
        JButton bekasiStation = createStationButton("Bekasi Station", new ListScheduleScreen(), "Bekasi");
        JButton bogorStation = createStationButton("Bogor Station", new ListScheduleScreen(), "Bogor");
        JButton cirebonStation = createStationButton("Cirebon Station", new ListScheduleScreen(), "Cirebon");
        JButton depokStation = createStationButton("Depok Station", new ListScheduleScreen(), "Depok");

        mainPanel.add(bandungStation);
        mainPanel.add(bekasiStation);
        mainPanel.add(bogorStation);
        mainPanel.add(cirebonStation);
        mainPanel.add(depokStation);

        this.add(mainPanel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

    private JButton createStationButton(String text, ListScheduleScreen scheduleScreen, String station) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(200, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e -> {
            switchToScheduleScreen(scheduleScreen, station);
        });
        return button;
    }

    private void switchToScheduleScreen(ListScheduleScreen scheduleScreen, String station) {
        this.dispose();
        StationScheduleListScreen scheduleDetailScreen = new StationScheduleListScreen(scheduleScreen, station);
        scheduleDetailScreen.setVisible(true);
    }

    // Testing
    public static void main(String[] args) {
        StationScheduleSelection screen = new StationScheduleSelection();
        screen.setVisible(true);
    }
}
