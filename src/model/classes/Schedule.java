package model.classes;

import java.util.Date;

public class Schedule {
    Integer scheduleID;
    Integer train_id;
    Integer departure;
    Integer arrival;
    Date departureDate;
    double fee;

    public Schedule(Integer scheduleID, Integer train_id, Integer departure, Integer arrival, Date departureDate, double fee) {
        this.scheduleID = scheduleID;
        this.train_id = train_id;
        this.departure = departure;
        this.arrival = arrival;
        this.departureDate = departureDate;
        this.fee = fee;
    }

    public Integer getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Integer scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Integer getTrainID() {
        return train_id;
    }

    public void setInteger(Integer train_id) {
        this.train_id = train_id;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Integer getArrival() {
        return arrival;
    }

    public void setArrival(Integer arrival) {
        this.arrival = arrival;
    }

    public Integer getDeparture() {
        return departure;
    }

    public void setDeparture(Integer departure) {
        this.departure = departure;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
