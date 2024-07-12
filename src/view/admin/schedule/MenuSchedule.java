package view.admin.schedule;

import model.classes.Schedule;
import view.admin.AdminMenu;
import view.admin.loyalty.LoyaltyManagementScreen;

import javax.swing.*;
import java.awt.*;

public class MenuSchedule extends JFrame {
	public MenuSchedule() {
		this.setVisible(true);
		this.setSize(900, 700);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle("Menu Schedule");

		JLabel screenTitle = new JLabel("List Menu Schedule");
		screenTitle.setFont(new Font("calibri", Font.BOLD, 40));
		screenTitle.setBounds(300, 30, 800, 50);

		JPanel listMenu = new JPanel();
		listMenu.setLayout(null);
		listMenu.setBounds(10, 100, 880, 680);

		JButton modifySchedule = new JButton("Modify Schedule");
		modifySchedule.setBounds(0, 0, 860, 40);
		listMenu.add(modifySchedule);

		modifySchedule.addActionListener(e -> {
			this.dispose();
			new EditScheduleScreen(new Schedule(null, null, null,null, 0));
		});

		JButton viewRescheduleRequest = new JButton("View Reschedule Request");
		viewRescheduleRequest.setBounds(0, 50, 860, 40);
		listMenu.add(viewRescheduleRequest);

		viewRescheduleRequest.addActionListener(e -> {
			this.dispose();
			new RescheduleRequestListScreen();
		});

		JButton viewScheduleDetail = new JButton("View Schedule List");
		viewScheduleDetail.setBounds(0, 100, 860, 40);
		listMenu.add(viewScheduleDetail);

		viewScheduleDetail.addActionListener(e -> {
			this.dispose();
			new StationScheduleSelection();
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
