package org.poo.packagePOO.Command;

import org.poo.packagePOO.Bank.Account.BankAccount;
import org.poo.packagePOO.GlobalManager;

public class AddFunds implements Command {
    private final String IBAN;
    private final double amount;
    private final int timestamp;

    public AddFunds(String IBAN, double amount, int timestamp) {
        this.IBAN = IBAN;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        BankAccount bankAccount = GlobalManager.getGlobal().getBank().getAccountIBAN(IBAN);
        if (bankAccount != null) {
            bankAccount.addAmount(amount);
        }
    }
}
