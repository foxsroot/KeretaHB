package model.classes;

import java.util.Calendar;

public class Schedule {
    Integer scheduleID;
    Train train;
    Station departure;
    Station arrival;
    Calendar departureDate;
    double fee;

    public Schedule(Integer scheduleID, Train train, Station departure, Station arrival, Calendar departureDate, double fee) {
        this.scheduleID = scheduleID;
        this.train = train;
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

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Calendar getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Calendar departureDate) {
        this.departureDate = departureDate;
    }

    public Station getArrival() {
        return arrival;
    }

    public void setArrival(Station arrival) {
        this.arrival = arrival;
    }

    public Station getDeparture() {
        return departure;
    }

    public void setDeparture(Station departure) {
        this.departure = departure;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
