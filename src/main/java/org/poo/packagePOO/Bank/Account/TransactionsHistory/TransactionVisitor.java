package org.poo.packagePOO.Bank.Account.TransactionsHistory;

public interface TransactionVisitor {
    void visit(CreateAccountTransaction transaction);
    void visit(CardCreateDeleteTransaction transaction);
    void visit(MoneyTransferTransaction transaction);
    void visit(CardPaymentTransaction cardPaymentTransaction);
    void visit(ErrorTransaction transaction);
}
