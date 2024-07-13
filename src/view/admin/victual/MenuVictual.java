package view.admin.victual;

import controller.AuthenticationHelper;
import view.admin.AdminMenu;
import view.guest.Login;

import javax.swing.*;
import java.awt.*;

public class MenuVictual extends JFrame {
	public MenuVictual() {
        int userId = AuthenticationHelper.getInstance().getUserId();
        if (userId == 0) {
            this.dispose();
            new Login();
        } else {
            this.setVisible(true);
        }
		this.setSize(900, 700);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle("Menu Victual");

		JLabel screenTitle = new JLabel("List Menu Victual");
		screenTitle.setFont(new Font("calibri", Font.BOLD, 40));
		screenTitle.setBounds(300, 30, 800, 50);

		JPanel listMenu = new JPanel();
		listMenu.setLayout(null);
		listMenu.setBounds(10, 100, 880, 680);

		JButton modifyVictual = new JButton("Add/Edit Victual");
		modifyVictual.setBounds(0, 0, 860, 40);
		listMenu.add(modifyVictual);

		modifyVictual.addActionListener(e -> {
			this.dispose();
			new AddVictualScreen();
		});

		JButton viewVictualList = new JButton("View Victual List");
		viewVictualList.setBounds(0, 50, 860, 40);
		listMenu.add(viewVictualList);

		viewVictualList.addActionListener(e -> {
			this.dispose();
			new ListVictualScreen();
		});

		JButton editStockFromStation = new JButton("Edit Stock from Station");
		editStockFromStation.setBounds(0, 100, 860, 40);
		listMenu.add(editStockFromStation);

		editStockFromStation.addActionListener(e -> {
			this.dispose();
			new ListStationScreen();
		});

		JButton back = new JButton("Back");
		back.setBounds(0, 150, 860, 40);
		listMenu.add(back);

		back.addActionListener(e -> {
			this.dispose();
			new AdminMenu();
		});

		add(screenTitle);
		add(listMenu);
	}
}
