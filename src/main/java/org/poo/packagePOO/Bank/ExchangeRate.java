package org.poo.packagePOO.Bank;

public class ExchangeRate {
    private final String from;
    private final String to;
    private final double rate;

    public ExchangeRate(String from, String to, double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getRate() {
        return rate;
    }

}
