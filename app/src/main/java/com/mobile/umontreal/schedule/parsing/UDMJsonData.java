package com.mobile.umontreal.schedule.parsing;

import android.net.Uri;
import android.util.Log;

import com.mobile.umontreal.schedule.misc.Callable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corneliu on 13-Mar-2015.
 */
public class UDMJsonData extends ReadData {

    private String LOG_TAG = UDMJsonData.class.getSimpleName();
    private Uri destinationUri;
    private Callable callback;
    public List<JSONObject> items;

    protected UDMJsonData getThis(){
        return  this;
    }

    public UDMJsonData(String uri)  {
        super(null);
        destinationUri = CreateUri(uri);
        items = new ArrayList<JSONObject>();
    }

    public List<JSONObject> getItems() {
        return items;
    }

    private Uri CreateUri(String url) {

        Uri.Builder builder = Uri.parse(url).buildUpon();
        return builder.build();
    }

    public void execute(Callable c) {

        super.setUrl(destinationUri.toString());
        callback = c;
        DownloadJsonData downloadData = new DownloadJsonData();
        downloadData.execute(destinationUri.toString());
//        Log.v(LOG_TAG, "Built URI : " + destinationUri.toString());

    }

    /**
     * Read JSON data
     * */

     public void processResult(){

        if (getDownloadStatus() != DownloadStatus.OK) {
            Log.e(LOG_TAG, "Error downloading raw file...");
            return;
        }

        try {

            // Read json data
            JSONArray jsonData = new JSONArray(getData());

            // Loop array
            for (int i = 0; i < jsonData.length(); i++) {

                // Read JSON objects
                JSONObject obj = jsonData.getJSONObject(i);
                items.add(obj);
                Log.v(LOG_TAG, "row data : " + items.get(i).toString());
            }

        } catch (JSONException jsonexception) {
            jsonexception.printStackTrace();
            Log.e(LOG_TAG, "Error processing Json data...");
        }

    }

    public class DownloadJsonData extends DownloadData {

        @Override
        protected String doInBackground(String... params) {
            return super.doInBackground(params);
        }

        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();
            callback.OnCallback(getThis());
        }
    }


}
