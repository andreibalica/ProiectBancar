package org.poo.packagePOO.Bank.Account.TransactionsHistory;

public class ErrorTransaction extends TransactionHistory {
    private final String errorMessage;

    public ErrorTransaction(int timestamp, String description) {
        super(timestamp, description);
        this.errorMessage = description;
    }

    @Override
    public void accept(TransactionVisitor visitor) {
        visitor.visit(this);
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}