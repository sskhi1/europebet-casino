package com.europebet;

import com.europebet.casino.CasinoSimulator;
import com.europebet.utils.FileManager;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        FileManager fileManager = new FileManager();

        List<String> transactions = fileManager.readTransactions();
        List<String> result = simulateTransactions(transactions);
        fileManager.writeResults(result);
    }

    private static List<String> simulateTransactions(List<String> transactions) {
        CasinoSimulator casinoSimulator = new CasinoSimulator();
        List<String> result = new ArrayList<>();
        for (String transaction : transactions) {
            result.addAll(casinoSimulator.processTransaction(transaction));
        }
        return result;
    }
}