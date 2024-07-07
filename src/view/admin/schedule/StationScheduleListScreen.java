package view.admin.schedule;

import controller.ScheduleController;
import model.classes.Schedule;

import javax.swing.*;
import java.awt.*;

public class StationScheduleListScreen extends JFrame {
    public StationScheduleListScreen(ListScheduleScreen scheduleScreen, String station) {
        this.setTitle(station + " Station Schedule");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBounds(20, 70, 850, 300);

        switch (station) {
            case "Bandung":
                scheduleScreen.displayBandungSchedule(mainPanel);
                break;
            case "Bekasi":
                scheduleScreen.displayBekasiSchedule(mainPanel);
                break;
            case "Bogor":
                scheduleScreen.displayBogorSchedule(mainPanel);
                break;
            case "Cirebon":
                scheduleScreen.displayCirebonSchedule(mainPanel);
                break;
            case "Depok":
                scheduleScreen.displayDepokSchedule(mainPanel);
                break;
        }

        JLabel showScheduleById = createLabel("Input Schedule ID for Detail");
        mainPanel.add(showScheduleById);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JTextField showScheduleByIdField = new JTextField();
        showScheduleByIdField.setFont(new Font("Calibri", Font.BOLD, 16));
        showScheduleByIdField.setPreferredSize(new Dimension(600, 30));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Calibri", Font.BOLD, 16));
        searchButton.setPreferredSize(new Dimension(100, 29));

        JButton backButton = new JButton("Back to Schedule Selection");
        backButton.setBounds(50, 600, 250, 30);
        backButton.setFont(new Font("Calibri", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            StationScheduleSelection selectStation = new StationScheduleSelection();
            this.dispose();
            selectStation.setVisible(true);
        });

        searchButton.addActionListener(e -> {
            String searchID = showScheduleByIdField.getText();
            ScheduleController schController = new ScheduleController();
            if (searchID.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill the Schedule ID", "Empty Field", JOptionPane.WARNING_MESSAGE);
            }
            Schedule searchedSchedule = schController.getSchedulesById(Integer.parseInt(searchID));
            if (searchedSchedule == null) {
                JOptionPane.showMessageDialog(null, "Schedule ID not found", "Schedule Not Found", JOptionPane.ERROR_MESSAGE);
            } else {
                ScheduleDetailScreen scheduleDetailScreen = new ScheduleDetailScreen(searchedSchedule);
                this.dispose();
                scheduleDetailScreen.setVisible(true);

            }
        });
        searchPanel.add(showScheduleByIdField);
        searchPanel.add(searchButton);
        add(backButton);


        mainPanel.add(searchPanel);

        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        this.add(mainScrollPane);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

}
