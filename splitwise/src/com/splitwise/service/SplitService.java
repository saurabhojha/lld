package com.splitwise.service;

import com.splitwise.models.Split;

public class SplitService {

    public Split createSplit(String owedByUserId,double amount,double percentage) {
        Split split = new Split();
        split.setAmount(amount);
        split.setPercentage(percentage);
        split.setUserId(owedByUserId);
        return split;
    }
}
