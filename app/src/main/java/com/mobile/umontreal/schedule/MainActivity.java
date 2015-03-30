package com.mobile.umontreal.schedule;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mobile.umontreal.schedule.db.UDMDatabaseManager;
import com.mobile.umontreal.schedule.misc.MenuHelper;
import com.mobile.umontreal.schedule.parsing.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Main Activity
 * */
public class MainActivity extends ListActivity {

    private String LOG_TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;

    // departments JSONArray
    JSONArray departments = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> departmentsList;


    // Data Base
    UDMDatabaseManager dbh;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheme(R.style.AppTheme);

        // Connection DataBase
        dbh = new UDMDatabaseManager(this);
        db = dbh.getWritableDatabase();

        departmentsList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();



        // Calling async task to get json
        new GetContacts().execute();
        Toast.makeText(this, "App started", Toast.LENGTH_SHORT).show();
        Log.d("MENUITEM","App started" );


    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Build a URL for json file
            String url = Config.URL_API_UDEM + "A14-ift-1015.json";

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    departments = jsonObj.getJSONArray(Config.TAG_SECTIONS);

                    // looping through All Contacts
                    for (int i = 0; i < departments.length(); i++) {
                        JSONObject c = departments.getJSONObject(i);

                        String id = c.getString(Config.TAG_SECTION);
                        String name = c.getString(Config.TAG_TYPE);
                        String email = c.getString(Config.TAG_DESCRIPTION);
//                        try {
//                            email = new String(c.getString(Config.TAG_DESCRIPTION).getBytes("ISO-8859-1"), "UTF-8");
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        contact.put(Config.TAG_SECTION, id);
                        contact.put(Config.TAG_TYPE, name);
                        contact.put(Config.TAG_DESCRIPTION, email);


                        // adding contact to contact list
                        departmentsList.add(contact);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, departmentsList,
                    R.layout.item_course_section,
                    new String[]{
                            Config.TAG_SECTION,
                            Config.TAG_TYPE,
                            Config.TAG_DESCRIPTION},
                    new int[]{
                            R.id.name,
                            R.id.email,
                            R.id.mobile}
            );

            setListAdapter(adapter);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Common create options menu code is in MenuHelper
        MenuInflater inflater = getMenuInflater();
        MenuHelper.onCreateOptionsMenu(menu, inflater);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {

        //Common prepare options menu code is in MenuHelper
        MenuHelper.onPrepareOptionsMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

    return MenuHelper.onOptionsItemSelected(getApplicationContext(), item);
    }


}
