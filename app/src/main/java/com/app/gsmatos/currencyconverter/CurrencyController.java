package com.app.gsmatos.currencyconverter;

import android.app.Application;
import android.widget.Toast;

import com.app.gsmatos.currencyconverter.ExchangeAPI.CurrencyAPI;
import com.app.gsmatos.currencyconverter.ExchangeAPI.CurrencyAPIBuilder;
import com.app.gsmatos.currencyconverter.ExchangeAPI.UpdatedCurrency;
import com.app.gsmatos.currencyconverter.Persistence.Currency;
import com.app.gsmatos.currencyconverter.Persistence.CurrencyRepository;
import com.google.gson.Gson;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyController implements Callback<UpdatedCurrency> {

    private Application application;

    private CurrencyRepository repository;

    public CurrencyController(Application application){
        this.application = application;
        repository = new CurrencyRepository(application);
    }

    public void updateCurrencies() {
        CurrencyAPI currencyAPI = CurrencyAPIBuilder.build();
        Call<UpdatedCurrency> call = currencyAPI.loadCurrencies();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<UpdatedCurrency> call, Response<UpdatedCurrency> response) {

        if(response.isSuccessful()) {
            UpdatedCurrency changesList = response.body();
            Currency currency = new Currency();
            int today = (int) (new Date().getTime()/1000);
            currency.setDate(today);
            Gson gson = new Gson();
            currency.setCurrencies(gson.toJson(changesList.getRates()));
            repository.insert(currency);
            Toast toast = Toast.makeText(application,
                    "Rates successfully updated",
                    Toast.LENGTH_SHORT);
            toast.show();
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<UpdatedCurrency> call, Throwable t) {
        Toast toast = Toast.makeText(application,
                "No internet connection",
                Toast.LENGTH_SHORT);
        toast.show();
    }
}

