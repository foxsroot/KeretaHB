package model.classes;

public class SuperTrain extends Train {
    private String wifiName;
    private String wifiPassword;

    public SuperTrain(Carriage[] carriages, Integer speed, Victual victual, String wifiName, String wifiPassword) {
        super(carriages, speed, victual);
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
