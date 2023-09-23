package com.challenge.domain;

import lombok.Data;

@Data
public class RateLimit {

    private int value;
    private String unit;
    private static final String SEPARATOR = "_";
    public RateLimit(String rate) {
        this.value = Integer.parseInt(rate.split(SEPARATOR)[0]);
        this.unit = rate.split(SEPARATOR)[1];
    }
}
