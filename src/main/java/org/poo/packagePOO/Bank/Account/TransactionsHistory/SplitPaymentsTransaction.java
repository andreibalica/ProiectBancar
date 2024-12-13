package org.poo.packagePOO.Bank.Account.TransactionsHistory;

import java.util.ArrayList;

public class SplitPaymentsTransaction extends TransactionHistory {
    private final double totalAmount;
    private final double splitAmount;
    private final String currency;
    private final ArrayList<String> involvedAccounts;

    public SplitPaymentsTransaction(int timestamp, double totalAmount, double splitAmount,
                                   String currency, ArrayList<String> accounts) {
        super(timestamp, String.format("Split payment of %.2f %s", totalAmount, currency));
        this.totalAmount = totalAmount;
        this.splitAmount = splitAmount;
        this.currency = currency;
        this.involvedAccounts = new ArrayList<>(accounts);
    }

    public double getSplitAmount() { return splitAmount; }
    public String getCurrency() { return currency; }
    public ArrayList<String> getInvolvedAccounts() { return involvedAccounts; }

    @Override
    public void accept(TransactionVisitor visitor) {
        visitor.visit(this);
    }
}