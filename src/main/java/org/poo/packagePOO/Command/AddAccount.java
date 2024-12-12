package org.poo.packagePOO.Command;

import org.poo.packagePOO.Bank.Account.BankAccount;
import org.poo.packagePOO.Bank.Account.TransactionsHistory.TransactionFactory;
import org.poo.packagePOO.GlobalManager;
import org.poo.utils.Utils;

public class AddAccount implements Command {
    private final String email;
    private final String currency;
    private final String accountType;
    private final int timestamp;
    private final double interestRate;

    public AddAccount(String email, String currency, String accountType, int timestamp,
                      double interestRate) {
        this.email = email;
        this.currency = currency;
        this.accountType = accountType;
        this.timestamp = timestamp;
        this.interestRate = interestRate;
    }

    @Override
    public void execute() {
        String IBAN = Utils.generateIBAN();
        BankAccount.BankAccountBuilder builder = new BankAccount.BankAccountBuilder(email, IBAN,
                currency, timestamp);
        builder.setAccountType(accountType);

        if ("savings".equals(accountType)) {
            builder.setInterestRate(interestRate);
        }


        BankAccount account = builder.build();
        account.addTransactionHistory(TransactionFactory.createAccountCreationTransaction(timestamp));
        GlobalManager.getGlobal().getBank().addAccount(account);
    }
}
