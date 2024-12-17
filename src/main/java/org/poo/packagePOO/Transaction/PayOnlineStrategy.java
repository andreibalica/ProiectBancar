package org.poo.packagePOO.Transaction;

import org.poo.packagePOO.Bank.Account.BankAccount;
import org.poo.packagePOO.Bank.Account.TransactionsHistory.TransactionFactory;
import org.poo.packagePOO.Bank.Card;
import org.poo.packagePOO.CurrencyConverter;
import org.poo.packagePOO.GlobalManager;
import org.poo.utils.Utils;

public final class PayOnlineStrategy implements TransactionStrategy {
    private final String cardNumber;
    private final double amount;
    private final String currency;
    private final String description;
    private final String commerciant;
    private final String email;
    private final int timestamp;
    private String error;

    /**
     *
     * @param cardNumber
     * @param amount
     * @param currency
     * @param description
     * @param commerciant
     * @param email
     * @param timestamp
     */
    public PayOnlineStrategy(final String cardNumber, final double amount,
                             final String currency, final String description,
                             final String commerciant, final String email,
                             final int timestamp) {
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.commerciant = commerciant;
        this.email = email;
        this.timestamp = timestamp;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean validate() {
        for (BankAccount account : GlobalManager.getGlobal().getBank().getAccounts()) {
            for (Card card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    if (!card.getEmail().equals(email)) {
                        error = "Card not owned by user";
                        return false;
                    }
                    if (card.getStatus().equals("frozen")) {
                        account.addTransactionHistory(TransactionFactory
                                .createErrorTransaction(timestamp, "The card is frozen"));
                        return false;
                    }
                    return true;
                }
            }
        }
        error = "Card not found";
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean process() {
        for (BankAccount account : GlobalManager.getGlobal().getBank().getAccounts()) {
            for (Card card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    double amountConvert;
                    try {
                        amountConvert = CurrencyConverter.getConverter().convert(
                                currency, account.getCurrency(), amount);
                    } catch (IllegalArgumentException e) {
                        return false;
                    }

                    if (account.getBalance() < amountConvert) {
                        account.addTransactionHistory(TransactionFactory
                                .createErrorTransaction(timestamp, "Insufficient funds"));
                        return false;
                    }

                    account.payAmount(amountConvert);
                    account.addTransactionHistory(TransactionFactory
                            .createOnlineTransaction(timestamp, "Card payment",
                                    cardNumber, amountConvert, account.getCurrency(),
                                    commerciant));
                    if (card.getUse() == 0) {
                        account.addTransactionHistory(TransactionFactory
                                .createCardTransaction(timestamp, cardNumber,
                                        email, account.getIBAN(), false));
                        account.deleteCard(card.getCardNumber());
                        Card newCard = new Card(email, Utils.generateCardNumber(),
                                account.getIBAN(), timestamp);
                        newCard.setUse(0);
                        account.addTransactionHistory(TransactionFactory
                                .createCardTransaction(timestamp,
                                        newCard.getCardNumber(), email,
                                        account.getIBAN(), true));
                        account.addCard(newCard);
                    }
                    return true;
                }
            }
        }
        error = "Card not found";
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public String getError() {
        return error;
    }
}
