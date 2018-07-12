package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import static com.facebook.FacebookSdk.getApplicationContext;


public class SelectDisciplineFragment extends Fragment {

    private OnChangeDisciplineRadioButtonInteractionListener mListener;
    public RadioGroup mRGDiscipline;
    //public String tempCode;
    public int tempIndex;

    public SelectDisciplineFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_discipline,
                container, false);

        mRGDiscipline = view.findViewById(R.id.radioGroupDisciplines);

        mRGDiscipline.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = mRGDiscipline.getCheckedRadioButtonId();
                onRadioButtonChanged(selectedId);  //I only need this for manipulations before returning info
            }
        });
        return view;
    }

    public void onRadioButtonChanged(int mSelectedID) {
        View selectedButton = mRGDiscipline.findViewById(mSelectedID);
        int buttonIndex = mRGDiscipline.indexOfChild(selectedButton);
        tempIndex = buttonIndex;

        if (mListener != null) {
            mListener.onChangeDisciplineRadioButtonInteraction(tempIndex);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChangeDisciplineRadioButtonInteractionListener) {
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
        public void onChangeDisciplineRadioButtonInteraction(int tempIndex);
    }

}
