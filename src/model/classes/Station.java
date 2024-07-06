package model.classes;

import java.util.ArrayList;
import java.util.HashMap;

public class Station {
    private Integer id;
    private ArrayList<Schedule> schedules;
    private HashMap<Victual, Integer> victual; //victual_id, stock
    private String name;
    private String location;
    private ArrayList<Train> trainList;
    private double income;

    public Station(Integer id, ArrayList<Schedule> schedules, HashMap<Victual, Integer> victual, String name, String location, ArrayList<Train> trainList, double income) {
        this.id = id;
        this.schedules = schedules;
        this.victual = victual;
        this.name = name;
        this.location = location;
        this.trainList = trainList;
        this.income = income;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }

    public HashMap<Victual, Integer> getVictual() {
        return victual;
    }

    public void setVictual(HashMap<Victual, Integer> victual) {
        this.victual = victual;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<Train> getTrainList() {
        return trainList;
    }

    public void setTrainList(ArrayList<Train> trainList) {
        this.trainList = trainList;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
