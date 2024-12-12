package org.poo.packagePOO;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.packagePOO.Bank.Bank;
import org.poo.utils.Utils;

public class GlobalManager {
    private static final GlobalManager global = new GlobalManager();
    private Bank bank;
    private ArrayNode output;

    private GlobalManager() {
        this.bank = new Bank();
    }

    public static GlobalManager getGlobal() {
        return global;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    public void setOutput(ArrayNode output) {
        this.output = output;
    }

    public ArrayNode getOutput() {
        return output;
    }

    public void newBank() {
        Utils.resetRandom();
        this.bank = new Bank();
    }
}
