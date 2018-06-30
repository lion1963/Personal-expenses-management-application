package com.sviatoslav.app.services;

import com.sviatoslav.app.entities.Expenses;
import com.sviatoslav.app.services.impl.CurrencyRateServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyRateServiceTest {

    private CurrencyRateServiceImpl currencyRateServiceImpl = new CurrencyRateServiceImpl();

    @Test
    public void convertCurrency(){
        Map<String, Double> map = new HashMap<>();
        map.put("EUR", 1.0);
        map.put("UAH", 30.0);
        currencyRateServiceImpl.setCurrencyRates(map);

        List<Expenses> list = new ArrayList<>();

        Expenses expense1 = new Expenses();
        expense1.setPrice(1.0);
        expense1.setCurrency("EUR");

        Expenses expense2 = new Expenses();
        expense2.setPrice(2.0);
        expense2.setCurrency("EUR");

        list.add(expense1);
        list.add(expense2);

        double result = currencyRateServiceImpl.convertCurrency(list, "UAH");

        Assert.assertEquals(90.0, result, 0.1);

    }



}