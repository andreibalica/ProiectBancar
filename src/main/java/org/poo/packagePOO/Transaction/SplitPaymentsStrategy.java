package org.poo.packagePOO.Transaction;

import org.poo.packagePOO.Bank.Account.BankAccount;
import org.poo.packagePOO.Bank.Account.TransactionsHistory.TransactionFactory;
import org.poo.packagePOO.CurrencyConverter;
import org.poo.packagePOO.GlobalManager;

import java.util.ArrayList;

public class SplitPaymentsStrategy implements TransactionStrategy {
    private final ArrayList<String> accounts;
    private final double totalAmount;
    private final String currency;
    private final int timestamp;
    double convertedAmount;
    private String error;

    public SplitPaymentsStrategy(ArrayList<String> accounts, double totalAmount,
                                String currency, int timestamp) {
        this.accounts = accounts;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.timestamp = timestamp;
    }

    @Override
    public boolean validate() {
        double amountPerParticipant = totalAmount / accounts.size();

        for (String accountIBAN : accounts) {
            BankAccount account = GlobalManager.getGlobal().getBank().getAccountIBAN(accountIBAN);
            if (account == null) {
                error = "Account not found";
                return false;
            }

            convertedAmount = amountPerParticipant;
            try{
                convertedAmount = CurrencyConverter.getConverter().convert(currency, account.getCurrency(), amountPerParticipant);
            }catch (IllegalArgumentException e){
                return false;
            }

            if (account.getBalance() < convertedAmount) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean process() {
        double amountPerParticipant = totalAmount / accounts.size();

        for (String accountIBAN : accounts) {
            BankAccount account = GlobalManager.getGlobal().getBank().getAccountIBAN(accountIBAN);

            convertedAmount = amountPerParticipant;
            try{
                convertedAmount = CurrencyConverter.getConverter().convert(currency, account.getCurrency(), amountPerParticipant);
            }catch (IllegalArgumentException e){
                return false;
            }

            account.payAmount(convertedAmount);
            account.addTransactionHistory(
                    TransactionFactory.createSplitPaymentTransaction(
                            timestamp,
                            totalAmount,
                            amountPerParticipant,
                            currency,
                            accounts
                    )
            );
        }
        return true;
    }

    @Override
    public String getError() {
        return error;
    }
}