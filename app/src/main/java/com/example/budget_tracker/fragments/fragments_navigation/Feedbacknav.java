package com.example.budget_tracker.fragments.fragments_navigation;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.budget_tracker.R;


public class Feedbacknav extends Fragment {


    public Feedbacknav() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feedbacknav, container, false);
    }

}
