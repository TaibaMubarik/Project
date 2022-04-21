package com.example.budget_tracker.fragments.fragments_navigation;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.budget_tracker.R;

public class GraphFragment extends Fragment {

    Intent intent;

    public GraphFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_graph, container, false);
        return rootView;
    }

}
