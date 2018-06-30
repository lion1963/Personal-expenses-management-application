package com.sviatoslav.app.services.impl;

import com.sviatoslav.app.entities.Expenses;
import com.sviatoslav.app.model.Rates;

import com.sviatoslav.app.services.CurrencyRateService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {

    @Value("${app.fixerURL}")
    private String currencyRatesURL;

    @Value("${app.access_key}")
    private String accessKey;

    @Setter
    @Getter
    private Map<String, Double> currencyRates;

    @Autowired
    private RestTemplate restTemplate;

    private Rates getJsonFromServer() {
        return restTemplate.getForObject(currencyRatesURL + accessKey, Rates.class);
    }

    @Override
    public boolean containsKey(String currency) {
        return currencyRates.containsKey(currency);
    }

    @Override
    public double convertCurrency(List<Expenses> expenses, String currency){
        double sum = 0;
        for (Expenses e : expenses){
            sum+= e.getPrice()/getCurrencyRates().get(e.getCurrency());
        }
        return  sum * getCurrencyRates().get(currency);
    }

    @Scheduled(fixedDelay = 3600000) // delay = 1 hour
    @Override
    public void getCurrency() {
        setCurrencyRates(getJsonFromServer().getRates());
    }

}
