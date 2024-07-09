package model.classes;

import java.sql.Timestamp;
import java.util.HashMap;

public class VictualTransaction extends Transaction {
    HashMap<Integer, Integer> items; //id, quantity
    Integer stationID;

    public VictualTransaction(Integer transactionID, Timestamp datePurchase, HashMap<Integer, Integer> items, Integer stationID, double amount) {
        super(transactionID, datePurchase, amount);
        this.items = items;
        this.stationID = stationID;
    }

    public VictualTransaction() {}

    public HashMap<Integer, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<Integer, Integer> items) {
        this.items = items;
    }

    public Integer getStationID() {
        return stationID;
    }

    public void setStationID(Integer stationID) {
        this.stationID = stationID;
    }
}
