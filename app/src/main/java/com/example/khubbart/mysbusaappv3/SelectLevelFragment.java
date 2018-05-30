package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


public class SelectLevelFragment extends Fragment {

    private OnChangeLevelRadioButtonInteractionListener mListener;
    public RadioGroup mRGLevel;
    public String tempCode;

    public SelectLevelFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_select_level,
                    container, false);

            mRGLevel = view.findViewById(R.id.radioGroupLevels);

            mRGLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    onRadioButtonChanged(checkedId);
                }
            });

            return view;
    }
    public void onRadioButtonChanged(int id){
        switch (id) {
            case R.id.radioButtonLevelB:
                tempCode = "B";
                break;
            case R.id.radioButtonLevel1:
                tempCode = "1";
                break;
            case R.id.radioButtonLevel2:
                tempCode = "2";
                break;
            case R.id.radioButtonLevel3:
                tempCode = "3";
                break;
            case R.id.radioButtonLevel4:
                tempCode = "4";
                break;
        }
        if(mListener != null){
            mListener.onChangeLevelRadioButtonInteraction(tempCode);
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnChangeLevelRadioButtonInteractionListener) {
            mListener = (OnChangeLevelRadioButtonInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeLevelRadioButtonInteractionListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    public interface OnChangeLevelRadioButtonInteractionListener{
        public void onChangeLevelRadioButtonInteraction(String tempCode);
    }
}
