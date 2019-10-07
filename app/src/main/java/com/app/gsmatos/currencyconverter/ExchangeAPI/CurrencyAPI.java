package com.app.gsmatos.currencyconverter.ExchangeAPI;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CurrencyAPI {
    @GET("latest/")
    Call<UpdatedCurrency> loadCurrencies();
}
