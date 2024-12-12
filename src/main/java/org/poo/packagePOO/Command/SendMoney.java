package org.poo.packagePOO.Command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.packagePOO.GlobalManager;
import org.poo.packagePOO.Transaction.SendMoneyStrategy;

public class SendMoney implements Command {
    private final SendMoneyStrategy strategy;
    private final String account;
    private final double amount;
    private final String receiver;
    private final String description;
    private final int timestamp;

    public SendMoney(String account, double amount, String receiver, String description,
                     int timestamp) {
        this.account = account;
        this.amount = amount;
        this.receiver = receiver;
        this.description = description;
        this.timestamp = timestamp;
        this.strategy = new SendMoneyStrategy(account, amount, receiver, description, timestamp);
    }

    @Override
    public void execute() {
        if ((!strategy.validate() || !strategy.process()) && strategy.getError() != null) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode response = mapper.createObjectNode();
            response.put("command", "sendMoney");
            ObjectNode output = mapper.createObjectNode();
            output.put("timestamp", timestamp);
            output.put("description", strategy.getError());
            response.set("output", output);
            response.put("timestamp", timestamp);
            GlobalManager.getGlobal().getOutput().add(response);
        }
    }
}
