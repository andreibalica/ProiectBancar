package org.poo.packagePOO.Command;

import org.poo.packagePOO.Bank.Account.BankAccount;
import org.poo.packagePOO.Bank.Account.TransactionsHistory.TransactionFactory;
import org.poo.packagePOO.Bank.Bank;
import org.poo.packagePOO.GlobalManager;

public class DeleteCard implements Command {
    private final String cardNumber;
    private final int timestamp;
    private final String email;

    public DeleteCard(String cardNumber, String email, int timestamp) {
        this.cardNumber = cardNumber;
        this.email = email;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Bank bank = GlobalManager.getGlobal().getBank();
        BankAccount account = bank.getAccountEmail(email);
        if (account.searchCard(cardNumber) != null) {
            account.deleteCard(cardNumber);
            String IBAN = account.getIBAN();
            account.addTransactionHistory(TransactionFactory.createCardTransaction(timestamp,
                    cardNumber, email, IBAN, false));
        }
    }
}
