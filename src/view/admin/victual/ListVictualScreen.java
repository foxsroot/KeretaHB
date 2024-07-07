package view.admin.victual;

import config.DirectoryConfig;
import controller.ImageController;
import controller.VictualController;
import model.classes.Victual;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Edit Victual");

        JLabel screenTitle = new JLabel("Victual List");
        screenTitle.setFont(new Font("calibri", Font.BOLD, 20));
        screenTitle.setBounds(380, 10, 200, 30);

        VictualController controller = new VictualController();
        List<Victual> victuals = controller.getVictualList();

        JPanel victualListPanel = new JPanel();
        victualListPanel.setLayout(null);
        victualListPanel.setBounds(30, 60, 880, 550);

        int yOffset = 10;
        int xOffset = 10;
        int counter = 0;

        for (Victual victual : victuals) {
            if (counter != 0 && counter % 2 == 0) {
                xOffset = 10;
                yOffset += 160;
            }

            victualListPanel.add(createVictualPanel(victual, xOffset, yOffset));

            counter++;
            xOffset += 410;
        }

        victualListPanel.setPreferredSize(new Dimension(800, yOffset + 160));

        JScrollPane scrollPane = new JScrollPane(victualListPanel);
        scrollPane.setBounds(20, 50, 850, 530);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton exitButton = new JButton("Exit to Main Menu");
        exitButton.setBounds(20, 600, 150, 30);
        exitButton.addActionListener(e -> {
            dispose();
            //back to main menu
        });

        add(screenTitle);
        add(scrollPane);
        add(exitButton);
    }

    private JPanel createVictualPanel(Victual victual, int xOffset, int yOffset) {
        JPanel victualPanel = new JPanel();
        victualPanel.setLayout(null);
        victualPanel.setBackground(Color.WHITE);
        victualPanel.setBounds(xOffset, yOffset, 400, 150);

        JLabel image = new JLabel();
        image.setBounds(10, 0, 120, 150);
        image.setIcon(new ImageIcon(ImageController.resizeImage(DirectoryConfig.VICTUAL_IMAGES + victual.getImage(), 120, 135)));
        victualPanel.add(image);

        JLabel name = new JLabel(victual.getName());
        name.setFont(new Font("calibri", Font.BOLD, 20));
        name.setBounds(140, 10, 200, 30);
        victualPanel.add(name);

        JLabel price = new JLabel("Rp " + victual.getPrice());
        price.setFont(new Font("calibri", Font.PLAIN, 20));
        price.setBounds(140, 40, 200, 30);
        victualPanel.add(price);

        JButton editButton = new JButton("Edit");
        editButton.setBounds(140, 90, 70, 30);
        victualPanel.add(editButton);

        //tambahin removeButton
        JButton removeButton = new JButton("Remove Victual");
        removeButton.setBackground(Color.RED);
        removeButton.setForeground(Color.WHITE);
        removeButton.setBounds(215, 90, 130, 30);
        victualPanel.add(removeButton);

        editButton.addActionListener(e -> {
            dispose();
            new EditVictualScreen(victual);
        });

        return victualPanel;
    }

    public static void main(String[] args) {
        new ListVictualScreen();
    }
}
