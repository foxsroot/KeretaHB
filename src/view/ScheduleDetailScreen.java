package view;

import javax.swing.*;
import java.awt.*;

public class ScheduleDetailScreen extends JFrame {
    public ScheduleDetailScreen(){
        this.setTitle("Schedule Detail");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JLabel viewScheduleDetail = createLabel("View Schedule Detail");
        add(viewScheduleDetail);
        JButton bandungStation = new JButton("Bandung Station");
        JButton bekasiStation = new JButton("Bekasi Station");
        JButton bogorStation = new JButton("Bogori Station");
        JButton cirebonStation = new JButton("Cirebon Station");
        JButton depokStation = new JButton("Depok Station");
        add(bandungStation);
        add(bekasiStation);
        add(bogorStation);
        add(cirebonStation);
        add(depokStation);
        bandungStation.addActionListener(e ->{
            bandungStation();
        });

    }

    private void bandungStation(){
        this.setTitle("Bandung Station Schedule");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }
}
