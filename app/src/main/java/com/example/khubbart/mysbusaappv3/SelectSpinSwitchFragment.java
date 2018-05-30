package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class SelectSpinSwitchFragment extends Fragment {

    private OnChangeSpinSwitchInteractionListener mListener;
    public ToggleButton flyingButton;
    public ToggleButton footChangeButton;

    public SelectSpinSwitchFragment(){}

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_select_spin_switch,
                    container, false);

            flyingButton = view.findViewById(R.id.toggleButtonFlyingEntry);
            footChangeButton = view.findViewById(R.id.toggleButtonFootChange);

            flyingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(ToggleButton toggleButton, Boolean isFlying) {
                    onFlyingButtonChanged(isFlying);
                }
            });

            return view;
    }

    public void onFlyingButtonChanged(Boolean isFlying){
        if(mListener != null){
            mListener.onChangeSpinSwitchInteraction(isFlying);
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnChangeSpinSwitchInteractionListener) {
            mListener = (OnChangeSpinSwitchInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeSpinSwitchInteractionListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    public interface OnChangeSpinSwitchInteractionListener{
        public void onChangeSpinSwitchInteraction(Boolean isFlying);
    }

}
