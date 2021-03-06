package com.plnyyanks.frcnotebook.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.plnyyanks.frcnotebook.Constants;
import com.plnyyanks.frcnotebook.activities.StartActivity;
import com.plnyyanks.frcnotebook.datatypes.ListElement;
import com.plnyyanks.frcnotebook.datatypes.ListItem;
import com.plnyyanks.frcnotebook.datatypes.Note;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * File created by phil on 2/22/2014.
 * Copyright 2015, Phil Lopreiato
 * This file is part of FRC Notebook
 * FRC Notebook is licensed under the MIT License
 * (http://opensource.org/licenses/MIT)
 */
public class ListViewArrayAdapter extends ArrayAdapter<ListItem> implements AdapterInterface{
    public static Context context;
    private LayoutInflater mInflater;
    public ArrayList<ListItem> values;
    public ArrayList<String> keys;

    public ListViewArrayAdapter(Context context, ListItem[] values, String[] keys){
        this(context, new ArrayList<ListItem>(Arrays.asList(values)),new ArrayList<String>(Arrays.asList(keys)));
    }

    public enum ItemType{
        LIST_ITEM,HEADER_ITEM
    }

    public ListViewArrayAdapter(Context context, ArrayList<ListItem> values, ArrayList<String> keys){
        super(context,android.R.layout.simple_list_item_1,values);
        this.context = context;
        this.values = values;
        this.keys = keys;
        mInflater = LayoutInflater.from(context);
    }

    public void removeAt(int index){
        if(index >= 0){
            values.remove(index);
            keys.remove(index);
        }
    }

    public void removeKey(String key){
        int index = keys.indexOf(key);
        if(index != -1){
            keys.remove(index);
            values.remove(index);
            Log.d(Constants.LOG_TAG,"Deleted note with id:"+key);
        }else{
            Log.w(Constants.LOG_TAG,"Tried to delete nonexistant note with id:"+key);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            /*LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            TextView mainLine = (TextView) rowView.findViewById(android.R.layout.simple_list_item_1); */
        View v = getItem(position).getView(mInflater, convertView);
        if(v.isSelected()){
            v.setBackgroundResource(android.R.color.holo_blue_light);
        }else{
            v.setBackgroundResource(android.R.color.transparent);
        }
        return v;
    }

    public String getKey(int position){
        return keys.get(position);
    }


    @Override
    public void addNote(Note n) {
        values.add(new ListElement(n.getNote(),Short.toString(n.getId())));
        keys.add(Short.toString(n.getId()));
        notifyDataSetChanged();
    }

    @Override
    public void updateNote(Note n) {
        int index = keys.indexOf(Short.toString(n.getId()));
        StartActivity.db.updateNote(n);
        if(index != -1){
            values.set(index,new ListElement(n.getNote(),Short.toString(n.getId())));
        }
    }

    public void updateListData(){
        notifyDataSetChanged();
    }

}