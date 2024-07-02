package model.classes;

public class Train {
    private Carriage[] carriages;
    private Integer speed;

    public Train(Carriage[] carriages, Integer speed) {
        this.carriages = carriages;
        this.speed = speed;
    }

    public Carriage[] getCarriages() {
        return carriages;
    }

    public void setCarriages(Carriage[] carriages) {
        this.carriages = carriages;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
