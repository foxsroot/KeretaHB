package model.classes;

import java.util.Date;

public abstract class Transaction {
    Integer transactionID;
    Date datePurchase;

    public Transaction(Integer transactionID, Date datePurchase) {
        this.transactionID = transactionID;
        this.datePurchase = datePurchase;
    }

    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public Date getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(Date datePurchase) {
        this.datePurchase = datePurchase;
    }
}
