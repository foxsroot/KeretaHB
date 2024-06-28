package model.classes;

import model.enums.CarriageType;
import model.enums.ClassType;

public class Carriage {
    private Integer capacity;
    private CarriageType type;
    private Integer maxWeight;
    private Integer baggageAllowance;
    private ClassType carriageClass;

    public Carriage(Integer capacity, CarriageType type, Integer maxWeight, Integer baggageAllowance, ClassType carriageClass) {
        this.capacity = capacity;
        this.type = type;
        this.maxWeight = maxWeight;
        this.baggageAllowance = baggageAllowance;
        this.carriageClass = carriageClass;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public CarriageType getType() {
        return type;
    }

    public void setType(CarriageType type) {
        this.type = type;
    }

    public Integer getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Integer maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Integer getBaggageAllowance() {
        return baggageAllowance;
    }

    public void setBaggageAllowance(Integer baggageAllowance) {
        this.baggageAllowance = baggageAllowance;
    }

    public ClassType getCarriageClass() {
        return carriageClass;
    }

    public void setCarriageClass(ClassType carriageClass) {
        this.carriageClass = carriageClass;
    }
}
