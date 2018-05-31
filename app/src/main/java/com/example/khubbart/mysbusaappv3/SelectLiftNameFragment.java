package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


public class SelectLiftNameFragment extends Fragment {

    private OnChangeLiftNameRadioButtonInteractionListener mListener;
    public RadioGroup mRGLiftName;
    public String tempCode;

    public SelectLiftNameFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_select_lift_name,
                    container, false);

            mRGLiftName = view.findViewById(R.id.radioGroupLiftNames);

            mRGLiftName.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    onRadioButtonChanged(checkedId);
                }
            });

            return view;
    }

    public void onRadioButtonChanged(int id){
        switch (id) {
            case R.id.radioButtonLiftName1:
                tempCode = "BLi";
                break;
            case R.id.radioButtonLiftName2:
                tempCode = "RLi";
                break;
            case R.id.radioButtonLiftName3:
                tempCode = "SLi";
                break;
            case R.id.radioButtonLiftName4:
                tempCode = "TLi";
                break;
            case R.id.radioButtonLiftName5:
                tempCode = "ALi";
                break;
        }
        if(mListener != null){
            mListener.onChangeLiftNameRadioButtonInteraction(tempCode);
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnChangeLiftNameRadioButtonInteractionListener) {
            mListener = (OnChangeLiftNameRadioButtonInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeLiftNameRadioButtonInteractionListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    public interface OnChangeLiftNameRadioButtonInteractionListener{
        public void onChangeLiftNameRadioButtonInteraction(String tempCode);
    }
}
