package com.app.gsmatos.currencyconverter;


import com.app.gsmatos.currencyconverter.ExchangeAPI.CurrencyAPI;
import com.app.gsmatos.currencyconverter.ExchangeAPI.CurrencyAPIBuilder;
import com.app.gsmatos.currencyconverter.ExchangeAPI.UpdatedCurrency;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;


public class CurrencyConnectorTest {

    @Test
    public void should_return_updated_currency() throws IOException {
        CurrencyAPI currencyAPI = CurrencyAPIBuilder.build();
        Call<UpdatedCurrency> call = currencyAPI.loadCurrencies();
        Response<UpdatedCurrency> resp = call.execute();
        String euBase = resp.body().getBase();
        assertEquals(euBase, "EUR");
    }
}
