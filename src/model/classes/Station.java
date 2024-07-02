package model.classes;

import java.util.ArrayList;
import java.util.HashMap;

public class Station {
    private ArrayList<Schedule> schedules;
    private HashMap<Integer, Integer> victualStock; //victual_id, stock
    private String name;
    private Integer id;
    private String location;
    private ArrayList<String> trainList;
    private double income;

    public Station(ArrayList<Schedule> schedules, String name, Integer id, String location, ArrayList<String> trainList, double income) {
        this.schedules = schedules;
        this.name = name;
        this.id = id;
        this.location = location;
        this.trainList = trainList;
        this.income = income;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<String> getTrainList() {
        return trainList;
    }

    public void setTrainList(ArrayList<String> trainList) {
        this.trainList = trainList;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
