package com.mobile.umontreal.schedule;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mobile.umontreal.schedule.misc.Callable;
import com.mobile.umontreal.schedule.misc.GoogleIntegrationManager;
import com.mobile.umontreal.schedule.misc.MenuHelper;
import com.mobile.umontreal.schedule.objects.CourseSection;
import com.mobile.umontreal.schedule.parsing.UDMJsonData;


public class FullDetailsCourseActivity extends ActionBarActivity
        implements ActionBar.OnNavigationListener, Callable {

    // Log view class
    private String LOG_TAG = FullDetailsCourseActivity.class.getSimpleName();

    // Action bar
    private android.support.v7.app.ActionBar actionBar;

    // Course title and description
    private TextView courseTitre;
    private TextView description;

    // Course dates
    private TextView dateCancellation;
    private TextView dateDrop;
    private TextView dateDropLimit;
    private TextView dateCancellationView;
    private TextView dateDropView;
    private TextView dateDropLimitView;

    // Buttons
    private Button buttonNext;
    private Button buttonBack;

    // Section
    private CourseSection courseSection;

    // Session name
    private String tagSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_details_course);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        // Action Bar settings
        actionBar = getSupportActionBar();
        Bundle extras = getIntent().getExtras();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        // Set title in ActionBar
        String tagAcronym = extras.getString(Config.JSON_SIGLE);
        String tagCourseNum = extras.getString(Config.JSON_COURSE_NUM);
        String tagSession = extras.getString(Config.JSON_SESSION);
        actionBar.setTitle(tagAcronym + " " + tagCourseNum + "-" + tagSession);

        // Build a URL for json file
        String url = Config.URL_API_UDEM + tagSession + "-" + tagAcronym.toLowerCase() + "-" + tagCourseNum + ".json";


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_full_details_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return MenuHelper.onOptionsItemSelected(getApplicationContext(), item, this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        GoogleIntegrationManager.onActivityResult(requestCode, resultCode, data, this);
    }

    @Override
    public void OnCallback(UDMJsonData data) {

    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return false;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_full_details_course, container, false);
            return rootView;
        }
    }
}
