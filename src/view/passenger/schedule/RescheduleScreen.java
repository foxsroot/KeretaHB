package view.passenger.schedule;

import controller.RescheduleController;
import controller.ScheduleController;
import model.classes.Schedule;
import model.classes.TicketTransaction;
import view.passenger.transaction.TransactionHistoryScreen;

import java.sql.Timestamp;
import java.util.List;

import javax.swing.*;
import java.awt.*;


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

        JLabel screenTitle = new JLabel("Reschedule Ticket ID: " + ticket.getSchdeuleID());
        screenTitle.setFont(new Font("Calibri", Font.BOLD, 36));
        screenTitle.setBounds(250, 20, 400, 40);
        this.add(screenTitle);

        Schedule sch = scheduleController.getSchedulesById(ticket.getSchdeuleID());

        JLabel departureDateLabel = new JLabel("Departure Date: " + sch.getDepartureDate());
        departureDateLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        departureDateLabel.setBounds(100, 100, 400, 30);
        this.add(departureDateLabel);

        JPanel scheduleListPanel = new JPanel();
        scheduleListPanel.setLayout(null);
        scheduleListPanel.setBackground(Color.WHITE);

        List<Schedule> schedules = getSchedulesForStation(sch.getDeparture());
        int yOffset = 10;
        for (Schedule schedule : schedules) {
            scheduleListPanel.add(createSchedulePanel(ticket, schedule, yOffset));
            yOffset += 110;
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

        JButton rescheduleButton = new JButton("Reschedule");
        rescheduleButton.setBounds(730, 30, 110, 30);
        rescheduleButton.addActionListener(e -> {
            int choose = JOptionPane.showConfirmDialog(null, "Are you sure want to reschedule to " + schedule.getDepartureDate() + "?");
            RescheduleController rescheduleController = new RescheduleController();
            if (choose == JOptionPane.YES_OPTION) {
                if (rescheduleController.requestReschedule(ticket.getTransactionID(), schedule.getScheduleID())){
                    JOptionPane.showMessageDialog(null, "Reschedule successful!", "Reschedule Success", JOptionPane.INFORMATION_MESSAGE);
                    new TransactionHistoryScreen();
                }else{
                    JOptionPane.showMessageDialog(null, "Reschedule failed!", "Reschedule failed", JOptionPane.ERROR_MESSAGE);
                }
            } this.dispose();
        }); schedulePanel.add(rescheduleButton);

        return schedulePanel;
    }

    public static void main(String[] args) {
        Timestamp date = new Timestamp(100);
        TicketTransaction ticket = new TicketTransaction(1, date, 4, false, 5, false, 100000);
        new RescheduleScreen(ticket);
    }
}
