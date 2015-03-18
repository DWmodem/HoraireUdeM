package com.example.leto.horaireudem.misc;

import android.net.Uri;
import android.util.Log;

import com.example.leto.horaireudem.DepartmentsActivity;
import com.example.leto.horaireudem.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Corneliu on 13-Mar-2015.
 */
public class UDMJsonData extends ReadData {

    private String LOG_TAG = UDMJsonData.class.getSimpleName();
    private Uri destinationUri;
    public List<Object> items;

    public UDMJsonData(String uri)  {
        super(null);
        destinationUri = CreateUri(uri);
        items = new ArrayList<Object>();
    }

    public List<Object> getItems() {
        return items;
    }

    private Uri CreateUri(String url) {

        Uri.Builder builder = Uri.parse(url).buildUpon();
        return builder.build();
    }

    public void execute() {

        super.setUrl(destinationUri.toString());

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

        // TODO : Read the data for different tags

        try {

            // Read json data
            JSONArray jsonData = new JSONArray(getData());

            // Loop array
            for (int i = 0; i < jsonData.length() ; i++) {

                // Read JSON objects
                JSONObject obj = jsonData.getJSONObject(i);
                items.add(obj);
                Log.v(LOG_TAG, "Row data : " + items.get(i).toString());
            }

        } catch(Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error processing Json data...");
        }

    }

    public class DownloadJsonData extends DownloadData {

        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();
        }

        @Override
        protected String doInBackground(String... params) {

            String[] par = { destinationUri.toString() };
            return super.doInBackground(par);
        }

    }


}
