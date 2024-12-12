package org.poo.packagePOO.Command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.packagePOO.Bank.Account.BankAccount;
import org.poo.packagePOO.Bank.Account.TransactionsHistory.TransactionHistory;
import org.poo.packagePOO.Bank.Account.TransactionsHistory.TransactionPrinter;
import org.poo.packagePOO.Bank.Account.TransactionsHistory.TransactionVisitor;
import org.poo.packagePOO.GlobalManager;

public class PrintTransactions implements Command {
    private final String email;
    private final int timestamp;

    public PrintTransactions(String email, int timestamp) {
        this.email = email;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode commandNode = mapper.createObjectNode();
        ArrayNode transactionsArray = mapper.createArrayNode();

        for (BankAccount account : GlobalManager.getGlobal().getBank().getAccounts()) {
            if (account.getEmail().equals(email)) {
                for (TransactionHistory transaction : account.getTransactionHistory()) {
                    TransactionVisitor visitor = new TransactionPrinter(transactionsArray);
                    transaction.accept(visitor);
                }
            }
        }

        commandNode.put("command", "printTransactions");
        commandNode.set("output", transactionsArray);
        commandNode.put("timestamp", timestamp);

        GlobalManager.getGlobal().getOutput().add(commandNode);
    }
}