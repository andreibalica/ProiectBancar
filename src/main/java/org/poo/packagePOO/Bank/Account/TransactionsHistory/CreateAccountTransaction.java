package org.poo.packagePOO.Bank.Account.TransactionsHistory;

public class CreateAccountTransaction extends TransactionHistory {
    public CreateAccountTransaction(int timestamp, String description) {
        super(timestamp, description);
    }

    @Override
    public void accept(TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
