package model.classes;

import java.util.Calendar;

public class Notification {
    Integer notificationID;
    Integer recipientID;
    String message;
    Calendar receivedDate;

    public Notification(Integer notificationID, Integer recipientID, String message, Calendar receivedDate) {
        this.notificationID = notificationID;
        this.recipientID = recipientID;
        this.message = message;
        this.receivedDate = receivedDate;
    }

    public Integer getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(Integer notificationID) {
        this.notificationID = notificationID;
    }

    public Integer getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(Integer recipientID) {
        this.recipientID = recipientID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Calendar getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Calendar receivedDate) {
        this.receivedDate = receivedDate;
    }
}
