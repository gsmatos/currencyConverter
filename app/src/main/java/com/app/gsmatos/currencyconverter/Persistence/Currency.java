package com.app.gsmatos.currencyconverter.Persistence;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "currency_table")
public class Currency {

    public static final String currencyKey = "currency_key";

    @PrimaryKey
    @ColumnInfo(name = "key_col")
    @NonNull
    private String key = currencyKey;

    @ColumnInfo(name = "date_col")
    private Integer date;

    @ColumnInfo(name = "currencies_col")
    private String currencies;

    public void setDate(int date) {this.date = date;}

    public Integer getDate(){return this.date;}

    public void setCurrencies(String currencies) {this.currencies = currencies;}

    public String getCurrencies(){return this.currencies;}

    public void setKey(String key) {this.key = key;}

    public String getKey(){return this.key;}
}
