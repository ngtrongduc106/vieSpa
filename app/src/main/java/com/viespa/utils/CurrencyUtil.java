package com.viespa.utils;

import javafx.beans.property.SimpleStringProperty;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class CurrencyUtil {
    public static SimpleStringProperty formatCur (String price){
        Locale locale = new Locale("en", "US");
//        Currency cur = Currency.getInstance("USD");
        NumberFormat curFormat = NumberFormat.getCurrencyInstance(locale);
        curFormat.setMaximumFractionDigits(0); //Set number  after '.'
//        String symbol = cur.getSymbol();
        double d =Double.parseDouble(price);
        String s = curFormat.format(d);
        return new SimpleStringProperty(s);
    }
}
