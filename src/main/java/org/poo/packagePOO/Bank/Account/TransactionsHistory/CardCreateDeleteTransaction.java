package org.poo.packagePOO.Bank.Account.TransactionsHistory;

public class CardCreateDeleteTransaction extends TransactionHistory {
    private final String card;
    private final String cardHolder;
    private final String account;

    public CardCreateDeleteTransaction(int timestamp, String description,
                                       String card, String cardHolder, String account) {
        super(timestamp, description);
        this.card = card;
        this.cardHolder = cardHolder;
        this.account = account;
    }

    public String getCard() { return card; }
    public String getCardHolder() { return cardHolder; }
    public String getAccount() { return account; }

    @Override
    public void accept(TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
