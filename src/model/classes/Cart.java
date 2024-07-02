package model.classes;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
    private ArrayList<Victual> victuals;
    private HashMap<Victual, Integer> counts;

    private double totalPrice;

    public Cart(ArrayList<Victual> victuals, Double totalPrice) {
        this.victuals = victuals;
        this.totalPrice = totalPrice;
    }

    public Cart(ArrayList<Victual> victuals) {
        this.victuals = victuals;
    }

    public ArrayList<Victual> getVictuals() {
        return victuals;
    }

    public void setVictuals(ArrayList<Victual> victuals) {
        this.victuals = victuals;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
