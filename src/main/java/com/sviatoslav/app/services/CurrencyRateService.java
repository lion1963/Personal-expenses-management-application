package com.sviatoslav.app.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sviatoslav.app.entities.Expenses;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@Data
public class CurrencyRateService {

    @Value("${app.fixerURL}")
    private String currencyRatesURL;

    @Value("${app.access_key}")
    private String accessKey;

    private Map<String, Double> currencyRates;

    @Autowired
    private RestTemplate restTemplate;

    private String getJsonFromServer(){

        return restTemplate.getForObject(currencyRatesURL+accessKey, String.class);
    }

    private Map<String, Double> parseJson(String json){
        HashMap<String, Double> rates = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(json);

            JsonNode baseNode = rootNode.get("base");
            rates.put(baseNode.asText(), 1.0); //base rate always is 1.0

            Iterator<Map.Entry<String, JsonNode>> rateNodes = rootNode.get("rates").fields();
            while (rateNodes.hasNext()){
                Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) rateNodes.next();
                rates.put(entry.getKey(), entry.getValue().asDouble());
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return rates;
    }

    public double convertCurrency(List<Expenses> expenses, String currency){
        double sum = 0;
        for (Expenses e : expenses){
            sum+= e.getPrice()/currencyRates.get(e.getCurrency());
        }
        return  sum*currencyRates.get(currency);
    }

    @Scheduled(fixedDelay = 3600000) // delay = 1 hour
    public void getCurrency(){
        setCurrencyRates(parseJson(getJsonFromServer()));
    }
}
