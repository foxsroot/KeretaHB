package model.classes;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Victual> victuals;
    private double totalPrice;

    public Cart(ArrayList<Victual> victuals, double totalPrice) {
        this.victuals = victuals;
        this.totalPrice = totalPrice;
    }

    public ArrayList<Victual> getVictuals() {
        return victuals;
    }

    public void setVictuals(ArrayList<Victual> victuals) {
        this.victuals = victuals;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
