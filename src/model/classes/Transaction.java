package model.classes;

import java.util.Calendar;

public abstract class Transaction {
    Integer transactionID;
    Calendar datePurchase;

    public Transaction(Integer transactionID, Calendar datePurchase) {
        this.transactionID = transactionID;
        this.datePurchase = datePurchase;
    }

    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public Calendar getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(Calendar datePurchase) {
        this.datePurchase = datePurchase;
    }
}
