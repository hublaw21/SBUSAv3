package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;


public class SelectFlyingSwitchFragment extends Fragment {

    private OnChangeFlyingSwitchInteractionListener mListener;
    public ToggleButton flyingButton;

    private SelectFootChangeSwitchFragment.OnChangeFootChangeSwitchInteractionListener m2Listener;
    public ToggleButton footChangeButton;

    public SelectFlyingSwitchFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_flying_switch,
                container, false);

        flyingButton = view.findViewById(R.id.toggleButtonFlyingEntry);
        flyingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isFlying) {
                onFlyingButtonChanged(isFlying);
            }
        });

        footChangeButton = view.findViewById(R.id.toggleButtonFootChange);
        footChangeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView2, boolean isFootChange) {
                onFootChangeButtonChanged(isFootChange);
            }
        });

        return view;
    }

    public void onFlyingButtonChanged(boolean isFlying) {
        if (mListener != null) {
            mListener.onChangeFlyingSwitchInteraction(isFlying);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChangeFlyingSwitchInteractionListener) {
            mListener = (OnChangeFlyingSwitchInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeFlyingSwitchInteractionListener");
        }
        if (context instanceof SelectFootChangeSwitchFragment.OnChangeFootChangeSwitchInteractionListener) {
            m2Listener = (SelectFootChangeSwitchFragment.OnChangeFootChangeSwitchInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeFootChangeSwitchInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        m2Listener = null;
    }

    public interface OnChangeFlyingSwitchInteractionListener {
        public void onChangeFlyingSwitchInteraction(boolean isFlying);
    }

    public void onFootChangeButtonChanged(boolean isFootChange) {
        if (m2Listener != null) {
            m2Listener.onChangeFootChangeSwitchInteraction(isFootChange);
        }
    }

    public interface OnChangeFootChangeSwitchInteractionListener {
        public void onChangeFootChangeSwitchInteraction(boolean isFootChange);
    }


}
