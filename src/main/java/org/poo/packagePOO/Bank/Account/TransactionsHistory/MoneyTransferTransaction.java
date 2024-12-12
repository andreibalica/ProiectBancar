package org.poo.packagePOO.Bank.Account.TransactionsHistory;

public class MoneyTransferTransaction extends TransactionHistory {
    private final String senderIBAN;
    private final String receiverIBAN;
    private final String amount;
    private final String transferType;

    public MoneyTransferTransaction(int timestamp, String description, String senderIBAN,
                                    String receiverIBAN, double amount, String currency) {
        super(timestamp, description);
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount + " " + currency;
        this.transferType = "sent";
    }

    public String getSenderIBAN() { return senderIBAN; }
    public String getReceiverIBAN() { return receiverIBAN; }
    public String getAmount() { return amount; }
    public String getTransferType() { return transferType; }

    @Override
    public void accept(TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
