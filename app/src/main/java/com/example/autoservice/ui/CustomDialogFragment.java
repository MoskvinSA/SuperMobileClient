package com.example.autoservice.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.icu.text.CaseMap;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;

public class CustomDialogFragment extends DialogFragment {
    String error;
    String title;
    String message;

    public CustomDialogFragment(String _error, String _title, String _message) {
        error = _error;
        title =_title;
        message = _message;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message + error);
        builder.setNeutralButton("Ок", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton){
                dialog.dismiss();
            }}
        );
        return builder.create();
    }
}
