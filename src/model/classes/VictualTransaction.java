package model.classes;

import java.util.Date;

public class VictualTransaction extends Transaction {
    Integer victual_id;
    int quantity;

    public VictualTransaction(Integer transactionID, Date datePurchase, Integer item, int quantity) {
        super(transactionID, datePurchase);
        this.victual_id = item;
        this.quantity = quantity;
    }

    public Integer getVictual_id() {
        return victual_id;
    }

    public void setVictual_id(Integer victual_id) {
        this.victual_id = victual_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
