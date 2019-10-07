package com.app.gsmatos.currencyconverter;

import com.app.gsmatos.currencyconverter.ExchangeAPI.Rates;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class CurrencyConverterTest {

    private Rates rates;
    private CurrencyConverter converter;
    private Double value;

    @Before
    public void start(){
        rates = new Rates();
        rates.setBGN(1.0);
        rates.setAUD(2.0);
        converter =  new CurrencyConverter(rates);
        value = 1.0;
    }
    @Test
    public void should_convert_currency_given_rate(){
        double convertedValue = converter.Convert("bGN", "aUD", value);
        double expectedConversion = 2.0;
        assertEquals(convertedValue,expectedConversion, 0.01);
    }

    @Test
    public void should_return_zero_for_invalid_currency(){
        double convertedValue = converter.Convert("invalid", "aUD", value);
        double expectedConversion = 0.0;
        assertEquals(convertedValue,expectedConversion, 0.01);
    }
}
