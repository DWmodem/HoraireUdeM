package com.mobile.umontreal.schedule;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;


public class DetailsCourseActivity extends ActionBarActivity
        implements ActionBar.OnNavigationListener {

    // Log view class
    private String LOG_TAG = DetailsCourseActivity.class.getSimpleName();

    // Action bar
    private ActionBar actionBar;

    // Sections JSONArray
    private JSONArray sections = null;
    // Hashmap for ListView
    private ArrayList<HashMap<String, String>> sectionList;
    // Course title
    private TextView courseTitre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_course);

        courseTitre = (TextView) findViewById(R.id.course_title);

        // Action Bar settings
        actionBar = getSupportActionBar();
        Bundle extras = getIntent().getExtras();

        // Set titre in ActionBar
        String tagAcronym = extras.getString(Config.TAG_SIGLE);
        String tagCourseNum = extras.getString(Config.TAG_COURSE_NUM);
        String tagSession = extras.getString(Config.TAG_SESSION);

        actionBar.setTitle(tagAcronym + " " + tagCourseNum + "-" + tagSession);

        // Hide the action bar title
        actionBar.setDisplayShowTitleEnabled(true);

        // Build a URL for json file
        String url = Config.URL_API_UDEM + tagSession.toLowerCase()
                + "-" + tagAcronym + "-" + tagCourseNum + ".json";

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        return false;
    }
}
