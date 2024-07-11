package model.classes;

import model.enums.VictualTransactionStatus;

import java.sql.Timestamp;
import java.util.HashMap;

public class VictualTransaction extends Transaction {
    private HashMap<Integer, Integer> items; //id, quantity
    private Integer stationID;
    private VictualTransactionStatus status;

    public VictualTransaction(Integer transactionID, Timestamp datePurchase, HashMap<Integer, Integer> items, Integer stationID, double total, VictualTransactionStatus status) {
        super(transactionID, datePurchase, total);
        this.items = items;
        this.stationID = stationID;
        this.status = status;
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

    public VictualTransactionStatus getStatus() {
        return status;
    }

    public void setStatus(VictualTransactionStatus status) {
        this.status = status;
    }
}
