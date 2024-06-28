package model.classes;

public class Wallet {
    private Double balance;
    private String pin;

    public Wallet(String pin) {
        this.balance = 0.0;
        this.pin = pin;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
