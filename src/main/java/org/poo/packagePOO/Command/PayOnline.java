package org.poo.packagePOO.Command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.packagePOO.GlobalManager;
import org.poo.packagePOO.Transaction.PayOnlineStrategy;
import org.poo.packagePOO.Transaction.TransactionStrategy;

public class PayOnline implements Command {
    private final TransactionStrategy strategy;
    private final String cardNumber;
    private final double amount;
    private final String currency;
    private final String description;
    private final String commerciant;
    private final String email;
    private final int timestamp;

    public PayOnline(String CardNumber, double Amount, String Currency, String Description,
                     String Commerciant, String Email, int Timestamp) {
        this.cardNumber = CardNumber;
        this.amount = Amount;
        this.currency = Currency;
        this.description = Description;
        this.commerciant = Commerciant;
        this.email = Email;
        this.timestamp = Timestamp;
        this.strategy = new PayOnlineStrategy(cardNumber, amount, currency, description,
                commerciant, email, timestamp);
    }

    @Override
    public void execute() {

        if ((!strategy.validate() || !strategy.process()) && strategy.getError() != null) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode response = mapper.createObjectNode();
            response.put("command", "payOnline");
            ObjectNode output = mapper.createObjectNode();
            output.put("timestamp", timestamp);
            output.put("description", strategy.getError());
            response.set("output", output);
            response.put("timestamp", timestamp);
            GlobalManager.getGlobal().getOutput().add(response);
        }
    }

}