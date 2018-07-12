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
    public RadioGroup mRGSegment;
    public int tempIndex;

    public SelectSegmentFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_select_segment,
                    container, false);

            mRGSegment = view.findViewById(R.id.radioGroupSegments);

            mRGSegment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int selectedId = mRGSegment.getCheckedRadioButtonId();
                    onRadioButtonChanged(selectedId);  //I only need this for manipulations before returning info
            }
            });

            return view;
    }

    public void onRadioButtonChanged(int mSelectedID){
        //adjust this for selecting or returning skater level
        View selectedButton = mRGSegment.findViewById(mSelectedID);
        int buttonIndex = mRGSegment.indexOfChild(selectedButton);
        tempIndex = buttonIndex;

        if(mListener != null){
            mListener.onChangeSegmentRadioButtonInteraction(tempIndex);
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
                    + "must implement OnChangeSegmentRadioButtonInteractionListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    public interface OnChangeSegmentRadioButtonInteractionListener{
        public void onChangeSegmentRadioButtonInteraction(int tempIndex);
    }

}
