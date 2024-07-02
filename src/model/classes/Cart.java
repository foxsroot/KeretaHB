package model.classes;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
    private HashMap<Integer, Integer> victual; //victual_id, amount
    private double totalPrice;

    public Cart(HashMap<Integer, Integer> victual, double totalPrice) {
        this.victual = victual;
        this.totalPrice = totalPrice;
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

    public void calculateTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
