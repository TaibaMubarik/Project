package com.example.budget_tracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.budget_tracker.activities.MainActivity;
import com.scrounger.countrycurrencypicker.library.Buttons.CountryCurrencyButton;
import com.scrounger.countrycurrencypicker.library.Country;
import com.example.budget_tracker.activities.MainActivity;
import com.scrounger.countrycurrencypicker.library.Currency;
import com.scrounger.countrycurrencypicker.library.Listener.CountryCurrencyPickerListener;



public class setting extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private Object CountryCurrencyButton;

    public setting() {
    }
    public static setting newInstance(String param1, String param2) {
        setting fragment = new setting();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
////
////        CountryCurrencyButton button = (CountryCurrencyButton) findViewById(R.id.button);
////        button.setOnClickListener(new CountryCurrencyPickerListener() {
////            @Override
////            public void onSelectCountry(Country country) {
////                if (country.getCurrency() == null) {
////                    Toast.makeText(MainActivity.this,
////                            String.format("name: %s\ncode: %s", country.getName(), country.getCode())
////                            , Toast.LENGTH_SHORT).show();
////                } else {
////                    Toast.makeText(MainActivity.this,
////                            String.format("name: %s\ncurrencySymbol: %s", country.getName(), country.getCurrency().getSymbol())
////                            , Toast.LENGTH_SHORT).show();
////                }
////            }
////
////            @Override
////            public void onSelectCurrency(Currency currency) {
////
////
////            }
//
//
//        });



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);




        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }


}