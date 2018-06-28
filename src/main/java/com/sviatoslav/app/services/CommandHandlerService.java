package com.sviatoslav.app.services;

import com.sviatoslav.app.entities.Expenses;
import com.sviatoslav.app.repositories.ExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Clear;
import org.springframework.shell.standard.commands.Quit;

import javax.transaction.Transactional;
import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ShellComponent
public class CommandHandlerService {

    @Autowired
    private CurrencyRateService currencyRateService;

    @Autowired
    private ExpensesRepository expensesRepository;

    @ShellMethod("Display list of all expenses.")
    public void list(){
        List<Expenses> expenses = expensesRepository.findAllByOrderByDateAsc();
        for (int i=0; i < expenses.size(); i++) {
            Expenses e = expenses.get(i);
            if( i == 0){
                System.out.println(formatDate(e.getDate().toString())+"\n"+
                                   e.getProduct()+" "+e.getPrice()+" "+e.getCurrency());
            }else if (e.getDate().compareTo(expenses.get(i-1).getDate()) == 0 ){
                System.out.println(e.getProduct()+" "+e.getPrice()+" "+e.getCurrency());
            }else{
                System.out.println("\n"+
                                    formatDate(e.getDate().toString())+"\n"+
                                    e.getProduct()+" "+e.getPrice()+" "+e.getCurrency());
                }
        }
    }

    @ShellMethod("Add expense to the list.")
    public void add(@Pattern(regexp = "[12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])") String date,
                    double price,
                    @Pattern(regexp = "[A-Z]{3}") String currency,
                    String product){

        if(currencyRateService.getCurrencyRates().containsKey(currency) && price>0){
            Expenses expenses = new Expenses();
            expenses.setDate(parseDate(date));
            expenses.setPrice(price);
            expenses.setCurrency(currency);
            expenses.setProduct(product);
            expensesRepository.save(expenses);
            System.out.println("Expense saved!");}
        else
            System.out.println("Incorrect currency or amount of money is negative! Expense is not saved!");

    }

    @ShellMethod("Show total amount of spent money in specified currency.")
    public void total(@Pattern(regexp = "[A-Z]{3}") String currency){
        if (currencyRateService.getCurrencyRates().containsKey(currency)){
            System.out.println(currencyRateService.convertCurrency(expensesRepository.findAllByOrderByDateAsc(), currency)+" "+currency);
        }else
            System.out.println("Incorrect currency!");
    }

    @ShellMethod("Remove all expenses for specified date.")
    @Transactional
    public void clear(@Pattern(regexp = "[12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])") String date)  {
            expensesRepository.deleteByDate(parseDate(date));
            System.out.println("Removed!");
    }

    @ShellMethod("End the work of system.")
    public void exit(){
        System.out.println("Good bye!");
        System.exit(0);
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

}
