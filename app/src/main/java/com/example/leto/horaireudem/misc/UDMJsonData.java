package com.example.leto.horaireudem.misc;

import android.net.Uri;
import android.util.Log;

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
    private String[] tags;
    private List<String> items;

    public List<String> getItems() {
        return items;
    }

    public UDMJsonData(String uri, String[] tags)  {
        super(null);
        destinationUri = CreateUri(uri);
        this.tags = tags;
        items = new ArrayList<String>();
    }

    private Uri CreateUri(String url) {

        Uri.Builder builder = Uri.parse(url).buildUpon();
        return builder.build();
    }

    public void execute() {

        super.setUrl(destinationUri.toString());

        DownloadJsonData downloadData = new DownloadJsonData();
        downloadData.execute(destinationUri.toString());
        Log.v(LOG_TAG, "Built URI : " + destinationUri.toString());

    }

    /**
     * Read JSON data
     * */

     public void processResult(){

        if (getDownloadStatus() != DownloadStatus.OK) {
            Log.e(LOG_TAG, "Error downloading raw file...");
            return;
        }

        // TODO : Read the data for different tags

        try {

            JSONArray jsonData = new JSONArray(getData());

            for (int i = 0; i < jsonData.length() ; i++) {

                JSONObject obj = jsonData.getJSONObject(i);
                // Read JSON tags
                items.add(obj.getString(tags[0]));
                Log.v(LOG_TAG, "Data row: " + obj.getString(tags[0]));
            }


        } catch (JSONException jsone) {
            jsone.printStackTrace();
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
        }
    }

}
