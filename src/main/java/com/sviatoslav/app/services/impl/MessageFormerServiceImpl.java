package com.sviatoslav.app.services.impl;

import com.sviatoslav.app.entities.Expenses;
import com.sviatoslav.app.repositories.ExpensesRepository;
import com.sviatoslav.app.services.MessageFormerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MessageFormerServiceImpl implements MessageFormerService {

    @Autowired
    private ExpensesRepository  expensesRepository;

    @Autowired
    private CurrencyRateServiceImpl currencyRateServiceImpl;

    @Override
    public String list(){
        StringBuilder stringBuilder = new StringBuilder();
        List<Expenses> expenses = expensesRepository.findAllByOrderByDateAsc();

        for (int i=0; i < expenses.size(); i++) {
            Expenses e = expenses.get(i);
            if( i == 0){
                stringBuilder.append("\n").append(formatDate(e.getDate().toString()))
                        .append("\n").append(e.getProduct()).append(" ").append(e.getPrice()).append(" ").append(e.getCurrency());
            }else if (e.getDate().compareTo(expenses.get(i-1).getDate()) == 0 ){
                stringBuilder.append("\n").append(e.getProduct()).append(" ").append(e.getPrice()).append(" ").append(e.getCurrency());
            }else{
                stringBuilder.append("\n\n").append(formatDate(e.getDate().toString()))
                        .append("\n").append(e.getProduct()).append(" ").append(e.getPrice()).append(" ").append(e.getCurrency());
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String add(String date, double price, String currency, String product){
        StringBuilder stringBuilder = new StringBuilder();
        if(currencyRateServiceImpl.containsKey(currency) && price>0){

            Expenses expenses = new Expenses();
            expenses.setDate(parseDate(date));
            expenses.setPrice(price);
            expenses.setCurrency(currency);
            expenses.setProduct(product);
            expensesRepository.save(expenses);
            stringBuilder.append("Expense saved!");
        }
        else {
            stringBuilder.append("Incorrect currency or amount of money is negative! Expense is not saved!");
        }
        return stringBuilder.toString();
    }

    @Override
    public String total(String currency){
        StringBuilder stringBuilder = new StringBuilder();
        if (currencyRateServiceImpl.containsKey(currency)){
            stringBuilder.append(currencyRateServiceImpl.convertCurrency(expensesRepository.findAllByOrderByDateAsc(), currency)).append(" ").append(currency);
        }else{
            stringBuilder.append("Incorrect currency!");
        }
        return stringBuilder.toString();
    }

    @Override
    public String clear(String date)  {
        StringBuilder stringBuilder = new StringBuilder();
        expensesRepository.deleteByDate(parseDate(date));
        stringBuilder.append("Removed!");
        return stringBuilder.toString();
    }

    private String formatDate(String stringDate){
        String formattedDate = "";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(stringDate);
            formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            return  formattedDate;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    private Date parseDate (String stringDate)  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(stringDate);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
