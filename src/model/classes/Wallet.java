package model.classes;

public class Wallet {
    private Integer wallet_id;
    private Double balance;
    private String pin;

    public Wallet(Integer wallet_id, Double balance, String pin) {
        this.wallet_id = wallet_id;
        this.balance = balance;
        this.pin = pin;
    }

    public Integer getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(Integer wallet_id) {
        this.wallet_id = wallet_id;
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
    
    public void addBalance(Double newBalance) {
        this.balance += newBalance;
    }
    
    public boolean decreaseBalance(Double reduceBalance) {
        if (this.balance < reduceBalance) return false;
        this.balance -= reduceBalance;
        return true;
    }
    
    public boolean verifyPin(String pin) {
        return this.pin.equals(pin);
    }
}
