package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


public class SelectPairSpinNameFragment extends Fragment {

    private OnChangePairSpinNameRadioButtonInteractionListener mListener;
    public RadioGroup mRGPairSpinName;
    public String tempCode;

    public SelectPairSpinNameFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_select_pair_spin_name,
                    container, false);

            mRGPairSpinName = view.findViewById(R.id.radioGroupPairSpinNames);

            mRGPairSpinName.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    onRadioButtonChanged(checkedId);
                }
            });

            return view;
    }

    public void onRadioButtonChanged(int id){
        switch (id) {
            case R.id.radioButtonPairSpinName1:
                tempCode = "PSp";
                break;
            case R.id.radioButtonPairSpinName2:
                tempCode = "PCoSp";
                break;
            case R.id.radioButtonPairSpinName3:
                tempCode = "PiF";
                break;
        }
        if(mListener != null){
            mListener.onChangePairSpinNameRadioButtonInteraction(tempCode);
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnChangePairSpinNameRadioButtonInteractionListener) {
            mListener = (OnChangePairSpinNameRadioButtonInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangePairSpinNameRadioButtonInteractionListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    public interface OnChangePairSpinNameRadioButtonInteractionListener{
        public void onChangePairSpinNameRadioButtonInteraction(String tempCode);
    }
}
