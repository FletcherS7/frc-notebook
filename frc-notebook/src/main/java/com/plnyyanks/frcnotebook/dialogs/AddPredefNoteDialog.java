package com.plnyyanks.frcnotebook.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

import com.plnyyanks.frcnotebook.activities.StartActivity;
import com.plnyyanks.frcnotebook.adapters.ListViewArrayAdapter;
import com.plnyyanks.frcnotebook.datatypes.ListElement;

/**
 * File created by phil on 3/15/14.
 * Copyright 2015, Phil Lopreiato
 * This file is part of FRC Notebook
 * FRC Notebook is licensed under the MIT License
 * (http://opensource.org/licenses/MIT)
 */
public class AddPredefNoteDialog extends DialogFragment {

    static ListViewArrayAdapter adapter;

    public AddPredefNoteDialog(){
        super();
    }

    public AddPredefNoteDialog(ListViewArrayAdapter a){
        this();
        adapter = a;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText editField = new EditText(getActivity());
        editField.requestFocus();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Predefined Note");
        builder.setView(editField);
        builder.setPositiveButton("Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String text = editField.getText().toString();
                        if(!text.equals("")){
                            short newId = StartActivity.db.addDefNote(text);
                            if(newId != -1){
                                adapter.values.add(new ListElement(text,Short.toString(newId)));
                                adapter.keys.add(Short.toString(newId));

                                if(adapter.keys.get(0).equals("-1")){
                                    adapter.values.remove(0);
                                    adapter.keys.remove(0);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                        dialog.cancel();
                    }
                }
        );

        builder.setNeutralButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }
        );
        Dialog out = builder.create();
        out.getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        out.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return out;
    }
}
