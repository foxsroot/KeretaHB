package model.classes;

import java.sql.Timestamp;

public class TicketTransaction extends Transaction {
    int passengers;
    boolean commute;
    Integer schdeuleID;
    boolean rescheduled;

    public TicketTransaction(Integer transactionID, Timestamp datePurchase, int passengers, boolean commute, Integer schdeuleID, boolean rescheduled, double total) {
        super(transactionID, datePurchase, total);
        this.passengers = passengers;
        this.commute = commute;
        this.schdeuleID = schdeuleID;
        this.rescheduled = rescheduled;
    }

    public TicketTransaction() {

    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public boolean isCommute() {
        return commute;
    }

    public void setCommute(boolean commute) {
        this.commute = commute;
    }

    public Integer getSchdeuleID() {
        return schdeuleID;
    }

    public void setSchdeuleID(Integer schdeuleID) {
        this.schdeuleID = schdeuleID;
    }

    public boolean isRescheduled() {
        return rescheduled;
    }

    public void setRescheduled(boolean rescheduled) {
        this.rescheduled = rescheduled;
    }
}
