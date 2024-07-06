package view.passenger.transaction;

import model.classes.Victual;

import javax.swing.*;

public class VictualCheckoutScreen extends JFrame {
    Victual victual;
    int quantity;

    public VictualCheckoutScreen(Victual victual, int quantity) {
        this.victual = victual;
        this.quantity = quantity;

        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        //pake dummy passenger dulu, ntar diubah ke user manager pake singleton(?)

    }

    public static void main(String[] args) {
//        new VictualCheckoutScreen();
    }
}
