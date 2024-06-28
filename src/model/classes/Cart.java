package model.classes;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Victual> victuals;
    private Double totalPrice;

    public Cart(ArrayList<Victual> victuals, Double totalPrice) {
        this.victuals = victuals;
        this.totalPrice = totalPrice;
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
