package org.poo.packagePOO.Command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.packagePOO.Bank.Account.BankAccount;
import org.poo.packagePOO.Bank.Account.TransactionsHistory.TransactionHistory;
import org.poo.packagePOO.Bank.Account.TransactionsHistory.TransactionPrinter;
import org.poo.packagePOO.Bank.Account.TransactionsHistory.TransactionVisitor;
import org.poo.packagePOO.GlobalManager;

public class Report implements Command{
    private final int startTimestamp;
    private final int endTimestamp;
    private final String IBAN;
    private final int timestamp;

    public Report(int startTimestamp, int endTimestamp, String IBAN, int timestamp){
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.IBAN = IBAN;
        this.timestamp = timestamp;
    }

    @Override
    public void execute(){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode commandNode = mapper.createObjectNode();
        ObjectNode outputNode = mapper.createObjectNode();
        ArrayNode transactionsArray = mapper.createArrayNode();
        BankAccount account = GlobalManager.getGlobal().getBank().getAccountIBAN(IBAN);

        if(account != null) {
            for (TransactionHistory transaction : account.getTransactionHistory()) {
                if (transaction.getTimestamp() >= startTimestamp && transaction.getTimestamp() <= endTimestamp) {
                    TransactionVisitor visitor = new TransactionPrinter(transactionsArray);
                    transaction.accept(visitor);
                }
            }

            commandNode.put("command", "report");
            outputNode.put("IBAN", IBAN);
            outputNode.put("balance", account.getBalance());
            outputNode.put("currency", account.getCurrency());
            outputNode.set("transactions", transactionsArray);
            commandNode.set("output", outputNode);;
            commandNode.put("timestamp", timestamp);

            GlobalManager.getGlobal().getOutput().add(commandNode);
        }
    }
}
