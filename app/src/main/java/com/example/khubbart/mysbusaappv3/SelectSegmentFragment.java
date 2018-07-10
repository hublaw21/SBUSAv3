package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


public class SelectSegmentFragment extends Fragment {

    private OnChangeSegmentRadioButtonInteractionListener mListener;
    public RadioGroup mRGLevel;
    public String tempCode;

    public SelectSegmentFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_select_segment,
                    container, false);

            mRGLevel = view.findViewById(R.id.radioGroupSegments);

            mRGLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    onRadioButtonChanged(checkedId);
                }
            });

            return view;
    }

    public void onRadioButtonChanged(int id){
        //adjust this for selecting or returning skater level
        tempCode = "test";

        if(mListener != null){
            mListener.onChangeSegmentRadioButtonInteraction(tempCode);
        }
    }


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnChangeSegmentRadioButtonInteractionListener) {
            //mListener = (SelectSegmentFragment.OnChangeSegmentRadioButtonInteractionListener) context;
            mListener = (OnChangeSegmentRadioButtonInteractionListener) context;
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

    public interface OnChangeSegmentRadioButtonInteractionListener{
        public void onChangeSegmentRadioButtonInteraction(String tempCode);
    }

}
