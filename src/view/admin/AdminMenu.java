package view.admin;

import model.classes.Schedule;
import view.admin.schedule.AddScheduleScreen;

import javax.swing.*;

public class AdminMenu extends JFrame {
    public AdminMenu() {
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Schedule schedule = new Schedule(null, null, null,null, 0);
        AddScheduleScreen addScheduleScreen = new AddScheduleScreen();
    }
}
