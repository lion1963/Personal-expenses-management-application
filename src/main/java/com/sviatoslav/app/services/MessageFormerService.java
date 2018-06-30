package com.sviatoslav.app.services;

public interface MessageFormerService {

    String list();
    String add(String date, double price, String currency, String product);
    String total(String currency);
    String clear(String date);

}
