package model.classes;

import java.util.Date;
import java.util.HashMap;

public class VictualTransaction extends Transaction {
    HashMap<Integer, Integer> items; //id, quantity
    Integer station_id;

    public VictualTransaction(Integer transactionID, Date datePurchase, HashMap<Integer, Integer> items, Integer station_id) {
        super(transactionID, datePurchase);
        this.items = items;
        this.station_id = station_id;
    }

    public HashMap<Integer, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<Integer, Integer> items) {
        this.items = items;
    }

    public Integer getStation_id() {
        return station_id;
    }

    public void setStation_id(Integer station_id) {
        this.station_id = station_id;
    }
}
