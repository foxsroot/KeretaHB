package view.admin.train;

import model.classes.Schedule;
import view.admin.AdminMenu;
import view.admin.schedule.AddEditScheduleScreen;
import view.admin.schedule.RescheduleRequestListScreen;
import view.admin.schedule.ScheduleDetailScreen;

import javax.swing.*;
import java.awt.*;

public class MenuTrain extends JFrame {
	public MenuTrain() {
		this.setVisible(true);
		this.setSize(900, 700);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle("Menu Schedule");

		JLabel screenTitle = new JLabel("List Menu Train");
		screenTitle.setFont(new Font("calibri", Font.BOLD, 40));
		screenTitle.setBounds(300, 30, 800, 50);

		JPanel listMenu = new JPanel();
		listMenu.setLayout(null);
		listMenu.setBounds(10, 100, 880, 680);

		JButton modifySchedule = new JButton("Modify Carriage");
		modifySchedule.setBounds(0, 0, 860, 40);
		listMenu.add(modifySchedule);

		modifySchedule.addActionListener(e -> {
			this.dispose();
			new AddEditCarriageScreen(null);
		});

		JButton viewRescheduleRequest = new JButton("View Carriage Detail");
		viewRescheduleRequest.setBounds(0, 50, 860, 40);
		listMenu.add(viewRescheduleRequest);

		viewRescheduleRequest.addActionListener(e -> {
			this.dispose();
			new CarriageDetailScreen(null);
		});

		JButton viewScheduleDetail = new JButton("Modify Train");
		viewScheduleDetail.setBounds(0, 100, 860, 40);
		listMenu.add(viewScheduleDetail);

		viewScheduleDetail.addActionListener(e -> {
			this.dispose();
			new AddEditTrainScreen(null);
		});

		JButton assignRevokeCarriage = new JButton("Assign Revoke Carriage");
		assignRevokeCarriage.setBounds(0, 150, 860, 40);
		listMenu.add(assignRevokeCarriage);

		assignRevokeCarriage.addActionListener(e -> {
			this.dispose();
			new AssignRevokeCarriageScreen(null);
		});

		JButton viewTrainList = new JButton("View Train List");
		viewTrainList.setBounds(0, 200, 860, 40);
		listMenu.add(viewTrainList);

		viewTrainList.addActionListener(e -> {
			this.dispose();
			new TrainListScreen();
		});

		JButton viewTrainDetail = new JButton("View Train Detail");
		viewTrainDetail.setBounds(0, 250, 860, 40);
		listMenu.add(viewTrainDetail);

		viewTrainDetail.addActionListener(e -> {
			this.dispose();
			new TrainDetailScreen(null);
		});

		JButton back = new JButton("Back");
		back.setBounds(0, 300, 860, 40);
		listMenu.add(back);

		back.addActionListener(e -> {
			this.dispose();
			new AdminMenu();
		});

		add(screenTitle);
		add(listMenu);
	}
}
