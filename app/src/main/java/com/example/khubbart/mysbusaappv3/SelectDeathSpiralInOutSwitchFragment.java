package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

//import android.widget.CompoundButton.OnCheckedChangeListener;


public class SelectDeathSpiralInOutSwitchFragment extends Fragment {

    private OnChangeInOutSwitchInteractionListener mListener;
    public ToggleButton inOutButton;
    public SelectDeathSpiralInOutSwitchFragment(){}

    private SelectDeathSpiralFrontBackSwitchFragment.OnChangeFrontBackSwitchInteractionListener m2Listener;
    public ToggleButton frontBackButton;


    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_select_death_spiral_in_out_switch,
                    container, false);

            inOutButton = view.findViewById(R.id.toggleButtonInOut);
            inOutButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isInOut) {
                    onInOutButtonChanged(isInOut);
                }
            });

        frontBackButton = view.findViewById(R.id.toggleButtonFrontBack);
        frontBackButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isFrontBack) {
                onFrontBackButtonChanged(isFrontBack);
            }
        });

            return view;
    }

    public void onInOutButtonChanged(boolean isInOut){
        if(mListener != null){
            mListener.onChangeInOutSwitchInteraction(isInOut);
        }
    }

    public void onFrontBackButtonChanged(boolean isFrontBack){
        if(m2Listener != null){
            m2Listener.onChangeFrontBackSwitchInteraction(isFrontBack);
        }
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnChangeInOutSwitchInteractionListener) {
            mListener = (OnChangeInOutSwitchInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeInOutSwitchInteractionListener");
        }

        if(context instanceof SelectDeathSpiralFrontBackSwitchFragment.OnChangeFrontBackSwitchInteractionListener) {
            m2Listener = (SelectDeathSpiralFrontBackSwitchFragment.OnChangeFrontBackSwitchInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeFrontBackSwitchInteractionListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    public interface OnChangeInOutSwitchInteractionListener{
        public void onChangeInOutSwitchInteraction(boolean isInOut);
    }

    public interface OnChangeFrontBackSwitchInteractionListener{
        public void onChangeFrontBackSwitchInteraction(boolean isFrontBack);
    }

}
