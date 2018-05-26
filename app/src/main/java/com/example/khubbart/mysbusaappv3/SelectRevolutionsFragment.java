package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


public class SelectRevolutionsFragment extends Fragment {

    private OnChangeRevolutionsRadioButtonInteractionListener mListener;
    public RadioGroup mRGRevs;

    public SelectRevolutionsFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_select_revolutions,
                    container, false);

            mRGRevs = view.findViewById(R.id.radioGroupRevs);

            mRGRevs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    onRadioButtonChanged(checkedId);
                }
            });

            return view;
    }

    public void onRadioButtonChanged(int id){
        if(mListener != null){
            mListener.onChangeRevolutionsRadioButtonInteraction(id);
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnChangeRevolutionsRadioButtonInteractionListener) {
            mListener = (OnChangeRevolutionsRadioButtonInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnChangeRevolutionsRadioButtonInteractionListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    public interface OnChangeRevolutionsRadioButtonInteractionListener{
        public void onChangeRevolutionsRadioButtonInteraction(int id);
    }

}
