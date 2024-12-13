package org.poo.packagePOO.Transaction;

import org.poo.packagePOO.Bank.Account.BankAccount;
import org.poo.packagePOO.Bank.Account.TransactionsHistory.TransactionFactory;
import org.poo.packagePOO.Bank.Card;
import org.poo.packagePOO.Command.DeleteCard;
import org.poo.packagePOO.CurrencyConverter;
import org.poo.packagePOO.GlobalManager;
import org.poo.utils.Utils;

public class PayOnlineStrategy implements TransactionStrategy {
    private final String cardNumber;
    private final double amount;
    private final String currency;
    private final String description;
    private final String commerciant;
    private final String email;
    private final int timestamp;
    private String error;

    public PayOnlineStrategy(String cardNumber, double amount, String currency,
                             String description, String commerciant, String email, int timestamp) {
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.commerciant = commerciant;
        this.email = email;
        this.timestamp = timestamp;
    }

    @Override
    public boolean validate() {
        for (BankAccount account : GlobalManager.getGlobal().getBank().getAccounts()) {
            for (Card card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    if (!card.getEmail().equals(email)) {
                        error = "Card not owned by user";
                        return false;
                    }
                    return true;
                }
            }
        }
        error = "Card not found";
        return false;
    }

    @Override
    public boolean process() {
        for (BankAccount account : GlobalManager.getGlobal().getBank().getAccounts()) {
            for (Card card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    double amountConvert;
                    try {
                        amountConvert = CurrencyConverter.getConverter().convert(currency, account.getCurrency(), amount);
                    } catch (IllegalArgumentException e) {
                        return false;
                    }

                    if (card.getStatus() == "frozen") {
                        account.addTransactionHistory(TransactionFactory.createErrorTransaction(timestamp, "The card is frozen"));
                        return false;
                    }

                    if (account.getBalance() < amountConvert) {
                        account.addTransactionHistory(TransactionFactory.createErrorTransaction(timestamp, "Insufficient funds"));
                        return false;
                    }

                    account.payAmount(amountConvert);
                    if(card.getUse()==0){
                        account.deleteCard(card.getCardNumber());
                        Card newCard = new Card(email, Utils.generateCardNumber(), account.getIBAN(), timestamp);
                        newCard.setUse(0);
                        account.addCard(newCard);
                    }

                    account.addTransactionHistory(TransactionFactory.createOnlineTransaction(timestamp, "Card payment", cardNumber, amountConvert, account.getCurrency(), commerciant));
                    return true;
                }
            }
        }
        error = "Card not found";
        return false;
    }

    @Override
    public String getError() {
        return error;
    }
}