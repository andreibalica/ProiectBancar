package org.poo.packagePOO.Bank.Account.TransactionsHistory;

public interface Visitable {
    void accept(TransactionVisitor visitor);
}
