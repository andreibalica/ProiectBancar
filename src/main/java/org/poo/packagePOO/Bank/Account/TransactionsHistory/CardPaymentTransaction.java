package org.poo.packagePOO.Bank.Account.TransactionsHistory;

public class CardPaymentTransaction extends TransactionHistory {
    private final String cardNumber;
    private final double amount;
    private final String currency;
    private final String commerciant;

    public CardPaymentTransaction(int timestamp, String description,
                                  String cardNumber, double amount,
                                  String currency, String commerciant) {
        super(timestamp, description);
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.commerciant = commerciant;
    }

    @Override
    public void accept(TransactionVisitor visitor) {
        visitor.visit(this);
    }

    public String getCardNumber() { return cardNumber; }
    public double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getCommerciant() { return commerciant; }
}
