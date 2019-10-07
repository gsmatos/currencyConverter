package com.app.gsmatos.currencyconverter.ExchangeAPI;

import com.squareup.moshi.Moshi;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class CurrencyAPIBuilder {

    static final String BASE_URL = "https://api.exchangeratesapi.io/";

    public static CurrencyAPI build(){
        Moshi moshi = new Moshi.Builder()
                      .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();

        return retrofit.create(CurrencyAPI.class);
    }
}
