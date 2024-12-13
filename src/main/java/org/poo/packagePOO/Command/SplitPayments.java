package org.poo.packagePOO.Command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.packagePOO.GlobalManager;
import org.poo.packagePOO.Transaction.SplitPaymentsStrategy;

import java.util.ArrayList;

public class SplitPayments implements Command {
    private final SplitPaymentsStrategy strategy;
    private final ArrayList<String> accounts;
    private final int timestamp;
    private final String currency;
    private final double amount;

    public SplitPayments(ArrayList<String> accounts, int timestamp, String currency, double amount) {
        this.accounts = accounts;
        this.timestamp = timestamp;
        this.currency = currency;
        this.amount = amount;
        strategy = new SplitPaymentsStrategy(accounts, amount, currency, timestamp);
    }

    @Override
    public void execute() {
        if ((!strategy.validate() || !strategy.process()) && strategy.getError() != null) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode response = mapper.createObjectNode();
            response.put("command", "splitPayments");
            ObjectNode output = mapper.createObjectNode();
            output.put("timestamp", timestamp);
            output.put("description", strategy.getError());
            response.set("output", output);
            response.put("timestamp", timestamp);
            GlobalManager.getGlobal().getOutput().add(response);
        }
    }
}
