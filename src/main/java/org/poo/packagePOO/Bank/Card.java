package org.poo.packagePOO.Bank;

public class Card {
    private final String email;
    private final String cardNumber;
    private final String IBAN;
    private final int timestamp;
    private String status;
    private int use;

    public Card(String email, String cardNumber, String IBAN, int timestamp) {
        this.email = email;
        this.cardNumber = cardNumber;
        this.IBAN = IBAN;
        this.timestamp = timestamp;
        this.status = "active";
        this.use = 1;
    }

    public void setUse(int use) {
        this.use = use;
    }

    public int getUse() {
        return use;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getIBAN() {
        return IBAN;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
