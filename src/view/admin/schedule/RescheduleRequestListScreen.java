package view.admin.schedule;

import javax.swing.*;
import java.awt.*;

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
        screenTitle.setBounds(400, 10, 300, 30);


    }
}
