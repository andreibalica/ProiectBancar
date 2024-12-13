package org.poo.packagePOO.Bank.Account.TransactionsHistory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TransactionPrinter implements TransactionVisitor {
    private final ArrayNode output;

    public TransactionPrinter(ArrayNode output) {
        this.output = output;
    }

    @Override
    public void visit(CreateAccountTransaction transaction) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("timestamp", transaction.getTimestamp());
        node.put("description", transaction.getDescription());
        output.add(node);
    }

    @Override
    public void visit(CardCreateDeleteTransaction transaction) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("timestamp", transaction.getTimestamp());
        node.put("description", transaction.getDescription());
        node.put("card", transaction.getCard());
        node.put("cardHolder", transaction.getCardHolder());
        node.put("account", transaction.getAccount());
        output.add(node);
    }

    @Override
    public void visit(MoneyTransferTransaction transaction) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("timestamp", transaction.getTimestamp());
        node.put("description", transaction.getDescription());
        node.put("senderIBAN", transaction.getSenderIBAN());
        node.put("receiverIBAN", transaction.getReceiverIBAN());
        node.put("amount", transaction.getAmount());
        node.put("transferType", transaction.getTransferType());
        output.add(node);
    }

    @Override
    public void visit(CardPaymentTransaction transaction) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("timestamp", transaction.getTimestamp());
        node.put("description", transaction.getDescription());
        node.put("amount", transaction.getAmount());
        node.put("commerciant", transaction.getCommerciant());
        output.add(node);
    }

    @Override
    public void visit(ErrorTransaction transaction) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("timestamp", transaction.getTimestamp());
        node.put("description", transaction.getDescription());
        output.add(node);
    }

    @Override
    public void visit(SplitPaymentsTransaction transaction) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("timestamp", transaction.getTimestamp());
        node.put("description", transaction.getDescription());
        node.put("currency", transaction.getCurrency());
        node.put("amount", transaction.getSplitAmount());

        ArrayNode accountsArray = mapper.createArrayNode();
        for (String account : transaction.getInvolvedAccounts()) {
            accountsArray.add(account);
        }
        node.set("involvedAccounts", accountsArray);

        output.add(node);
    }
}