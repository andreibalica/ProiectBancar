package org.poo.packagePOO.Command;

import org.poo.packagePOO.Bank.Account.BankAccount;
import org.poo.packagePOO.Bank.Bank;
import org.poo.packagePOO.GlobalManager;

public class SetMinimumBalance implements Command {
    private final String IBAN;
    private final double minBalance;
    private final int timestamp;

    public SetMinimumBalance(String IBAN, double minBalance, int timestamp) {
        this.IBAN = IBAN;
        this.minBalance = minBalance;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Bank bank = GlobalManager.getGlobal().getBank();
        BankAccount account = bank.getAccountIBAN(IBAN);
        account.setMinBalance(minBalance);
    }
}
