package com.example.khubbart.mysbusaappv3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


public class SelectJumpFragment extends Fragment {
    public RadioGroup rgJump;
    public RadioGroup rgRevs;

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_jump,
                container, false);
    }

}
