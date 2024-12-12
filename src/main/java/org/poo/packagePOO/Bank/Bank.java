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

public class Bank {
    private final CommandFactory commandFactory = new CommandFactory();

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<ExchangeRate> exchangeRates = new ArrayList<>();
    private ArrayList<BankAccount> accounts = new ArrayList<>();
    private Map<String, Map<String, String>> aliases = new HashMap<>();

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }

    public void addUsersFromInput(UserInput[] inputs) {
        for (UserInput input : inputs) {
            users.add(new User(input.getFirstName(), input.getLastName(), input.getEmail()));
        }
    }

    public void addExchangeRatesFromInput(ExchangeInput[] rates) {
        for (ExchangeInput rate : rates) {
            exchangeRates.add(new ExchangeRate(rate.getFrom(), rate.getTo(), rate.getRate()));
        }
    }

    public void initializeBank(ObjectInput inputData) {
        GlobalManager.getGlobal().getBank().addUsersFromInput(inputData.getUsers());
        GlobalManager.getGlobal().getBank().addExchangeRatesFromInput(inputData.getExchangeRates());
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public void removeAccount(BankAccount account) {
        accounts.remove(account);
    }

    public BankAccount getAccountIBAN(String IBAN) {
        for (BankAccount account : accounts) {
            if (IBAN.equals(account.getIBAN())) {
                return account;
            }
        }
        return null;
    }

    public BankAccount getAccountEmail(String email) {
        for (BankAccount account : accounts) {
            if (email.equals(account.getEmail())) {
                return account;
            }
        }
        return null;
    }

    public BankAccount getAccountCard(String cardNumber) {
        for (BankAccount account : accounts) {
            if (account.searchCard(cardNumber) != null) {
                return account;
            }
        }
        return null;
    }

    public void addAlias(String email, String alias, String IBAN) {
        aliases.computeIfAbsent(email, k -> new HashMap<>()).put(alias, IBAN);
    }

    public void executeCommand(CommandInput commandInput) {
        Command command = commandFactory.createCommand(commandInput);
        if (command != null) {
            command.execute();
        }
    }
}
