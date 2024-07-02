package model.classes;

import java.util.Date;

public class VictualTransaction extends Transaction {
    Victual item;
    int quantity;

    public VictualTransaction(Integer transactionID, Date datePurchase, Victual item, int quantity) {
        super(transactionID, datePurchase);
        this.item = item;
        this.quantity = quantity;
    }

    public Victual getItem() {
        return item;
    }

    public void setItem(Victual item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
