package view;

import model.classes.Victual;

import javax.swing.*;

public class ViewVictualScreen extends JFrame {
    Victual victual;

    public ViewVictualScreen(Victual victual) {
        this.victual = victual;
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {

    }
}
