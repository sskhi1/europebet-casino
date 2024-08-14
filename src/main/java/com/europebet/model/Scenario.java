package com.europebet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Scenario {

    private double prize1;

    private double prize2;

    private double prize3;

    private boolean prize1Acquired;

    private boolean prize2Acquired;

    private boolean prize3Acquired;

    private boolean isUsed;
}
