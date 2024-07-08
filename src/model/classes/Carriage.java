package model.classes;

import model.enums.CarriageType;
import model.enums.ClassType;

public class Carriage {
    private Integer id;
    private Integer train_id;
    private int capacity;
    private CarriageType type;
    private int baggageAllowance;
    private ClassType carriageClass;

    public Carriage(Integer train_id, int capacity, CarriageType type, int baggageAllowance, ClassType carriageClass) {
        this.train_id = train_id;
        this.capacity = capacity;
        this.type = type;
        this.baggageAllowance = baggageAllowance;
        this.carriageClass = carriageClass;
    }

    public Carriage addId(Integer id){
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrain_id() {
        return train_id;
    }

    public void setTrain_id(Integer train_id) {
        this.train_id = train_id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public CarriageType getType() {
        return type;
    }

    public void setType(CarriageType type) {
        this.type = type;
    }

    public int getBaggageAllowance() {
        return baggageAllowance;
    }

    public void setBaggageAllowance(int baggageAllowance) {
        this.baggageAllowance = baggageAllowance;
    }

    public ClassType getCarriageClass() {
        return carriageClass;
    }

    public void setCarriageClass(ClassType carriageClass) {
        this.carriageClass = carriageClass;
    }
}
