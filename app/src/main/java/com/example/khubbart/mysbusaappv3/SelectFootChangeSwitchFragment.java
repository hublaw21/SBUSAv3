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


public class SelectFootChangeSwitchFragment extends Fragment {

    private OnChangeFootChangeSwitchInteractionListener mListener;
    public ToggleButton footChangeButton;
    public SelectFootChangeSwitchFragment(){}

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_select_flying_switch,
                    container, false);

            footChangeButton = view.findViewById(R.id.toggleButtonFootChange);

            footChangeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isFootChange) {
                    onFootChangeButtonChanged(isFootChange);
                }
            });

            return view;
    }

    public void onFootChangeButtonChanged(boolean isFootChange){
        if(mListener != null){
            mListener.onChangeFootChangeSwitchInteraction(isFootChange);
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnChangeFootChangeSwitchInteractionListener) {
            mListener = (OnChangeFootChangeSwitchInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeFootChangeSwitchInteractionListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    public interface OnChangeFootChangeSwitchInteractionListener{
        public void onChangeFootChangeSwitchInteraction(boolean isFootChange);
    }

}
