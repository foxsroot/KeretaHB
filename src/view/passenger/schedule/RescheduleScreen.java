package view.passenger.schedule;

import controller.RescheduleController;
import controller.ScheduleController;
import controller.StationController;
import model.classes.Schedule;
import model.classes.TicketTransaction;
import view.passenger.transaction.TransactionHistoryScreen;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class RescheduleScreen extends JFrame {
    ScheduleController scheduleController = new ScheduleController();

    public RescheduleScreen(TicketTransaction ticket) {
        this.setTitle("Reschedule Ticket");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        initComponents(ticket);
        this.setVisible(true);
    }

    private void initComponents(TicketTransaction ticket) {
        this.setLayout(null);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy (hh:mm)");

        JLabel screenTitle = new JLabel("Reschedule Ticket");
        screenTitle.setFont(new Font("Calibri", Font.BOLD, 36));
        screenTitle.setBounds(250, 20, 400, 40);
        this.add(screenTitle);

        Schedule currentSchedule = scheduleController.getSchedulesById(ticket.getScheduleID());

        JLabel departureDateLabel = new JLabel("Current Departure Date : " + dateFormat.format(currentSchedule.getDepartureDate()));
        departureDateLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        departureDateLabel.setBounds(400, 80, 400, 30);
        this.add(departureDateLabel);

        StationController stationController = new StationController();

        JLabel departureStationLabel = new JLabel("Departure Station : " + stationController.getStationNameById(currentSchedule.getDeparture()));
        departureStationLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        departureStationLabel.setBounds(10, 80, 400, 30);
        this.add(departureStationLabel);

        JLabel arrivalStationLabel = new JLabel("Arrival Station : " + stationController.getStationNameById(currentSchedule.getArrival()));
        arrivalStationLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        arrivalStationLabel.setBounds(10, 120, 400, 30);
        this.add(arrivalStationLabel);

        JPanel scheduleListPanel = new JPanel();
        scheduleListPanel.setLayout(null);
        scheduleListPanel.setBackground(Color.WHITE);

        List<Schedule> schedules = getSchedulesForStation(currentSchedule.getDeparture());
        int yOffset = 10;
        for (Schedule schedule : schedules) {
            if (!Objects.equals(schedule.getScheduleID(), ticket.getScheduleID())) {
                scheduleListPanel.add(createSchedulePanel(ticket, schedule, yOffset));
                yOffset += 90;
            }
        }

        scheduleListPanel.setPreferredSize(new Dimension(870, yOffset + 120));

        JScrollPane scrollPane = new JScrollPane(scheduleListPanel);
        scrollPane.setBounds(10, 150, 870, 500);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.add(scrollPane);
    }

    private List<Schedule> getSchedulesForStation(int stationId) {
        ScheduleController controller = new ScheduleController();
        return controller.getListSchedules(stationId);
    }

    private JPanel createSchedulePanel(TicketTransaction ticket, Schedule schedule, int yOffset) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy (hh:mm)");
        JPanel schedulePanel = new JPanel();
        schedulePanel.setLayout(null);
        schedulePanel.setBounds(10, yOffset, 850, 80);
        schedulePanel.setBackground(Color.WHITE);

        JLabel scheduleDetail = new JLabel("Departure Date: " + dateFormat.format(schedule.getDepartureDate()));
        scheduleDetail.setFont(new Font("Calibri", Font.PLAIN, 25));
        scheduleDetail.setBounds(10, 10, 600, 30);
        schedulePanel.add(scheduleDetail);

        JButton rescheduleButton = new JButton("Reschedule");
        rescheduleButton.setBounds(650, 10, 110, 30);
        rescheduleButton.addActionListener(e -> {
            int choose = JOptionPane.showConfirmDialog(null, "Are you sure want to reschedule to " + dateFormat.format(schedule.getDepartureDate()) + "?\nYou Can Only Reschedule 1 time!", "Confirmation", JOptionPane.YES_NO_OPTION);
            RescheduleController rescheduleController = new RescheduleController();
            if (choose == JOptionPane.YES_OPTION) {
                if (rescheduleController.requestReschedule(ticket.getTransactionID(), schedule.getScheduleID())){
                    JOptionPane.showMessageDialog(null, "Reschedule request will be reviewed by admin!", "Reschedule Success", JOptionPane.INFORMATION_MESSAGE);
                    new TransactionHistoryScreen();
                }else{
                    JOptionPane.showMessageDialog(null, "Reschedule request failed!", "Reschedule failed", JOptionPane.ERROR_MESSAGE);
                }
            } this.dispose();
        }); schedulePanel.add(rescheduleButton);

        return schedulePanel;
    }
}
