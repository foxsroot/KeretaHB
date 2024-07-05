package model.classes;

import java.sql.Timestamp;

public class Notification {
    private Integer notificationID;
    private Integer recipientID;
    private String title;
    private String message;
    private Timestamp receivedDate;

    public Notification(Integer notificationID, Integer recipientID, String title, String message, Timestamp receivedDate) {
        this.notificationID = notificationID;
        this.recipientID = recipientID;
        this.title = title;
        this.message = message;
        this.receivedDate = receivedDate;
    }

    public Notification() {}

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Timestamp receivedDate) {
        this.receivedDate = receivedDate;
    }
}
