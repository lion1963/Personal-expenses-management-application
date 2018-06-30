package com.sviatoslav.app.services;

import com.sviatoslav.app.entities.Expenses;

import java.util.List;

public interface CurrencyRateService {

    boolean containsKey(String currency);
    void getCurrency();
    double convertCurrency(List<Expenses> expenses, String currency);
}
