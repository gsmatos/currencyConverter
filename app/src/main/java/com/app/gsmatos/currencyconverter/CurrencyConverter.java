package com.app.gsmatos.currencyconverter;

import java.util.HashMap;

public class CurrencyConverter {

    public CurrencyConverter(HashMap<String, Object>  rates){
        this.rates = rates;
    }

    private HashMap<String, Object> rates;

    public double Convert(String from, String to, double value){
        if (rates != null){
            Object fromRateObj = rates.get(from.toUpperCase());
            Object toRateObj = rates.get(to.toUpperCase());
            if(fromRateObj != null && toRateObj != null){
                Double fromRate =   Double.valueOf(rates.get(from.toUpperCase()).toString());
                Double toRate =   Double.valueOf(rates.get(to.toUpperCase()).toString());
                Double euroValue = value / fromRate;
                return euroValue * toRate;
            }
        }
        return 0;
    }


}
