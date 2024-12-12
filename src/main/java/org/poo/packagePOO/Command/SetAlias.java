package org.poo.packagePOO.Command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.packagePOO.Bank.Account.BankAccount;
import org.poo.packagePOO.Bank.Bank;
import org.poo.packagePOO.GlobalManager;

public class SetAlias implements Command {
    private final String email;
    private final String alias;
    private final String IBAN;
    private final int timestamp;

    public SetAlias(String email, String alias, String IBAN, int timestamp) {
        this.email = email;
        this.alias = alias;
        this.IBAN = IBAN;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode commandNode = mapper.createObjectNode();
        ObjectNode outputNode = mapper.createObjectNode();

        Bank bank = GlobalManager.getGlobal().getBank();
        BankAccount account = bank.getAccountIBAN(IBAN);

        if (account == null) {
            outputNode.put("error", "Account not found");
            outputNode.put("timestamp", timestamp);
            commandNode.put("command", "setAlias");
            commandNode.set("output", outputNode);
            GlobalManager.getGlobal().getOutput().add(commandNode);
            return;
        }

        bank.addAlias(email, alias, IBAN);
    }
}
