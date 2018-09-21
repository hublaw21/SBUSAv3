package com.example.khubbart.mysbusaappv3;

import android.app.Activity;
import android.support.v4.app.DialogFragment;


public abstract class BaseDialogFragment<T> extends DialogFragment {
    private T activityInstance;

    public final T getActivityInstance(){
        return activityInstance;
    }

    @Override
    public void onAttach(Activity activity){
        activityInstance = (T) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach(){
        super.onDetach();
        activityInstance = null;
    }
}
