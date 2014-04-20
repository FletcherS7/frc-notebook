package com.plnyyanks.frcnotebook.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * File created by phil on 2/18/14.
 * Copyright 2014, Phil Lopreiato
 * This file is part of FRC Notebook.
 * FRC Notebook is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * FRC Notebook is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with FRC Notebook. If not, see http://www.gnu.org/licenses/.
 */
public class JSONManager {
    private static Gson gson;
    private static JsonParser parser;

    public static JsonArray getasJsonArray(String input){
       if(parser == null)
           parser = new JsonParser();
        if(input == null || input.equals(""))
            return new JsonArray();
        return parser.parse(input).getAsJsonArray();
    }

    public static JsonObject getAsJsonObject(String input){
        if(parser == null)
            parser = new JsonParser();
        if(input==null || input.equals("")) return new JsonObject();
        return parser.parse(input).getAsJsonObject();
    }

    public static ArrayList<String> getAsStringArrayList(String input){
        if(parser == null)
            parser = new JsonParser();
        Iterator<JsonElement> iterator = parser.parse(input).getAsJsonArray().iterator();
        ArrayList<String> output = new ArrayList<String>();
        JsonElement element;
        while(iterator.hasNext()){
            element = iterator.next();
            output.add(element.getAsString());
        }
        return output;
    }

    public static ArrayList<JsonObject> getAsObjectArrayList(String input){
        if(parser == null)
            parser = new JsonParser();
        Iterator<JsonElement> iterator = parser.parse(input).getAsJsonArray().iterator();
        ArrayList<JsonObject> output = new ArrayList<JsonObject>();
        JsonElement element;
        while(iterator.hasNext()){
            element = iterator.next();
            output.add(element.getAsJsonObject());
        }
        return output;
    }
    public static String flattenToJsonArray(ArrayList<String> input){
        if(input == null ||input.size()==0)
            return "[]";
        JsonArray array = new JsonArray();
        for(String i:input){
            array.add(new JsonPrimitive(i));
        }
        return array.toString();
    }
}
