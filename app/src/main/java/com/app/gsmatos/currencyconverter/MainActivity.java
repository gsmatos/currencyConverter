package com.app.gsmatos.currencyconverter;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.gsmatos.currencyconverter.Overview.CurrencyViewModel;
import com.gsm.currencyconvert.R;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    private CurrencyViewModel currencyViewModel;
    private List<String> currencies;

    Spinner spinnerFrom;
    Spinner spinnerTo;

    EditText fromValue;
    EditText toValue;

    TextView updateText;

    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currencies = Arrays.asList(getResources().getStringArray(R.array.currencies));
        currencyViewModel = ViewModelProviders.of(this).get(CurrencyViewModel.class);
        FillSpinners();

        updateText = findViewById(R.id.updateCurrencyText);

        fromValue = findViewById(R.id.fromCurrencyValue);
        toValue = findViewById(R.id.toCurrencyValue);

        update = findViewById(R.id.updateCurrencyBtn);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currencyViewModel.getNewCurrencies();
            }
        });

        fromValue.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    ConvertCurrency();
                }
                return false;
            }
        });

        toValue.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    RevertConversion();
                }
                return false;
            }
        });

        currencyViewModel.getCurrency().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String currencies) {
                // Update the cached copy of the words in the adapter.
                currencyViewModel.updateRates(currencies);
                ConvertCurrency();
            }
        });

        currencyViewModel.getDate().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer date) {
                Boolean shouldUpdate = currencyViewModel.shouldUpdateCurrencies(date);
                String text = currencyViewModel.shouldUpdateCurrencies(date) ?
                        getResources().getString(R.string.updateCurrencies) : getResources().getString(R.string.currenciesAreUpdated);
                updateText.setText(text);

                int updateVisible = shouldUpdate? View.VISIBLE : View.GONE;
                    update.setVisibility(updateVisible);
            }
        });
    }

    private void FillSpinners() {
        spinnerFrom = findViewById(R.id.fromCurrencySpin);
        spinnerTo = findViewById(R.id.toCurrencySpin);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);
        spinnerTo.setOnItemSelectedListener(this);
        spinnerFrom.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.fromCurrencySpin:
                ConvertCurrency();
                break;
            case R.id.toCurrencySpin:
                RevertConversion();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void ConvertCurrency() {
        String toCurr =  spinnerTo.getSelectedItem().toString();
        String fromCurr =  spinnerFrom.getSelectedItem().toString();

        String from = fromValue.getText().toString();
        if(!from.isEmpty()){
            Double converted = currencyViewModel.convertCurrency(fromCurr, toCurr, Double.parseDouble(from.replace(',', '.')));
            DecimalFormat format = new DecimalFormat("#0.00");
            toValue.setText(format.format(converted));
        }
    }

    private void RevertConversion() {
        String toCurr =  spinnerFrom.getSelectedItem().toString();
        String fromCurr =  spinnerTo.getSelectedItem().toString();

        String from = toValue.getText().toString();
        if(!from.isEmpty()){
            Double converted = currencyViewModel.convertCurrency(fromCurr, toCurr, Double.parseDouble(from.replace(',', '.')));
            DecimalFormat format = new DecimalFormat("#0.00");
            fromValue.setText(format.format(converted));
        }
    }


}
