package view.passenger.schedule;

import controller.ScheduleController;
import controller.StationController;
import model.classes.Schedule;
import view.passenger.schedule.ScheduleDetailScreen;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ListScheduleScreen extends JFrame {
    public ListScheduleScreen() {
        this.setTitle("All Station Schedules");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setLayout(null);

        JLabel screenTitle = new JLabel("All Station Schedules");
        screenTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        screenTitle.setBounds(300, 20, 400, 30);

        JPanel scheduleListPanel = new JPanel();
        scheduleListPanel.setLayout(null);
        scheduleListPanel.setBackground(Color.WHITE);

        // Display schedules for all stations
        String[] stations = {"Bandung", "Bekasi", "Bogor", "Cirebon", "Depok"};
        int yOffset = 10;
        for (String station : stations) {
            JLabel stationLabel = new JLabel(station + " Station");
            stationLabel.setFont(new Font("Calibri", Font.BOLD, 25));
            stationLabel.setBounds(10, yOffset, 200, 30);
            scheduleListPanel.add(stationLabel);
            yOffset += 40;

            List<Schedule> schedules = getSchedulesForStation(station);
            for (Schedule schedule : schedules) {
                scheduleListPanel.add(createSchedulePanel(schedule, yOffset));
                yOffset += 110;
            }

            yOffset += 20;
        }

        scheduleListPanel.setPreferredSize(new Dimension(870, yOffset + 120));

        JScrollPane scrollPane = new JScrollPane(scheduleListPanel);
        scrollPane.setBounds(10, 70, 870, 530);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton backButton = new JButton("Back to Schedule Selection");
        backButton.setBounds(30, 630, 250, 30);
        backButton.setFont(new Font("Calibri", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            StationScheduleSelection selectStation = new StationScheduleSelection();
            this.dispose();
            selectStation.setVisible(true);
        });

        add(screenTitle);
        add(scrollPane);
        add(backButton);
    }

    private List<Schedule> getSchedulesForStation(String station) {
        ScheduleController scheduleController = new ScheduleController();
        switch (station) {
            case "Bandung":
                return scheduleController.getListSchedules(1);
            case "Bekasi":
                return scheduleController.getListSchedules(2);
            case "Bogor":
                return scheduleController.getListSchedules(3);
            case "Cirebon":
                return scheduleController.getListSchedules(4);
            case "Depok":
                return scheduleController.getListSchedules(5);
            default:
                return null;
        }
    }

    private JPanel createSchedulePanel(Schedule schedule, int yOffset) {
        JPanel schedulePanel = new JPanel();
        schedulePanel.setLayout(null);
        schedulePanel.setBounds(10, yOffset, 850, 100);
        schedulePanel.setBackground(Color.WHITE);

        JLabel scheduleId = new JLabel("ID: " + schedule.getScheduleID());
        scheduleId.setFont(new Font("Calibri", Font.BOLD, 25));
        scheduleId.setBounds(10, 10, 100, 30);
        schedulePanel.add(scheduleId);

        JLabel scheduleDetail = new JLabel("Departure Date: " + schedule.getDepartureDate());
        scheduleDetail.setFont(new Font("Calibri", Font.PLAIN, 25));
        scheduleDetail.setBounds(120, 10, 600, 30);
        schedulePanel.add(scheduleDetail);

        JButton viewDetailButton = new JButton("View Detail");
        viewDetailButton.setBounds(730, 30, 110, 30);
        viewDetailButton.addActionListener(e -> {
            ScheduleDetailScreen scheduleDetailScreen = new ScheduleDetailScreen(schedule);
            this.dispose();
            scheduleDetailScreen.setVisible(true);
        });
        schedulePanel.add(viewDetailButton);

        return schedulePanel;
    }

    public static void main(String[] args) {
        new ListScheduleScreen();
    }
}
