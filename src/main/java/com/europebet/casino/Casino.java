package com.europebet.casino;

import com.europebet.model.Game;

public interface Casino {

    void registerUser(String userId);

    void addScenario(double prize1, double prize2, double prize3);

    void deposit(String userId, double amount);

    void processBet(String userId, Game game, double amount);

    String getBalance(String userId);
}
