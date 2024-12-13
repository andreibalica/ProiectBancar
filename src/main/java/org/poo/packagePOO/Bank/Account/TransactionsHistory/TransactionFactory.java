package org.poo.packagePOO.Bank.Account.TransactionsHistory;

import java.util.ArrayList;

public class TransactionFactory {
    public static TransactionHistory createAccountCreationTransaction(int timestamp) {
        return new CreateAccountTransaction(timestamp, "New account created");
    }

    public static TransactionHistory createCardTransaction(int timestamp, String card,
                                                           String cardHolder, String account,
                                                           boolean isCreation) {
        String description;
        if (isCreation) {
            description = "New card created";
        } else {
            description = "The card has been destroyed";
        }
        return new CardCreateDeleteTransaction(timestamp, description, card, cardHolder, account);
    }

    public static TransactionHistory createTransferTransaction(int timestamp, String description,
                                                               String senderIBAN,
                                                               String receiverIBAN, double amount, String currency) {
        return new MoneyTransferTransaction(timestamp, description, senderIBAN, receiverIBAN, amount, currency);
    }

    public static TransactionHistory createOnlineTransaction(int timestamp, String description,
                                                             String cardNumber, double amount,
                                                             String currency, String commerciant) {
        return new CardPaymentTransaction(timestamp, description, cardNumber, amount, currency, commerciant);
    }

    public static TransactionHistory createErrorTransaction(int timestamp, String errorMessage) {
        return new ErrorTransaction(timestamp, errorMessage);
    }

    public static TransactionHistory createSplitPaymentTransaction(int timestamp, double totalAmount, double amount, String currency, ArrayList<String> accounts) {
        return new SplitPaymentsTransaction(timestamp, totalAmount, amount, currency, accounts);
    }
}
