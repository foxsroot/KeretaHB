package view.admin.schedule;

import controller.*;
import model.classes.*;
import model.enums.RescheduleEnum;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RescheduleRequestListScreen extends JFrame {
    public RescheduleRequestListScreen() {
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Reschedule Request List");

        JLabel screenTitle = new JLabel("Reschedule Request List");
        screenTitle.setFont(new Font("calibri", Font.BOLD, 20));
        screenTitle.setBounds(350, 10, 300, 30);

        JPanel rescheduleListPanel = new JPanel();
        rescheduleListPanel.setLayout(null);

        RescheduleController rescheduleController = new RescheduleController();
        ArrayList<RescheduleRequest> rescheduleList = rescheduleController.getRescheduleRequestList();

        int yOffset = 10;

        for (RescheduleRequest rescheduleRequest : rescheduleList) {
            rescheduleListPanel.add(createReschedulePanel(rescheduleRequest, yOffset));
            yOffset += 160;
        }

        rescheduleListPanel.setPreferredSize(new Dimension(830, yOffset));

        JScrollPane scrollPane = new JScrollPane(rescheduleListPanel);
        scrollPane.setBounds(10, 50, 870, 550);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(screenTitle);
        add(scrollPane);
    }

    private JPanel createReschedulePanel(RescheduleRequest rescheduleRequest, int yOffset) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(10, yOffset, 850, 150);

        TransactionController transactionController = new TransactionController();
        TicketTransaction ticketTransaction = transactionController.getTicketTransaction(rescheduleRequest.getTransactionID());
        String buyerEmail =  transactionController.getTicketBuyerEmail(rescheduleRequest.getTransactionID());

        ScheduleController scheduleController = new ScheduleController();
        Schedule schedule = scheduleController.getSchedulesById(ticketTransaction.getSchdeuleID());
        Schedule requestedSchedule = scheduleController.getSchedulesById(rescheduleRequest.getRequestedScheduleID());

        StationController stationController = new StationController();
        String departureStationName = stationController.getStationNameById(schedule.getDeparture());
        String arrivalStationName = stationController.getStationNameById(schedule.getArrival());

        JLabel buyerLabel = new JLabel("Buyer Email: " + buyerEmail);
        buyerLabel.setBounds(10, 0, 300, 30);
        panel.add(buyerLabel);

        JLabel totalPassenger = new JLabel("Total Passengers: " + ticketTransaction.getPassengers());
        totalPassenger.setBounds(10, 30, 300, 30);
        panel.add(totalPassenger);

        JLabel departureStation = new JLabel("Departure Station: " + departureStationName);
        departureStation.setBounds(10, 60, 300, 30);
        panel.add(departureStation);

        JLabel arrivalStation = new JLabel("Arrival Station: " + arrivalStationName);
        arrivalStation.setBounds(10, 90, 300, 30);
        panel.add(arrivalStation);

        JLabel departureTime = new JLabel("Departure Time: " + new SimpleDateFormat("dd MMMM yyyy").format(schedule.getDepartureDate()));
        departureTime.setBounds(10, 120, 300, 30);
        panel.add(departureTime);

        JLabel totalPrice = new JLabel("Total Price: " + new NumberFormatter(NumberFormat.getCurrencyInstance(new Locale("id", "ID"))).getFormat().format(ticketTransaction.getTotal()));
        totalPrice.setBounds(300, 0, 330, 30);
        panel.add(totalPrice);

        JLabel requestedScheduleTitle = new JLabel("Requested Schedule:");
        requestedScheduleTitle.setBounds(300, 30, 300, 30);
        panel.add(requestedScheduleTitle);

        JLabel requestedDepartureTime = new JLabel("Departure Time: " + new SimpleDateFormat("dd MMMM yyyy").format(requestedSchedule.getDepartureDate()));
        requestedDepartureTime.setBounds(300, 60, 300, 30);
        panel.add(requestedDepartureTime);

        JComboBox<RescheduleEnum> response = new JComboBox<>(RescheduleEnum.values());
        response.setBounds(500, 40, 300, 30);
        panel.add(response);

        JButton sendButton = new JButton("Submit Response");
        sendButton.setBounds(500, 80, 300, 30);
        panel.add(sendButton);

        sendButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Send Selected Response to the Reschedule Request?", "Reschedule Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                RescheduleController rescheduleController = new RescheduleController();
                if (rescheduleController.respondRescheduleRequest(ticketTransaction.getTransactionID(), buyerEmail, RescheduleEnum.valueOf(response.getSelectedItem().toString()), 1, requestedSchedule.getScheduleID())) {
                    //admin id ganti ke singleton yg lagi login
                    JOptionPane.showMessageDialog(null, "Response Submitted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        return panel;
    }

    public static void main(String[] args) {
        new RescheduleRequestListScreen();
    }
}
