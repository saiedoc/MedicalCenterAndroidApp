package com.example.clinic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class LogoutDialog extends DialogFragment {


    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to logout?")
        .setPositiveButton("Logout", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                System.out.println("Logout");
                ((MainActivity)getActivity()).LogOut();
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {}
        });
        return builder.create();
    }
}
