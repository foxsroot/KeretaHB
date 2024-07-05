package view.passenger;

import javax.swing.*;
import java.awt.*;

public class ListVictualScreen extends JFrame {
    public ListVictualScreen() {
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Victual List");

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setBounds(100, 10, 500, 50);

        JTextField searchField = new JTextField();
        searchField.setBounds(0, 0, 150, 30);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Calibri", Font.BOLD, 13));
        searchButton.setBounds(155, 0, 80, 30);

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        this.add(searchPanel);
    }

    public static void main(String[] args) {
        new ListVictualScreen();
    }
}
