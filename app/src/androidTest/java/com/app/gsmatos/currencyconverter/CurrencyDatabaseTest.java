package com.app.gsmatos.currencyconverter;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.app.gsmatos.currencyconverter.Persistence.CurrencyDao;
import com.app.gsmatos.currencyconverter.Persistence.CurrencyDatabase;
import com.app.gsmatos.currencyconverter.Persistence.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class CurrencyDatabaseTest {

    private CurrencyDao currencyDao;
    private CurrencyDatabase db;


    @Before
    public void createDb() {
        Context context  = InstrumentationRegistry.getInstrumentation().getTargetContext();

        db = Room.inMemoryDatabaseBuilder(context, CurrencyDatabase.class)
                .allowMainThreadQueries()
                .build();

        currencyDao = db.currencyDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void should_insert_rates(){

        Currency user = new Currency();
        currencyDao.insert(user);
        LiveData<String> tonight = currencyDao.getCurrencies(Currency.currencyKey);
        System.out.println(tonight.getValue());

    }
}

