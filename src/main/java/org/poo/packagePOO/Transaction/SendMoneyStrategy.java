package org.poo.packagePOO.Transaction;

import org.poo.packagePOO.Bank.Account.BankAccount;
import org.poo.packagePOO.Bank.Account.TransactionsHistory.TransactionFactory;
import org.poo.packagePOO.CurrencyConverter;
import org.poo.packagePOO.GlobalManager;

public class SendMoneyStrategy implements TransactionStrategy {
    private final String sender;
    private final String receiver;
    private final double amount;
    private final String description;
    private final int timestamp;  // adăugăm timestamp
    private String error;

    public SendMoneyStrategy(String sender, double amount, String receiver, String description,
                             int timestamp) {
        this.sender = sender;
        this.amount = amount;
        this.receiver = receiver;
        this.description = description;
        this.timestamp = timestamp;  // salvăm timestamp-ul
    }

    @Override
    public boolean validate() {
        // Verificăm dacă conturile există
        BankAccount senderAccount = GlobalManager.getGlobal().getBank().getAccountIBAN(sender);
        BankAccount receiverAccount = GlobalManager.getGlobal().getBank().getAccountIBAN(receiver);

        if (senderAccount == null) {
            return false;
        }

        if (receiverAccount == null) {
            return false;
        }

        if (senderAccount.getBalance() < amount) {
            return false;
        }

        return true;
    }

    @Override
    public boolean process() {
        BankAccount senderAccount = GlobalManager.getGlobal().getBank().getAccountIBAN(sender);
        BankAccount receiverAccount = GlobalManager.getGlobal().getBank().getAccountIBAN(receiver);

        double amountToReceive = amount;
        if (!senderAccount.getCurrency().equals(receiverAccount.getCurrency())) {
            amountToReceive =
                    CurrencyConverter.getConverter().converteste(senderAccount.getCurrency(),
                            receiverAccount.getCurrency(), amount);
        }

        senderAccount.payAmount(amount);
        senderAccount.addTransactionHistory(TransactionFactory.createTransferTransaction(timestamp, description, sender, receiver, amount, senderAccount.getCurrency()));
        receiverAccount.addAmount(amountToReceive);
        return true;
    }

    @Override
    public String getError() {
        return error;
    }
}
