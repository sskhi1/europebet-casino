package com.europebet.casino;

import com.europebet.model.Game;
import com.europebet.model.Scenario;
import com.europebet.model.User;
import com.europebet.utils.Util;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CasinoSimulator implements Casino {

    private final Map<String, User> users = new HashMap<>();
    private final PrizeManager prizeManager = new PrizeManager(new ArrayList<>());
    private boolean shouldWin = true;

    private static final Logger logger = Logger.getLogger(CasinoSimulator.class.getName());

    public List<String> processTransaction(@NotNull String line) {
        List<String> transactionResults = new ArrayList<>();
        String[] parts = line.split(" ");
        if (parts.length <= 1) {
            logger.info("invalid operation");
            return transactionResults;
        }

        String command = parts[0];
        switch (command) {
            case "register" -> {
                registerUser(parts);
            }
            case "addscenario" -> {
                if (parts.length != 4) {
                    logger.info("invalid addscenario operation");
                    break;
                }
                addScenario(parts);
            }
            case "deposit" -> {
                deposit(parts);
            }
            case "bet" -> {
                processBet(parts);
            }
            case "balance" -> {
                if (parts.length != 2 || !userExists(parts[1])) {
                    logger.warning("invalid balance operation");
                    break;
                }
                transactionResults.add(getBalance(parts));
            }
            default -> logger.info("invalid operation");
        }
        return transactionResults;
    }

    private boolean userExists(String userId) {
        return users.get(userId) != null;
    }

    private void registerUser(String[] parts) {
        if (parts.length != 2) {
            logger.warning("invalid register operation");
            return;
        }
        String userId = parts[1];

        registerUser(userId);
    }

    private void addScenario(String[] parts) {
        if (parts.length != 4 || !Util.isDouble(parts[1]) || !Util.isDouble(parts[2]) || !Util.isDouble(parts[3])) {
            logger.warning("invalid addscenario operation");
            return;
        }
        double prize1 = Double.parseDouble(parts[1]);
        double prize2 = Double.parseDouble(parts[2]);
        double prize3 = Double.parseDouble(parts[3]);

        addScenario(prize1, prize2, prize3);
    }

    private void deposit(String[] parts) {
        if (parts.length != 3 || !Util.isDouble(parts[2])) {
            logger.warning("invalid deposit operation");
            return;
        }
        String userId = parts[1];
        double amount = Double.parseDouble(parts[2]);

        deposit(userId, amount);
    }

    private void processBet(String[] parts) {
        if (parts.length != 4 || !Util.isDouble(parts[3])) {
            logger.warning("invalid bet operation");
            return;
        }
        String userId = parts[1];
        Game game;
        try {
            game = Game.valueOf(parts[2].toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.warning("Invalid game type: " + parts[2]);
            return;
        }
        double amount = Double.parseDouble(parts[3]);

        processBet(userId, game, amount);
    }

    private String getBalance(String[] parts) {
        String userId = parts[1];

        return getBalance(userId);
    }

    @Override
    public void registerUser(String userId) {
        users.putIfAbsent(userId, new User(userId));
        logger.info("User registered: " + userId);
    }

    @Override
    public void addScenario(double prize1, double prize2, double prize3) {
        Scenario scenario = Scenario.builder()
                .prize1(prize1)
                .prize2(prize2)
                .prize3(prize3)
                .isUsed(false)
                .build();
        prizeManager.addScenario(scenario);
    }

    @Override
    public void deposit(String userId, double amount) {
        User user = users.get(userId);
        if (user != null) {
            user.deposit(amount);
            logger.info("Deposited " + amount + " to user " + userId);
            logger.info("User " + userId + "'s balance is now " + user.getBalance());
        } else {
            logger.warning("User not found: " + userId);
        }
    }

    @Override
    public void processBet(String userId, Game game, double amount) {
        User user = users.get(userId);
        if (user != null) {
            if (user.getBalance() >= amount) {
                user.bet(amount, shouldWin, game);
                logger.info("User " + userId + " " + (shouldWin ? "won" : "lost") + " " + amount + " on " + game);
                prizeManager.checkAndAwardPrizes(user);
                shouldWin = !shouldWin;
            } else {
                logger.warning("Insufficient balance for user " + userId);
            }
        } else {
            logger.warning("User not found: " + userId);
        }
    }

    @Override
    public String getBalance(String userId) {
        User user = users.get(userId);
        double balance = user.getBalance();

        DecimalFormat wholeNumberFormat = new DecimalFormat("0");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        if (balance == (int) balance) {
            return wholeNumberFormat.format(balance);
        } else {
            return decimalFormat.format(balance);
        }
    }
}
