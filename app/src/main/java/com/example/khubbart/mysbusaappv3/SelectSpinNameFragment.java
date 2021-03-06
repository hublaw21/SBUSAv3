package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


public class SelectSpinNameFragment extends Fragment {

    private OnChangeSpinNameRadioButtonInteractionListener mListener;
    public RadioGroup mRGSpinName;
    public String tempCode;

    public SelectSpinNameFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_select_spin_name,
                    container, false);

            mRGSpinName = view.findViewById(R.id.radioGroupSpinNames);

            mRGSpinName.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    onRadioButtonChanged(checkedId);
                }
            });

            return view;
    }

    public void onRadioButtonChanged(int id){
        switch (id) {
            case R.id.radioButtonSpinName1:
                tempCode = "CSp";
                break;
            case R.id.radioButtonSpinName2:
                tempCode = "LSp";
                break;
            case R.id.radioButtonSpinName3:
                tempCode = "SSp";
                break;
            case R.id.radioButtonSpinName4:
                tempCode = "USp";
                break;
            case R.id.radioButtonSpinName5:
                tempCode = "CoSp";
                break;
        }
        if(mListener != null){
            mListener.onChangeSpinNameRadioButtonInteraction(tempCode);
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnChangeSpinNameRadioButtonInteractionListener) {
            mListener = (OnChangeSpinNameRadioButtonInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeSpinNameRadioButtonInteractionListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    public interface OnChangeSpinNameRadioButtonInteractionListener{
        public void onChangeSpinNameRadioButtonInteraction(String tempCode);
    }
}
