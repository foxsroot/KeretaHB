package model.classes;

import java.sql.Timestamp;

public abstract class Transaction {
    Integer transactionID;
    Timestamp datePurchase;
    double amount;

    public Transaction(Integer transactionID, Timestamp datePurchase, double amount) {
        this.transactionID = transactionID;
        this.datePurchase = datePurchase;
        this.amount = amount;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
