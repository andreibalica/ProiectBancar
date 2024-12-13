package org.poo.packagePOO;

import org.poo.packagePOO.Bank.Bank;
import org.poo.packagePOO.Bank.ExchangeRate;

import java.util.*;

public class CurrencyConverter {
    private static final CurrencyConverter converter = new CurrencyConverter();
    private Map<String, Map<String, Double>> exchangeRates;

    private CurrencyConverter() {
        this.exchangeRates = new HashMap<>();
    }

    public static CurrencyConverter getConverter() {
        return converter;
    }

    public void newConverter() {
        this.exchangeRates = new HashMap<>();
    }

    public void initializeaza() {
        exchangeRates.clear();
        Bank bank = GlobalManager.getGlobal().getBank();

        for (ExchangeRate rate : bank.getExchangeRates()) {
            addRate(rate.getFrom(), rate.getTo(), rate.getRate());
            addRate(rate.getTo(), rate.getFrom(), 1.0 / rate.getRate());
        }
    }

    private void addRate(String from, String to, double rate) {
        exchangeRates.computeIfAbsent(from, k -> new HashMap<>()).put(to, rate);
    }

    public double convert(String from, String to, double amount) {
        if (from.equals(to)) {
            return amount;
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, Double> rates = new HashMap<>();
        Map<String, String> path = new HashMap<>();

        queue.add(from);
        rates.put(from, 1.0);
        visited.add(from);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (!exchangeRates.containsKey(current)) {
                continue;
            }

            for (Map.Entry<String, Double> neighbor : exchangeRates.get(current).entrySet()) {
                String nextCurrency = neighbor.getKey();
                if (!visited.contains(nextCurrency)) {
                    visited.add(nextCurrency);
                    queue.add(nextCurrency);
                    rates.put(nextCurrency, rates.get(current) * neighbor.getValue());
                    path.put(nextCurrency, current);

                    if (nextCurrency.equals(to)) {
                        return amount * rates.get(to);
                    }
                }
            }
        }

        throw new IllegalArgumentException(
                String.format("No conversion path found from %s to %s", from, to)
        );
    }
}
