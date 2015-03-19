package com.example.leto.horaireudem;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


/**
 * Main Activity for tests ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * */
public class MainActivity extends ActionBarActivity{

    private String LOG_TAG = MainActivity.class.getSimpleName();

    public static final String URL_API_UDEM = "http://www-labs.iro.umontreal.ca/~roys/horaires/json/";

    public static final String TAG_TRIMESTER  = "trimestre";
    public static final String TAG_SIGLE      = "sigle";
    public static final String TAG_COURSE_NUM = "coursnum";
    public static final String TAG_SECTION    = "section";

    public static final String TAG_TITLE      = "titre";

    // Data Base
    UDMDatabaseManager dbh;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connection DataBase
        dbh = new UDMDatabaseManager(this);
        db = dbh.getWritableDatabase();


        // Launching new Activity
        // if data base is empty go to Departments
        Intent intent = new Intent(getApplicationContext(), DepartmentsActivity.class);

        // Sending data to new activity
        intent.putExtra(TAG_TRIMESTER, "H15");
        startActivity(intent);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
