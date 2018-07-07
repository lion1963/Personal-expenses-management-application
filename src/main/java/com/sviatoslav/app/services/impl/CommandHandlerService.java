package com.sviatoslav.app.services.impl;

import com.sviatoslav.app.services.PrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.transaction.Transactional;
import javax.validation.constraints.Pattern;

@ShellComponent
public class CommandHandlerService {

    @Autowired
    private PrinterService printerService;

    @Autowired
    private MessageFormerServiceImpl messageFormerServiceImpl;

    private final String dateErrorMessage = "Date should fit pattern: YYYY-MM-DD, where YYYY - year, MM - month and DD - day";
    private final String currencyErrorMessage = "Currency should fit pattern - XXX, where XXX - uppercase abbreviation of currency";

    @ShellMethod("Display list of all expenses.")
    public void list(){

        printerService.print(messageFormerServiceImpl.list());
    }

    @ShellMethod("Add expense to the list.")
    public void add(@Pattern(regexp = "[12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])", message = dateErrorMessage) String date,
                    double price,
                    @Pattern(regexp = "[A-Z]{3}", message = currencyErrorMessage) String currency,
                    String product){

        printerService.print(messageFormerServiceImpl.add(date, price, currency, product));
    }

    @ShellMethod("Show total amount of spent money in specified currency.")
    public void total(@Pattern(regexp = "[A-Z]{3}", message = currencyErrorMessage) String currency){

        printerService.print(messageFormerServiceImpl.total(currency));
    }

    @ShellMethod("Remove all expenses for specified date.")
    @Transactional
    public void clear(@Pattern(regexp = "[12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])", message = dateErrorMessage) String date)  {

        printerService.print(messageFormerServiceImpl.clear(date));
    }

    @ShellMethod("End the work of system.")
    public void exit(){
        System.exit(0);
    }
}
