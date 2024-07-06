package model.classes;

import java.util.HashMap;

public class Cart {
    private HashMap<Integer, Integer> victual; //victual_id, qty
    private double totalPrice;


    public Cart(HashMap<Integer, Integer> victual, double totalPrice) {
        this.victual = victual;
        this.totalPrice = totalPrice;
    }

    public Cart(HashMap<Integer, Integer> victual) {
        this.victual = victual;
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
}
