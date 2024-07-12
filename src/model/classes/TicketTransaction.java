package model.classes;

import model.enums.ClassType;

import java.sql.Timestamp;

public class TicketTransaction extends Transaction {
    private int passengers;
    private boolean commute;
    private Integer scheduleID;
    private boolean rescheduled;
    private ClassType type;

    public TicketTransaction(Integer transactionID, Timestamp datePurchase, int passengers, boolean commute, Integer scheduleID, boolean rescheduled, double total) {
        super(transactionID, datePurchase, total);
        this.passengers = passengers;
        this.commute = commute;
        this.scheduleID = scheduleID;
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

    public Integer getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Integer scheduleID) {
        this.scheduleID = scheduleID;
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
