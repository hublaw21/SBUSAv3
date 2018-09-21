package com.example.khubbart.mysbusaappv3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ElementButtonChangeDialogCaller extends BaseDialogFragment {

    public static ElementButtonChangeDialogCaller newInstance(int eleNum) {
        //Create an instance of the Dialog with input
        ElementButtonChangeDialogCaller frag = new ElementButtonChangeDialogCaller();
        Bundle args = new Bundle();
        args.putInt("elementNumber", eleNum);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.alertdialog_change_element, null);
    }

    @Override
    public void onViewCreated(View dialogView, @Nullable Bundle savedInstanceState){
        super.onViewCreated(dialogView, savedInstanceState);
        /*
        // Specify alert dialog is not cancelable/not ignorable
        dialogView.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(dialogView);
        */
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the custom alert dialog view widgets reference
        Button submitButton = (Button) dialogView.findViewById(R.id.dialog_change_element_submit_button);
        Button lookupButton = (Button) dialogView.findViewById(R.id.dialog_change_element_lookup_button);
        Button cancelButton = (Button) dialogView.findViewById(R.id.dialog_change_element_cancel_button);
        final EditText editTextElementCode = (EditText) dialogView.findViewById(R.id.edittext_change_element_code);

        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        // Set submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                String tElementCode = editTextElementCode.getText().toString();
                //int ttNum = tNum; // needs to track element number from list
                //pullElementInfo(tElementCode, tNum); - 9/10/18 - I will need this
            }
        });

        // Set lookup button click listener
        lookupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss/cancel the alert dialog
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Display the custom alert dialog on interface
        dialog.show();
        //return editTextElementCode;
    }
}
