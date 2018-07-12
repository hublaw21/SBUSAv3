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

    private View.OnClickListener mListener1;
    private View.OnClickListener mListener2;
    private View.OnClickListener mListener3;
    public Button mCancel;
    public Button mMaybe;
    public Button mOK;
    public String tempCode;

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
            }
        });

        mMaybe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempCode="Maybe";
            }
        });

        mOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempCode="OK";
                Toast.makeText(getApplicationContext(), "Button Bar Retrun Top: " + tempCode, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    public void onClick(final View view){ // Check which button is pushed
        switch (view.getId()){
            case R.id.buttonBarCancel:
                tempCode="Cancel";
                break;
            case R.id.buttonBarMaybe:
                tempCode="Maybe";
                Toast.makeText(getApplicationContext(), "Button Bar Retrun: " + tempCode, Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonBarOK:
                tempCode="OK";
                break;
            default:
                Toast.makeText(getApplicationContext(), "Button Bar Default: " + tempCode, Toast.LENGTH_LONG).show();
                tempCode=null;
        }
    }

    public interface ButtonBarInteractionListener {
        public String ButtonBarInteraction(String tempCode);
    }

    /*
    public void onRadioButtonChanged(int id) {
        //adjust this for selecting or returning skater level
        tempCode = "test2";

        if (mListener != null) {
            mListener.onChangeDisciplineRadioButtonInteraction(tempCode);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChangeDisciplineRadioButtonInteractionListener) {
            //mListener = (SelectDisciplineFragment.OnChangeDisciplineRadioButtonInteractionListener) context;
            mListener = (OnChangeDisciplineRadioButtonInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeLevelRadioButtonInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnChangeDisciplineRadioButtonInteractionListener {
        public void onChangeDisciplineRadioButtonInteraction(String tempCode);
    }
    */

}
