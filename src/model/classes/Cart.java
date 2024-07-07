package model.classes;

import java.util.HashMap;

public class Cart {
    private HashMap<Integer, Integer> victual; //victual_id, qty
    private double totalPrice;
    private Integer stationId;

    public Cart(HashMap<Integer, Integer> victual, double totalPrice, Integer stationId) {
        this.victual = victual;
        this.totalPrice = totalPrice;
        this.stationId = stationId;
    }

    public Cart(HashMap<Integer, Integer> victual, Integer stationId) {
        this.victual = victual;
        this.totalPrice = calculateTotalPrice();
        this.stationId = stationId;
    }

    public HashMap<Integer, Integer> getVictual() {
        return victual;
    }

    public void setVictual(HashMap<Integer, Integer> victual) {
        this.victual = victual;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double calculateTotalPrice() {
        return 0d;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }
}
