package org.poo.packagePOO.Bank;

import org.poo.fileio.CommandInput;
import org.poo.fileio.ExchangeInput;
import org.poo.fileio.ObjectInput;
import org.poo.fileio.UserInput;
import org.poo.packagePOO.Bank.Account.BankAccount;
import org.poo.packagePOO.Command.Command;
import org.poo.packagePOO.Command.CommandFactory;
import org.poo.packagePOO.GlobalManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Bank {
    private final CommandFactory commandFactory = new CommandFactory();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<ExchangeRate> exchangeRates = new ArrayList<>();
    private ArrayList<BankAccount> accounts = new ArrayList<>();
    private Map<String, Map<String, String>> aliases = new HashMap<>();

    /**
     *
     * @return
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     *
     * @return
     */
    public ArrayList<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    /**
     *
     * @return
     */
    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }

    /**
     *
     * @param inputs
     */
    public void addUsersFromInput(final UserInput[] inputs) {
        for (UserInput input : inputs) {
            users.add(new User(input.getFirstName(),
                    input.getLastName(), input.getEmail()));
        }
    }

    /**
     *
     * @param rates
     */
    public void addExchangeRatesFromInput(final ExchangeInput[] rates) {
        for (ExchangeInput rate : rates) {
            exchangeRates.add(new ExchangeRate(rate.getFrom(),
                    rate.getTo(), rate.getRate()));
        }
    }

    /**
     *
     * @param inputData
     */
    public void initializeBank(final ObjectInput inputData) {
        GlobalManager.getGlobal().getBank().addUsersFromInput(inputData.getUsers());
        GlobalManager.getGlobal().getBank()
                .addExchangeRatesFromInput(inputData.getExchangeRates());
    }

    /**
     *
     * @param account
     */
    public void addAccount(final BankAccount account) {
        accounts.add(account);
    }

    /**
     *
     * @param account
     */
    public void removeAccount(final BankAccount account) {
        accounts.remove(account);
    }

    /**
     *
     * @param iban
     * @return
     */
    public BankAccount getAccountIBAN(final String iban) {
        for (BankAccount account : accounts) {
            if (iban.equals(account.getIBAN())) {
                return account;
            }
        }
        return null;
    }

    /**
     *
     * @param cardNumber
     * @return
     */
    public BankAccount getAccountCard(final String cardNumber) {
        for (BankAccount account : accounts) {
            if (account.searchCard(cardNumber) != null) {
                return account;
            }
        }
        return null;
    }

    /**
     *
     * @param email
     * @param alias
     * @param iban
     */
    public void addAlias(final String email,
                         final String alias,
                         final String iban) {
        aliases.computeIfAbsent(email, k -> new HashMap<>()).put(alias, iban);
    }

    /**
     *
     * @param commandInput
     */
    public void executeCommand(final CommandInput commandInput) {
        Command command = commandFactory.createCommand(commandInput);
        if (command != null) {
            command.execute();
        }
    }
}
