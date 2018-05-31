package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


public class SelectGroupFragment extends Fragment {

    private OnChangeGroupRadioButtonInteractionListener mListener;
    public RadioGroup mRGGroup;
    public String tempCode;

    public SelectGroupFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_select_group,
                    container, false);

            mRGGroup = view.findViewById(R.id.radioGroupGroups);

            mRGGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    onRadioButtonChanged(checkedId);
                }
            });

            return view;
    }
    public void onRadioButtonChanged(int id){
        switch (id) {
            case R.id.radioButtonGroup1:
                tempCode = "1";
                break;
            case R.id.radioButtonGroup2:
                tempCode = "2";
                break;
            case R.id.radioButtonGroup3:
                tempCode = "3";
                break;
            case R.id.radioButtonGroup4:
                tempCode = "4";
                break;
            case R.id.radioButtonGroup5:
                tempCode = "5";
                break;
        }
        if(mListener != null){
            mListener.onChangeGroupRadioButtonInteraction(tempCode);
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnChangeGroupRadioButtonInteractionListener) {
            mListener = (OnChangeGroupRadioButtonInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeGroupRadioButtonInteractionListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    public interface OnChangeGroupRadioButtonInteractionListener{
        public void onChangeGroupRadioButtonInteraction(String tempCode);
    }
}
