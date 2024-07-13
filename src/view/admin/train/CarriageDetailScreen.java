package view.admin.train;

import controller.AuthenticationHelper;
import controller.CarriageController;
import model.classes.Carriage;
import view.guest.Login;

import javax.swing.*;
import java.awt.*;

public class CarriageDetailScreen extends JFrame {
    private final CarriageController carriageController = new CarriageController();

    public CarriageDetailScreen(Carriage carriage) {
        this.setTitle("Carriage Detail");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        addCarriageDetails(mainPanel, carriage);

        this.add(mainPanel);
        
        int userId = AuthenticationHelper.getInstance().getUserId();
        if (userId == 0) {
            this.dispose();
            new Login();
        } else {
            this.setVisible(true);
        }
    }

    private void addCarriageDetails(JPanel panel, Carriage carriage) {
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new GridLayout(5, 2, 10, 10));
        detailPanel.setBackground(Color.WHITE);

        detailPanel.add(createLabel("Carriage ID"));
        detailPanel.add(createValueLabel(String.valueOf(carriage.getId())));

        detailPanel.add(createLabel("Train ID"));
        detailPanel.add(createValueLabel(String.valueOf(carriage.getTrain_id())));

        detailPanel.add(createLabel("Carriage Type"));
        detailPanel.add(createValueLabel(carriage.getType().toString()));

        detailPanel.add(createLabel("Class Type"));
        detailPanel.add(createValueLabel(carriage.getType().toString()));

        detailPanel.add(createLabel("Capacity"));
        detailPanel.add(createValueLabel(String.valueOf(carriage.getCapacity())));

        detailPanel.add(createLabel("Baggage Allowance"));
        detailPanel.add(createValueLabel(String.valueOf(carriage.getBaggageAllowance())));

        panel.add(detailPanel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.BOLD, 18));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.PLAIN, 18));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }
}
