package model.classes;

public class Train {
    private Integer id;
    private Carriage[] carriages;
    private Integer speed;

    public Train(Integer id, Carriage[] carriages, Integer speed) {
        this.id = id;
        this.carriages = carriages;
        this.speed = speed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSpeed(Integer speed) {
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
