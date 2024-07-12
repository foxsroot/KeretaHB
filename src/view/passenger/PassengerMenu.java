package view.passenger;

import javax.swing.*;

public class PassengerMenu extends JFrame {
    public PassengerMenu() {
        this.setVisible(true);
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Menu");
    }

    public static void main(String[] args) {
        new PassengerMenu();
    }
}