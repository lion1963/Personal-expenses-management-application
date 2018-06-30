package com.sviatoslav.app.model;

import lombok.Data;

import java.util.Map;

@Data
public class Rates {
    private Map<String, Double> rates;
}
