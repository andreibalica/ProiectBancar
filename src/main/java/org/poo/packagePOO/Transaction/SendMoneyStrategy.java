package org.poo.packagePOO.Transaction;

import org.poo.packagePOO.Bank.Account.BankAccount;
import org.poo.packagePOO.Bank.Account.TransactionsHistory.TransactionFactory;
import org.poo.packagePOO.CurrencyConverter;
import org.poo.packagePOO.GlobalManager;

public final class SendMoneyStrategy implements TransactionStrategy {
    private final String sender;
    private final String receiver;
    private final double amount;
    private final String description;
    private final int timestamp;
    private String error;
    private double amountToReceive;

    /**
     *
     * @param sender
     * @param amount
     * @param receiver
     * @param description
     * @param timestamp
     */
    public SendMoneyStrategy(final String sender,
                             final double amount,
                             final String receiver,
                             final String description,
                             final int timestamp) {
        this.sender = sender;
        this.amount = amount;
        this.receiver = receiver;
        this.description = description;
        this.timestamp = timestamp;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean validate() {
        BankAccount senderAccount = GlobalManager.getGlobal()
                .getBank().getAccountIBAN(sender);
        BankAccount receiverAccount = GlobalManager.getGlobal()
                .getBank().getAccountIBAN(receiver);

        if (senderAccount == null) {
            return false;
        }

        if (receiverAccount == null) {
            return false;
        }

        if (senderAccount.getBalance() < amount) {
            senderAccount.addTransactionHistory(
                    TransactionFactory.createErrorTransaction(
                            timestamp,
                            "Insufficient funds"
                    )
            );
            return false;
        }

        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean process() {
        BankAccount senderAccount = GlobalManager.getGlobal()
                .getBank().getAccountIBAN(sender);
        BankAccount receiverAccount = GlobalManager.getGlobal()
                .getBank().getAccountIBAN(receiver);

        amountToReceive = amount;
        try {
            amountToReceive = CurrencyConverter.getConverter()
                    .convert(senderAccount.getCurrency(),
                            receiverAccount.getCurrency(),
                            amount);
        } catch (IllegalArgumentException e) {
            return false;
        }

        senderAccount.payAmount(amount);
        senderAccount.addTransactionHistory(
                TransactionFactory.createTransferTransaction(
                        timestamp,
                        description,
                        sender,
                        receiver,
                        amount,
                        senderAccount.getCurrency(),
                        true
                )
        );
        receiverAccount.addAmount(amountToReceive);
        receiverAccount.addTransactionHistory(
                TransactionFactory.createTransferTransaction(
                        timestamp,
                        description,
                        sender,
                        receiver,
                        amountToReceive,
                        receiverAccount.getCurrency(),
                        false
                )
        );
        return true;
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
