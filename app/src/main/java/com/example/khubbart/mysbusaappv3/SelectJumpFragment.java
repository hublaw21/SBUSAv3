package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.util.Objects;


public class SelectJumpFragment extends Fragment {

    private OnChangeJumpRadioButtonInteractionListener mListener;
    public RadioGroup mRGJump;
    public String tempCode;

    public SelectJumpFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_select_jump,
                    container, false);

            mRGJump = view.findViewById(R.id.radioGroupJumps);

            mRGJump.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    onRadioButtonChanged(checkedId);
                }
            });

            return view;
    }

    public void onRadioButtonChanged(int id){
        tempCode = "";
        switch (id) {
            case R.id.radioButtonJump1:
                tempCode = "T";
                break;
            case R.id.radioButtonJump2:
                tempCode  = "S";
                break;
            case R.id.radioButtonJump3:
                tempCode  = "F";
                break;
            case R.id.radioButtonJump4:
                tempCode  = "Lo";
                break;
            case R.id.radioButtonJump5:
                tempCode  = "Lz";
                break;
            case R.id.radioButtonJump6:
                tempCode  = "A";
                break;
        }

        if(mListener != null){
            mListener.onChangeJumpRadioButtonInteraction(tempCode);
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnChangeJumpRadioButtonInteractionListener) {
            mListener = (OnChangeJumpRadioButtonInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeJumpRadioButtonInteractionListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    public interface OnChangeJumpRadioButtonInteractionListener{
        public void onChangeJumpRadioButtonInteraction(String tempCode);
    }

}
