package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


public class SelectSkaterLevelFragment extends Fragment {

    private OnChangeSkaterLevelRadioButtonInteractionListener mListener;
    public RadioGroup mRGLevel;
    public int tempIndex;

    public SelectSkaterLevelFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_select_skater_level,
                    container, false);

            mRGLevel = view.findViewById(R.id.radioGroupSkaterLevels);

            mRGLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int selectedId = mRGLevel.getCheckedRadioButtonId();
                    onRadioButtonChanged(selectedId);  //I only need this for manipulations before returning info
                }
            });
            return view;
    }

    public void onRadioButtonChanged(int mSelectedID){
        View selectedButton = mRGLevel.findViewById(mSelectedID);
        int buttonIndex = mRGLevel.indexOfChild(selectedButton);
        tempIndex = buttonIndex;

        if(mListener != null){
            mListener.onChangeSkaterLevelRadioButtonInteraction(tempIndex);
        }
    }


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnChangeSkaterLevelRadioButtonInteractionListener) {
            //mListener = (SelectSkaterLevelFragment.OnChangeSkaterLevelRadioButtonInteractionListener) context;
            mListener = (OnChangeSkaterLevelRadioButtonInteractionListener) context;
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

    public interface OnChangeSkaterLevelRadioButtonInteractionListener{
        public void onChangeSkaterLevelRadioButtonInteraction(int tempIndex);
    }

}
