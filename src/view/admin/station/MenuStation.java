package view.admin.station;

import view.admin.AdminMenu;

import javax.swing.*;
import java.awt.*;

public class MenuStation extends JFrame {
	public MenuStation() {
		this.setVisible(true);
		this.setSize(900, 700);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle("Menu Station");

		JLabel screenTitle = new JLabel("List Menu Station");
		screenTitle.setFont(new Font("calibri", Font.BOLD, 40));
		screenTitle.setBounds(300, 30, 800, 50);

		JPanel listMenu = new JPanel();
		listMenu.setLayout(null);
		listMenu.setBounds(10, 100, 880, 680);

		JButton viewStationSchedule = new JButton("View Station List");
		viewStationSchedule.setBounds(0, 0, 860, 40);
		listMenu.add(viewStationSchedule);

		viewStationSchedule.addActionListener(e -> {
			this.dispose();
			new StationListScreen();
		});

//		JButton viewStationDetail = new JButton("View Station Detail");
//		viewStationDetail.setBounds(0, 50, 860, 40);
//		listMenu.add(viewStationDetail);
//
//		viewStationDetail.addActionListener(e -> {
//			this.dispose();
//			new StationDetailScreen(new Station(null, null, null, "", "", null, 0, ""));
//		});

		JButton back = new JButton("Back");
		back.setBounds(0, 50, 860, 40);
		listMenu.add(back);

		back.addActionListener(e -> {
			this.dispose();
			new AdminMenu();
		});

		add(screenTitle);
		add(listMenu);
	}
}
