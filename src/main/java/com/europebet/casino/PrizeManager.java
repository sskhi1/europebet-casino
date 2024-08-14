package com.europebet.casino;

import com.europebet.model.Scenario;
import com.europebet.model.User;

import java.util.List;
import java.util.logging.Logger;

public class PrizeManager {

    private static final Logger logger = Logger.getLogger(PrizeManager.class.getName());
    private final List<Scenario> scenarios;

    public PrizeManager(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }

    public void addScenario(Scenario scenario) {
        this.scenarios.add(scenario);
    }

    public boolean canAcquireFirstPrize(User user) {
        return user.getDepositSum() >= 100 && user.getSlotBets() >= 50;
    }

    public void checkAndAwardPrizes(User user) {
        if (user.getScenario() == null && canAcquireFirstPrize(user)) {
            for (Scenario scenario : scenarios) {
                if (!scenario.isUsed()) {
                    scenario.setUsed(true);
                    user.setScenario(scenario);
                    break;
                }
            }
        }
        if (user.getScenario() != null) {
            checkAndAcquirePrizeForScenario(user, user.getScenario());
        }
    }

    private void checkAndAcquirePrizeForScenario(User user, Scenario scenario) {
        if (!scenario.isPrize1Acquired() && user.getDepositSum() >= 100 && user.getSlotBets() >= 50) {
            user.winFirstPrize();
            logger.info("User " + user.getUserId() + " has acquired the first prize.");
        }

        if (!scenario.isPrize2Acquired() && user.getDepositSum() >= 500 && user.getSlotBets() >= 250) {
            user.winSecondPrize();
            logger.info("User " + user.getUserId() + " has acquired the second prize.");
        }

        if (!scenario.isPrize3Acquired() && user.getDepositSum() >= 1000 && user.getSlotBets() >= 500) {
            user.winThirdPrize();
            logger.info("User " + user.getUserId() + " has acquired the third prize.");
        }
    }
}
