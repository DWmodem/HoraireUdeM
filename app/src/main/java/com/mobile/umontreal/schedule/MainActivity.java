package com.mobile.umontreal.schedule;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.mobile.umontreal.schedule.db.UDMDatabaseManager;
import com.mobile.umontreal.schedule.googleI.GoogleIntegrationManager;
import com.mobile.umontreal.schedule.misc.MenuHelper;


/**
 * Main Activity
 * */
public class MainActivity extends ListActivity {

    private String LOG_TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toast.makeText(this, "App started", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG,"App started" );

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

        return MenuHelper.onOptionsItemSelected(getApplicationContext(), item, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        GoogleIntegrationManager.onActivityResult(requestCode, resultCode, data, this);
    }

    //Starts the getUsername asynctask
    protected void getUsername(){
        GoogleIntegrationManager.getUsername(this);
    }


}