package model.classes;

import java.sql.Timestamp;
import java.util.Date;

public class Schedule {
    Integer scheduleID;
    Integer train_id;
    Integer departure;
    Integer arrival;
    Timestamp departureDate;
    double fee;

    public Schedule(Integer train_id, Integer departure, Integer arrival, Timestamp departureDate, double fee) {
        this.train_id = train_id;
        this.departure = departure;
        this.arrival = arrival;
        this.departureDate = departureDate;
        this.fee = fee;
    }

    // Intention : Builder
    public Schedule addScheduleID(Integer scheduleID) {
        this.scheduleID = scheduleID;
        return this;
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

    public Timestamp getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Timestamp departureDate) {
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
