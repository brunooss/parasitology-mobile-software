package com.android.parasitologymobilesoftware;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

public class SigninDialog extends AppCompatDialogFragment {
    private  AlertDialog.Builder builder;
    private View dialogView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_signin, null);
        builder.setView(dialogView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });


        return builder.create();
    }
}
