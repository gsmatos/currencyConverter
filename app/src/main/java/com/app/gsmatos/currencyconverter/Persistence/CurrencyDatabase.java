package com.app.gsmatos.currencyconverter.Persistence;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Currency.class}, version = 2)
public abstract class CurrencyDatabase extends RoomDatabase {

    public abstract CurrencyDao currencyDao();

    static volatile CurrencyDatabase INSTANCE;

    public static CurrencyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CurrencyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CurrencyDatabase.class, "currency_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}