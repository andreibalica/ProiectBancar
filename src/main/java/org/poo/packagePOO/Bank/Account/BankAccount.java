package org.poo.packagePOO.Bank.Account;

import org.poo.packagePOO.Bank.Account.TransactionsHistory.TransactionHistory;
import org.poo.packagePOO.Bank.Card;

import java.util.ArrayList;

public final class BankAccount {
    private final String email;
    private final String iban;
    private final String currency;
    private final String accountType;
    private final int timestamp;
    private double interestRate;
    private double balance = 0;
    private double minBalance = 0;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<TransactionHistory> transactionHistory = new ArrayList<>();

    private BankAccount(final BankAccountBuilder builder) {
        this.email = builder.email;
        this.iban = builder.iban;
        this.currency = builder.currency;
        this.accountType = builder.accountType;
        this.timestamp = builder.timestamp;
        this.interestRate = builder.interestRate;
    }

    public static final class BankAccountBuilder {
        private final String email;
        private final String iban;
        private final String currency;
        private final int timestamp;
        private String accountType;
        private double interestRate;

        /**
         *
         * @param email
         * @param iban
         * @param currency
         * @param timestamp
         */
        public BankAccountBuilder(final String email,
                                  final String iban,
                                  final String currency,
                                  final int timestamp) {
            this.email = email;
            this.iban = iban;
            this.currency = currency;
            this.timestamp = timestamp;
        }

        /**
         *
         * @param accountType
         * @return
         */
        public BankAccountBuilder setAccountType(final String accountType) {
            this.accountType = accountType;
            return this;
        }

        /**
         *
         * @param interestRate
         * @return
         */
        public BankAccountBuilder setInterestRate(final double interestRate) {
            if (!this.accountType.equals("savings")) {
                throw new IllegalArgumentException(
                        "Interest rate is only applicable for savings accounts."
                );
            }
            this.interestRate = interestRate;
            return this;
        }

        /**
         *
         * @return
         */
        public BankAccount build() {
            if (this.accountType == null) {
                throw new IllegalStateException("Account type must be specified!");
            }
            return new BankAccount(this);
        }
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return
     */
    public String getIBAN() {
        return iban;
    }

    /**
     *
     * @return
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @return
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     *
     * @return
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @return
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     *
     * @return
     */
    public double getBalance() {
        return balance;
    }

    /**
     *
     * @return
     */
    public double getMinBalance() {
        return minBalance;
    }

    /**
     *
     * @param minBalance
     */
    public void setMinBalance(final double minBalance) {
        this.minBalance = minBalance;
    }

    /**
     *
     * @return
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     *
     * @return
     */
    public ArrayList<TransactionHistory> getTransactionHistory() {
        return transactionHistory;
    }

    /**
     *
     * @param amount
     */
    public void addAmount(final double amount) {
        balance += amount;
    }

    /**
     *
     * @param amount
     */
    public void payAmount(final double amount) {
        balance -= amount;
    }

    /**
     *
     * @param card
     */
    public void addCard(final Card card) {
        cards.add(card);
    }

    /**
     *
     * @param card
     */
    public void removeCard(final Card card) {
        cards.remove(card);
    }

    /**
     *
     * @param cardNumber
     */
    public void deleteCard(final String cardNumber) {
        Card card = searchCard(cardNumber);
        if (card != null) {
            cards.remove(card);
        }
    }

    /**
     *
     * @param cardNumber
     * @return
     */
    public Card searchCard(final String cardNumber) {
        for (Card card : cards) {
            if (card.getCardNumber().equals(cardNumber)) {
                return card;
            }
        }
        return null;
    }

    /**
     *
     * @param transaction
     */
    public void addTransactionHistory(final TransactionHistory transaction) {
        transactionHistory.add(transaction);
    }

    /**
     *
     * @param newRate
     */
    public void setInterestRate(final double newRate) {
        if (!accountType.equals("savings")) {
            throw new IllegalStateException("This is not a savings account");
        }
        this.interestRate = newRate;
    }

    /**
     *
     */
    public void applyInterest() {
        if (!accountType.equals("savings")) {
            throw new IllegalStateException("This is not a savings account");
        }
        double interestAmount = balance * (interestRate / 100.0);
        balance += interestAmount;
    }
}
