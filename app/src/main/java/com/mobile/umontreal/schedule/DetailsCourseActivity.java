package com.mobile.umontreal.schedule;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.umontreal.schedule.misc.Callable;
import com.mobile.umontreal.schedule.misc.MenuHelper;
import com.mobile.umontreal.schedule.objects.CourseSection;
import com.mobile.umontreal.schedule.parsing.UDMJsonData;

import org.json.JSONException;
import org.json.JSONObject;


public class DetailsCourseActivity extends ActionBarActivity
        implements ActionBar.OnNavigationListener, Callable {

    // Log view class
    private String LOG_TAG = DetailsCourseActivity.class.getSimpleName();

    // Action bar
    private ActionBar actionBar;
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
    // Section
    private CourseSection courseSection;

    // Buttons
    private Button butNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_course);

        courseTitre = (TextView) findViewById(R.id.course_title);
        description = (TextView) findViewById(R.id.course_description);
        dateCancellation = (TextView) findViewById(R.id.course_cancellation_value);
        dateDrop = (TextView) findViewById(R.id.course_drop_value);
        dateDropLimit = (TextView) findViewById(R.id.course_drop_limit_value);
        dateCancellationView = (TextView) findViewById(R.id.course_cancellation);
        dateDropView = (TextView) findViewById(R.id.course_drop_limit);
        dateDropLimitView = (TextView) findViewById(R.id.course_drop);
        butNext = (Button) findViewById(R.id.course_button_next);


        // Action Bar settings
        actionBar = getSupportActionBar();
        Bundle extras = getIntent().getExtras();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        // Set title in ActionBar
        String tagAcronym = extras.getString(Config.TAG_SIGLE);
        String tagCourseNum = extras.getString(Config.TAG_COURSE_NUM);
        String tagSession = extras.getString(Config.TAG_SESSION);
        actionBar.setTitle(tagAcronym + " " + tagCourseNum + "-" + tagSession);

        // Set name of course
        courseTitre.setText(extras.getString(Config.TAG_COURSE_TITLE));

        // Build a URL for json file
        String url = Config.URL_API_UDEM + tagSession + "-" + tagAcronym.toLowerCase() + "-" + tagCourseNum + ".json";

        UDMJsonData data = new UDMJsonData(url);
        data.execute(this);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.action_settings:
                return true;
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                //General menu apparatus
                return MenuHelper.onOptionsItemSelected(getApplicationContext(), item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        return false;
    }

    @Override
    public void OnCallback(UDMJsonData data) {

        // Convert JSON to object. Sort the list.
        try {
            for (JSONObject c : data.getItems())
                 courseSection = new CourseSection(c);
        }
        catch (JSONException e)
        {

            e.printStackTrace();
        }

        if (courseSection == null) {

            // Hide visibility for views
            description.setVisibility(View.GONE);
            dateCancellationView.setVisibility(View.GONE);
            dateDropView.setVisibility(View.GONE);
            dateDropLimitView.setVisibility(View.GONE);
            butNext.setVisibility(View.GONE);

            Toast.makeText(getApplicationContext(), R.string.DATA_IS_NOT_AVAILABLE,
                    Toast.LENGTH_LONG).show();
        } else {

            description.setText(courseSection.getDescription());
            dateCancellation.setText(DateFormat.
                    format(Config.DATE_FORMAT_OUT, courseSection.getCancel()).toString());
            dateDrop.setText(DateFormat.
                    format(Config.DATE_FORMAT_OUT, courseSection.getDrop()).toString());
            dateDropLimit.setText(DateFormat.
                    format(Config.DATE_FORMAT_OUT, courseSection.getDropLimit()).toString());

            butNext.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "click performed...",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
