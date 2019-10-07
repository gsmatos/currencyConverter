package com.app.gsmatos.currencyconverter.Persistence;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


public class CurrencyRepository {

    private CurrencyDao currencyDao;
    private LiveData<String> currency;
    private LiveData<Integer> date;

    public CurrencyRepository(Application application) {
        CurrencyDatabase db = CurrencyDatabase.getDatabase(application);
        currencyDao = db.currencyDao();
        currency = currencyDao.getCurrencies(Currency.currencyKey);
        date = currencyDao.getDate(Currency.currencyKey);
    }

    public LiveData<String> getCurrencies() {
            return currency;
        };

    public LiveData<Integer> getDate() {
        return date;
    };


    public void insert (Currency currency) {
        new insertAsyncTask(currencyDao).execute(currency);
    }

    private static class insertAsyncTask extends AsyncTask<Currency, Void, Void> {

        private CurrencyDao currencyDao;

        insertAsyncTask(CurrencyDao dao) {
            currencyDao = dao;
        }

        @Override
        protected Void doInBackground(final Currency... params) {
            currencyDao.insert(params[0]);
            return null;
        }
    }
}
