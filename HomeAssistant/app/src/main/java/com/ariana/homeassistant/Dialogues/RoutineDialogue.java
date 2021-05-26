package com.ariana.homeassistant.Dialogues;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ariana.homeassistant.R;

public class RoutineDialogue extends AppCompatDialogFragment {

    EditText editText;
    private RoutineDialogueListner listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_basic_dialogue,null);
        builder.setView(v).setTitle("Add A Routine").setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String routine = editText.getText().toString();
                listener.applyRoutineText(routine);
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
        listener = (RoutineDialogueListner) context;
    }
    
    public interface RoutineDialogueListner{
        void applyRoutineText(String name);
    }
}
