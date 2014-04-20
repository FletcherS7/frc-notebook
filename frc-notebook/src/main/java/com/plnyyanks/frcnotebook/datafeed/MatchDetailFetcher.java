package com.plnyyanks.frcnotebook.datafeed;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.plnyyanks.frcnotebook.activities.StartActivity;
import com.plnyyanks.frcnotebook.database.PreferenceHandler;
import com.plnyyanks.frcnotebook.datatypes.Match;
import com.plnyyanks.frcnotebook.json.JSONManager;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by phil on 2/20/14.
 */
public class MatchDetailFetcher extends AsyncTask<String,String,String>{

    private Context context;
    private String eventKey;

    public MatchDetailFetcher(Context inContext, String eventKey){
        this.context = inContext;
        this.eventKey = eventKey;
    }

    @Override
    protected String doInBackground(String... strings) {
        switch(PreferenceHandler.getDataSource()) {
            case TBAv1:
                TBADatafeed.fetchMatches_TBAv1(strings[0]);
                break;

            case TBAv2:
                TBADatafeed.fetchMatches_TBAv2(strings[0]);
                break;

            case USFIRST:
            default:
                USFIRSTParser.fetchMatches_USFIRST(strings[1].substring(0,4),strings[1].substring(4));
                break;
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //we've finished. now toast the user
        if(s.equals(""))
            Toast.makeText(context, "Info downloaded for " + this.eventKey, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


}
