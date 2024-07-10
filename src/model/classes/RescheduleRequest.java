package model.classes;

import model.enums.RescheduleEnum;

public class RescheduleRequest {
    private int rescheduleID;
    private int transactionID;
    private int adminID;
    private int requestedScheduleID;
    private RescheduleEnum status;

    public RescheduleRequest(int rescheduleID, int transactionID, int requestedScheduleID, RescheduleEnum status) {
        this.rescheduleID = rescheduleID;
        this.transactionID = transactionID;
        this.requestedScheduleID = requestedScheduleID;
        this.status = status;
    }

    public int getRescheduleID() {
        return rescheduleID;
    }

    public void setRescheduleID(int rescheduleID) {
        this.rescheduleID = rescheduleID;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public int getRequestedScheduleID() {
        return requestedScheduleID;
    }

    public void setRequestedScheduleID(int requestedScheduleID) {
        this.requestedScheduleID = requestedScheduleID;
    }

    public RescheduleEnum getStatus() {
        return status;
    }

    public void setStatus(RescheduleEnum status) {
        this.status = status;
    }
}
