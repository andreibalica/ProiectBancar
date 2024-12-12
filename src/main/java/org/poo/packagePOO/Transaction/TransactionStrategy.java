package org.poo.packagePOO.Transaction;

public interface TransactionStrategy {
    boolean validate();

    boolean process();

    String getError();
}