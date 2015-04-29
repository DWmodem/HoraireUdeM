package com.mobile.umontreal.schedule.parsing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.util.Log;

import com.mobile.umontreal.schedule.R;
import com.mobile.umontreal.schedule.misc.Callable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corneliu on 13-Mar-2015.
 */
//Extend from read data to read whole json files
public class UDMJsonData extends ReadData {

    private String LOG_TAG = UDMJsonData.class.getSimpleName();
    private Uri destinationUri;
    private Callable callback;
    private Activity activity;

    public List<JSONObject> items;

    protected UDMJsonData getThis(){
        return  this;
    }

    public UDMJsonData(String uri, Activity activity)  {
        super(null);
        this.activity = activity;
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
        this.callback = c;
        DownloadJsonData downloadData = new DownloadJsonData();
        downloadData.execute(destinationUri.toString());

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

            JSONArray jsonData = null;

            try {   //If the data is presented as array form
                jsonData = new JSONArray(getData());
                // Loop array
                //To read every line
                for (int i = 0; i < jsonData.length(); i++) {

                    // Read JSON objects
                    JSONObject jsonObject = jsonData.getJSONObject(i);
                    items.add(jsonObject);

                    Log.v(LOG_TAG, "row data : " + items.get(i).toString());
                }
            }
            //If the data is presented as object form
            catch (JSONException e) {
                JSONObject jsonObject = new JSONObject(getData());
                items.add(jsonObject);
            }


        } catch (JSONException jsonexception) {
            jsonexception.printStackTrace();
            Log.e(LOG_TAG, "Error processing Json data...");
        }

    }

    //Execute
    public class DownloadJsonData extends DownloadData {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(activity);
            
            dialog.setTitle(activity.getResources().getString(R.string.progress_dialog_title));
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMessage(activity.getResources().getString(R.string.progress_dialog_message));

            if (!dialog.isShowing()) {
                dialog.show();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            dialog.setProgress(Integer.valueOf(String.valueOf(values[0])));
        }

        @Override
        protected String doInBackground(String... params) {
            initiateLoading();
            return super.doInBackground(params);
        }

        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();
            callback.OnCallback(getThis());
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        private void initiateLoading() {

            try {
                Thread.sleep(500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
