package model.classes;

public abstract class Train {
    private Carriage[] carriages;
    private Integer speed;
    private Victual victual;

    public Train(Carriage[] carriages, Integer speed, Victual victual) {
        this.carriages = carriages;
        this.speed = speed;
        this.victual = victual;
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

    public Victual getVictual() {
        return victual;
    }

    public void setVictual(Victual victual) {
        this.victual = victual;
    }
}
