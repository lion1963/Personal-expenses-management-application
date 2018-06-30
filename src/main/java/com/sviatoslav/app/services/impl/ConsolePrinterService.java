package com.sviatoslav.app.services.impl;

import com.sviatoslav.app.services.PrinterService;
import org.springframework.stereotype.Service;

@Service
public class ConsolePrinterService implements PrinterService {

    public void print(String message){
        System.out.println(message);
    }
}
