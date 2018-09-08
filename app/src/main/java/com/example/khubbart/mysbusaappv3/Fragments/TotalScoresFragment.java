package com.example.khubbart.mysbusaappv3.Fragments;
//Displays Total Scores when scoring programs
//2018-09-07: attept to use new architecural components

import android.app.Fragment;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khubbart.mysbusaappv3.R;
import com.example.khubbart.mysbusaappv3.ViewModels.TotalScoresViewModel;

public class TotalScoresFragment extends android.support.v4.app.Fragment{
    private TotalScoresViewModel totalScoresViewModel;
    //private ViewModelProvider totalScoresViewModelProvider;
    private TextView segmentTotal;
    private TextView elementTotal;
    private TextView componentTotal;
    private TextView deductionTotal;

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_total_scores, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        totalScoresViewModel = ViewModelProviders.of(this).get(TotalScoresViewModel.class);
        //totalScoresViewModel = totalScoresViewModelProvider.getClass(TotalScoresViewModel.class);

        segmentTotal = getView().findViewById(R.id.segmentTotal);
        elementTotal = getView().findViewById(R.id.elementTotal);
        componentTotal = getView().findViewById(R.id.componentTotal);
        deductionTotal = getView().findViewById(R.id.deductionsTotal);
    }

}
