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


public class SelectDeathSpiralFrontBackSwitchFragment extends Fragment {

    private OnChangeFrontBackSwitchInteractionListener mListener;
    public ToggleButton frontBackButton;

    private SelectDeathSpiralInOutSwitchFragment.OnChangeInOutSwitchInteractionListener m2Listener;
    public ToggleButton inOutButton;

    public SelectDeathSpiralFrontBackSwitchFragment(){}

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_select_death_spiral_front_back_switch,
                    container, false);

            frontBackButton = view.findViewById(R.id.toggleButtonFrontBack);
            frontBackButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isFrontBack) {
                    onFrontBackButtonChanged(isFrontBack);
                }
            });

        inOutButton = view.findViewById(R.id.toggleButtonInOut);
        inOutButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isInOut) {
                onInOutButtonChanged(isInOut);
            }
        });

            return view;
    }

    public void onFrontBackButtonChanged(boolean isFrontBack){
        if(mListener != null){
            mListener.onChangeFrontBackSwitchInteraction(isFrontBack);
        }
    }

    public void onInOutButtonChanged(boolean isInOut){
        if(m2Listener != null){
            m2Listener.onChangeInOutSwitchInteraction(isInOut);
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnChangeFrontBackSwitchInteractionListener) {
            mListener = (OnChangeFrontBackSwitchInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeFrontBackSwitchInteractionListener");
        }

        if(context instanceof SelectDeathSpiralInOutSwitchFragment.OnChangeInOutSwitchInteractionListener) {
            m2Listener = (SelectDeathSpiralInOutSwitchFragment.OnChangeInOutSwitchInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeInOutSwitchInteractionListener");
        }

    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    public interface OnChangeFrontBackSwitchInteractionListener{
        public void onChangeFrontBackSwitchInteraction(boolean isFrontBack);
    }

    public interface OnChangeInOutSwitchInteractionListener{
        public void onChangeInOutSwitchInteraction(boolean isInOut);
    }

}
