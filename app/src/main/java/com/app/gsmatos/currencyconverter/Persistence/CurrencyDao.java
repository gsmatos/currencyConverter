package com.app.gsmatos.currencyconverter.Persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public interface CurrencyDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Currency currency);

    @Query("SELECT currencies_col from currency_table where key_col = :key")
    LiveData<String> getCurrencies(String key);

    @Query("SELECT date_col from currency_table where key_col = :key")
    LiveData<Integer> getDate(String key);
}
