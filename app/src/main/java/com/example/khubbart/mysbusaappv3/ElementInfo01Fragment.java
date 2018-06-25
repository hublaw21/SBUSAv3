package com.example.khubbart.mysbusaappv3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ElementInfo01Fragment extends Fragment {

    public ElementInfo01Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_elementinfo01,
                container, false);
        return view;
    }
}

