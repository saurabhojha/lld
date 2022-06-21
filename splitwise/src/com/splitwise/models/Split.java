package com.splitwise.models;

import lombok.Data;

@Data
public class Split {
    private String userId;
    private double amount;
    private double percentage;
}
