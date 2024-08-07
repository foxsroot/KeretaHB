package view.passenger.schedule;

import controller.AuthenticationHelper;
import controller.StationController;
import controller.TrainController;
import model.classes.Schedule;
import view.guest.Login;
import view.passenger.transaction.TicketCheckoutScreen;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

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

        mainPanel.add(createLabel("Fee"));
        mainPanel.add(createLabel(": Rp" + schedule.getFee()));

        JScrollPane mainScrollPane = new JScrollPane(mainPanel);

        JButton bookTicketButton = new JButton("Book Ticket");
        bookTicketButton.setFont(new Font("Calibri", Font.BOLD, 16));
        bookTicketButton.setPreferredSize(new Dimension(150, 29));

        bookTicketButton.addActionListener(e -> {
            if (AuthenticationHelper.getInstance().getUserId() == 0) {
                JOptionPane.showMessageDialog(null, "You Must Login First!");
                dispose();
                new Login();
            } else {
                dispose();
                new TicketCheckoutScreen(schedule);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        buttonPanel.add(bookTicketButton);

        this.setLayout(new BorderLayout());
        this.add(mainScrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

}
