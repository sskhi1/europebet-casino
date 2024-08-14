package com.europebet.utils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String DATA_DIRECTORY = "/data/";
    private static final String TRANSACTIONS_FILE = "transactions.txt";
    private static final String RESULTS_FILE = "result.txt";

    public List<String> readTransactions() {
        List<String> lines = new ArrayList<>();
        Path filePath = Paths.get(DATA_DIRECTORY + TRANSACTIONS_FILE);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public void writeResults(List<String> lines) {
        Path filePath = Paths.get(DATA_DIRECTORY + RESULTS_FILE);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
