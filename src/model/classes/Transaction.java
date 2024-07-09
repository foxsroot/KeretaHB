package model.classes;

import java.sql.Timestamp;

public abstract class Transaction {
    Integer transactionID;
    Timestamp datePurchase;
    double total;

    public Transaction(Integer transactionID, Timestamp datePurchase, double total) {
        this.transactionID = transactionID;
        this.datePurchase = datePurchase;
        this.total = total;
    }

    public Transaction() {}

    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public Timestamp getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(Timestamp datePurchase) {
        this.datePurchase = datePurchase;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
