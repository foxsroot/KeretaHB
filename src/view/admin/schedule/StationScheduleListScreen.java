package view.admin.schedule;

import controller.ScheduleController;
import model.classes.Schedule;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StationScheduleListScreen extends JFrame {
    public StationScheduleListScreen( String station) {
        this.setTitle(station + " Station Schedule");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        initComponents(station);
        this.setVisible(true);
    }

    private void initComponents( String station) {
        this.setLayout(null);

        JLabel screenTitle = new JLabel(station + " Station Schedule");
        screenTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        screenTitle.setBounds(300, 20, 400, 30);

        JPanel scheduleListPanel = new JPanel();
        scheduleListPanel.setLayout(null);
        scheduleListPanel.setBackground(Color.WHITE);

        // Display schedules based on the selected station
        List<Schedule> schedules = getSchedulesForStation(station);
        int yOffset = 10;
        for (Schedule schedule : schedules) {
            scheduleListPanel.add(createSchedulePanel(schedule, yOffset));
            yOffset += 110;
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
            new StationScheduleSelection();
            this.dispose();
        });

        add(screenTitle);
        add(scrollPane);
        add(backButton);
    }

    private List<Schedule> getSchedulesForStation(String station) {
        // Simulating fetching schedules based on station
        ScheduleController controller = new ScheduleController();
        switch (station) {
            case "Bandung":
                return controller.getListSchedules(1);
            case "Bekasi":
                return controller.getListSchedules(2);
            case "Bogor":
                return controller.getListSchedules(3);
            case "Cirebon":
                return controller.getListSchedules(4);
            case "Depok":
                return controller.getListSchedules(5);
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
            new ScheduleDetailScreen(schedule);
            this.dispose();
        });
        schedulePanel.add(viewDetailButton);

        return schedulePanel;
    }
}
