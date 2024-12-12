package org.poo.packagePOO.Command;

import org.poo.packagePOO.Bank.Account.BankAccount;
import org.poo.packagePOO.Bank.Account.TransactionsHistory.TransactionFactory;
import org.poo.packagePOO.Bank.Bank;
import org.poo.packagePOO.Bank.Card;
import org.poo.packagePOO.GlobalManager;
import org.poo.utils.Utils;

public class CreateOneTimeCard implements Command {
    private final String email;
    private final String IBAN;
    private final int timestamp;

    public CreateOneTimeCard(String email, String IBAN, int timestamp) {
        this.email = email;
        this.IBAN = IBAN;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Bank bank = GlobalManager.getGlobal().getBank();
        BankAccount account = bank.getAccountIBAN(IBAN);

        if (account != null) {
            String cardNumber = Utils.generateCardNumber();
            Card card = new Card(email, cardNumber, IBAN, timestamp);
            card.setUse(0);
            account.addTransactionHistory(TransactionFactory.createCardTransaction(timestamp,
                    cardNumber, email, IBAN, true));
            account.addCard(card);
        }
    }
}
