package model.classes;

import model.enums.ClassType;

import java.sql.Timestamp;

public class TicketTransaction extends Transaction {
    private int passengers;
    private boolean commute;
    private Integer schdeuleID;
    private boolean rescheduled;
    private ClassType type;

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

    public ClassType getType() {
        return type;
    }

    public void setType(ClassType type) {
        this.type = type;
    }
}
