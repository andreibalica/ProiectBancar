package org.poo.packagePOO.Bank.Account.TransactionsHistory;

public abstract class TransactionHistory implements Visitable {
    protected final int timestamp;
    protected final String description;

    public TransactionHistory(int timestamp, String description) {
        this.timestamp = timestamp;
        this.description = description;
    }

    public int getTimestamp() { return timestamp; }
    public String getDescription() { return description; }

    @Override
    public abstract void accept(TransactionVisitor visitor);
}
