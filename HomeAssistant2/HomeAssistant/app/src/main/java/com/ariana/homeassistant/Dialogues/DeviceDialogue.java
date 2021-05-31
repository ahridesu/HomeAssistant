package com.ariana.homeassistant.Dialogues;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ariana.homeassistant.R;

public class DeviceDialogue extends AppCompatDialogFragment {
    EditText editText;
    private DeviceDialogueListner listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_basic_dialogue,null);
        builder.setView(v).setTitle("Add A Device").setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String device = editText.getText().toString();
                listener.applyDeviceText(device);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        editText = v.findViewById(R.id.editTextName);

        return builder.create();
    }

    @Override
    public void onAttach( Context context) {
        super.onAttach(context);
        listener = (DeviceDialogueListner) context;
    }

    public interface DeviceDialogueListner{
        void applyDeviceText(String name);
    }
}
