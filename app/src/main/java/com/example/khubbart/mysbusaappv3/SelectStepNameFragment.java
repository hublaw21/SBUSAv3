package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


public class SelectStepNameFragment extends Fragment {

    private OnChangeStepNameRadioButtonInteractionListener mListener;
    public RadioGroup mRGStepName;
    public String tempCode;

    public SelectStepNameFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_select_step_name,
                    container, false);

            mRGStepName = view.findViewById(R.id.radioGroupStepNames);

            mRGStepName.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    onRadioButtonChanged(checkedId);
                }
            });

            return view;
    }

    public void onRadioButtonChanged(int id){
        switch (id) {
            case R.id.radioButtonStepName1:
                tempCode = "StSq";
                break;
            case R.id.radioButtonStepName2:
                tempCode = "ChSq";
                break;
         }
        if(mListener != null){
            mListener.onChangeStepNameRadioButtonInteraction(tempCode);
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnChangeStepNameRadioButtonInteractionListener) {
            mListener = (OnChangeStepNameRadioButtonInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeStepNameRadioButtonInteractionListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    public interface OnChangeStepNameRadioButtonInteractionListener{
        public void onChangeStepNameRadioButtonInteraction(String tempCode);
    }
}
