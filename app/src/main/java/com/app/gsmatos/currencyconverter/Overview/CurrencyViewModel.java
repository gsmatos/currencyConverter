package com.app.gsmatos.currencyconverter.Overview;

import android.app.Application;
import android.text.format.DateUtils;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.gsmatos.currencyconverter.CurrencyController;
import com.app.gsmatos.currencyconverter.CurrencyConverter;
import com.app.gsmatos.currencyconverter.Persistence.CurrencyRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class CurrencyViewModel extends AndroidViewModel {

    private CurrencyRepository repository;

    private LiveData<String> currencyTable;

    private LiveData<Integer> date;

    private HashMap<String, Object> rates;

    private CurrencyConverter converter;

    private CurrencyController controller;

    public CurrencyViewModel (Application application) {
        super(application);
        repository = new CurrencyRepository(application);
        currencyTable = repository.getCurrencies();
        date = repository.getDate();
        controller = new CurrencyController(application);
    }

    public LiveData<String> getCurrency() { return currencyTable; }

    public LiveData<Integer> getDate() { return date; }

    public Double convertCurrency(String  from, String to, Double value) {
        if(from != null && to != null && value != null  && converter != null){
            return converter.Convert(from, to, value);
        }
        return 0.0;
    }

    public void updateRates(String currencies){
        Gson gson = new Gson();
        Type empMapType = new TypeToken<HashMap<String, Object>>() {}.getType();
        rates = gson.fromJson(currencies, empMapType);
        converter = new CurrencyConverter(rates);
    }

    public Boolean shouldUpdateCurrencies(Integer date) {

        if (date != null) {
            Long dateLong = Long.valueOf(date.longValue() * 1000);
             return !DateUtils.isToday(dateLong);
        }
        return true;
    }

    public void getNewCurrencies() {
        controller.updateCurrencies();
    }
}