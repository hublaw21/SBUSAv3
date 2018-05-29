package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


public class SelectLevelFragment extends Fragment {

    //private OnChangeLevelRadioButtonInteractionListener mListener;
    public RadioGroup mRGLevel;

    public SelectLevelFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_select_level,
                    container, false);

            /*
            mRGLevel = view.findViewById(R.id.radioGroupLevels);

            mRGLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    onRadioButtonChanged(checkedId);
                }
            });
            */

            return view;
    }
    /*
    public void onRadioButtonChanged(int id){
        if(mListener != null){
            mListener.onChangeLevelRadioButtonInteraction(id);
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
        public void onChangeLevelRadioButtonInteraction(int id);
    }
    */
}
