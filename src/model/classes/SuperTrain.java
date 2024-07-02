package model.classes;

public class SuperTrain extends Train {
    private String wifiName;
    private String wifiPassword;

    public SuperTrain(Integer id, Carriage[] carriages, Integer speed, String wifiName, String wifiPassword) {
        super(id, carriages, speed);
        this.wifiName = wifiName;
        this.wifiPassword = wifiPassword;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getWifiPassword() {
        return wifiPassword;
    }

    public void setWifiPassword(String wifiPassword) {
        this.wifiPassword = wifiPassword;
    }
}
