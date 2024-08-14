package com.europebet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class User {

    private final String userId;

    private double balance;

    private double slotBets;

    private double depositSum;

    private Scenario scenario;


    public User(String userId) {
        this.userId = userId;
        this.balance = 0;
        this.slotBets = 0;
        this.depositSum = 0;
        this.scenario = null;
    }

    public void deposit(double amount) {
        this.balance += amount;
        this.depositSum += amount;
    }

    public void bet(double amount, boolean shouldWin, Game game) {
        if (Game.SLOT.equals(game)) {
            this.slotBets += amount;
        }
        if (shouldWin) {
            this.balance += amount;
        } else {
            this.balance -= amount;
        }
    }

    public void winFirstPrize() {
        this.scenario.setPrize1Acquired(true);
        this.setDepositSum(0);
        this.setSlotBets(0);
        this.balance += this.scenario.getPrize1();
    }

    public void winSecondPrize() {
        this.scenario.setPrize2Acquired(true);
        this.setDepositSum(0);
        this.setSlotBets(0);
        this.balance += this.scenario.getPrize2();
    }

    public void winThirdPrize() {
        this.scenario.setPrize3Acquired(true);
        this.setDepositSum(0);
        this.setSlotBets(0);
        this.balance += this.scenario.getPrize3();
    }
}
