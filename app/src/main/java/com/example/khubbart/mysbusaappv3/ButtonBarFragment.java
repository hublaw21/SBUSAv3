package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ButtonBarFragment extends Fragment {

    private ButtonBarInteractionListener mListener;
    public Button mCancel;
    public Button mMaybe;
    public Button mOK;
    public String tempCode;
    public int tempCodeID;

    public ButtonBarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_button_bar,
                container, false);

        mCancel = view.findViewById(R.id.buttonBarCancel);
        mMaybe = view.findViewById(R.id.buttonBarMaybe);
        mOK = view.findViewById(R.id.buttonBarOK);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempCode="Cancel";
                onClickedButton(tempCode);
            }
        });

        mMaybe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempCode="Maybe";
                onClickedButton(tempCode);
            }
        });

        mOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempCode="OK";
                onClickedButton(tempCode);
            }
        });

        return view;
    }

    public void onClickedButton(String mTempCode){
        if(mListener != null){
            mListener.ButtonBarInteraction(mTempCode);
        }
    }

    public interface ButtonBarInteractionListener {
        public String ButtonBarInteraction(String tempCode);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof ButtonBarInteractionListener) {
            mListener = (ButtonBarInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeJumpRadioButtonInteractionListener");
        }
    }
}
