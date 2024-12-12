package org.poo.packagePOO.Bank.Account;
import org.poo.packagePOO.Bank.Account.TransactionsHistory.TransactionHistory;
import org.poo.packagePOO.Bank.Card;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private final String email;
    private final String IBAN;
    private final String currency;
    private final String accountType;
    private final int timestamp;
    private final double interestRate;
    private double balance = 0;
    private double minBalance = 0;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<TransactionHistory> transactionHistory = new ArrayList<>();

    private BankAccount(BankAccountBuilder builder) {
        this.email = builder.email;
        this.IBAN = builder.IBAN;
        this.currency = builder.currency;
        this.accountType = builder.accountType;
        this.timestamp = builder.timestamp;
        this.interestRate = builder.interestRate;
    }

    public static class BankAccountBuilder {
        private final String email;
        private final String IBAN;
        private final String currency;
        private final int timestamp;
        private String accountType;
        private double interestRate;

        public BankAccountBuilder(String email, String IBAN, String currency, int timestamp) {
            this.email = email;
            this.IBAN = IBAN;
            this.currency = currency;
            this.timestamp = timestamp;
        }

        public BankAccountBuilder setAccountType(String accountType) {
            this.accountType = accountType;
            return this;
        }

        public BankAccountBuilder setInterestRate(double interestRate) {
            if (!this.accountType.equals("savings")) {
                throw new IllegalArgumentException("Interest rate is only applicable for savings accounts.");
            }
            this.interestRate = interestRate;
            return this;
        }

        public BankAccount build() {
            if (this.accountType == null) {
                throw new IllegalStateException("Account type must be specified!");
            }
            return new BankAccount(this);
        }

    }

    public String getEmail() {
        return email;
    }

    public String getIBAN() {
        return IBAN;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAccountType() {
        return accountType;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getBalance() {
        return balance;
    }

    public double getMinBalance() {return minBalance; }

    public void setMinBalance(double minBalance) {this.minBalance = minBalance;}

    public ArrayList<Card> getCards() {return cards;}

    public ArrayList<TransactionHistory> getTransactionHistory() {return transactionHistory;}

    public void addAmount(double amount) {
        balance += amount;
    }
    public void payAmount(double amount) {
        balance -= amount;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) { cards.remove(card);}

    public void deleteCard(String cardNumber) {
        Card card = searchCard(cardNumber);
        if (card != null) {
            cards.remove(card);
        }
    }

    public Card searchCard(String cardNumber) {
        for (Card card : cards) {
            if (card.getCardNumber().equals(cardNumber)) {
                return card;
            }
        }
        return null;
    }

    public void addTransactionHistory(TransactionHistory transaction) {
        transactionHistory.add(transaction);
    }

}
