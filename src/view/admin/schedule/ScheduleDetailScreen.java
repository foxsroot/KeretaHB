package view.admin.schedule;

import controller.ScheduleController;
import controller.StationController;
import controller.TrainController;
import model.classes.Schedule;
import view.admin.AdminMenu;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ScheduleDetailScreen extends JFrame {

    public ScheduleDetailScreen(Schedule schedule) {
        this.setTitle("Schedule Detail");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        StationController stController = new StationController();
        TrainController trainController = new TrainController();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(10, 2, 5, 10));
        mainPanel.setBounds(20, 70, 850, 300);

        mainPanel.add(createLabel("Schedule ID"));
        mainPanel.add(createLabel(": " + schedule.getScheduleID().toString()));

        mainPanel.add(createLabel("Train ID:"));
        mainPanel.add(createLabel(": " + schedule.getTrainID().toString()));

        int capacity = trainController.totalTrainCapacity(schedule.getTrainID());
        mainPanel.add(createLabel("Seat Capacity"));
        mainPanel.add(createLabel(": " + capacity));

        String departure = stController.getStationNameById(schedule.getDeparture());
        mainPanel.add(createLabel("Departure Station"));
        mainPanel.add(createLabel(": " + departure));

        String arrival = stController.getStationNameById(schedule.getArrival());
        mainPanel.add(createLabel("Arrival Station"));
        mainPanel.add(createLabel(": " + arrival));

        String departureDate = new SimpleDateFormat("dd-MM-yyyy").format(schedule.getDepartureDate());
        mainPanel.add(createLabel("Departure Date"));
        mainPanel.add(createLabel(": " + departureDate));

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        currencyFormat.setMaximumFractionDigits(0);
        mainPanel.add(createLabel("Fee"));
        mainPanel.add(createLabel(": " + currencyFormat.format(schedule.getFee())));

        JScrollPane mainScrollPane = new JScrollPane(mainPanel);

        JButton editScheduleButton = new JButton("Edit Schedule");
        editScheduleButton.setFont(new Font("Calibri", Font.BOLD, 16));
        editScheduleButton.setPreferredSize(new Dimension(150, 29));

        JButton deleteScheduleButton = new JButton("Delete Schedule");
        deleteScheduleButton.setFont(new Font("Calibri", Font.BOLD, 16));
        deleteScheduleButton.setPreferredSize(new Dimension(150, 29));


        JButton backButton = new JButton("Back to Schedule Selection");
        backButton.setFont(new Font("Calibri", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            new StationScheduleSelection();
            this.dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(editScheduleButton);
        buttonPanel.add(deleteScheduleButton);

        this.setLayout(new BorderLayout());
        this.add(mainScrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        editScheduleButton.addActionListener(e -> {
            new AddEditScheduleScreen(schedule);
            this.dispose();
        });
        deleteScheduleButton.addActionListener(e -> {
            ScheduleController schController = new ScheduleController();
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this Schedule?", "Warning", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (schController.deleteSchedule(schedule)) {
                    JOptionPane.showMessageDialog(null, "Schedule deleted successfully", "Delete Schedule", JOptionPane.INFORMATION_MESSAGE);
                    new AdminMenu();
                    this.dispose();
                }
            }
        });

        this.setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

}
