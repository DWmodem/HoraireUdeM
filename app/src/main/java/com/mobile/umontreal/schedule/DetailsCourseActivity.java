package com.mobile.umontreal.schedule;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.umontreal.schedule.misc.Callable;
import com.mobile.umontreal.schedule.misc.MenuHelper;
import com.mobile.umontreal.schedule.objects.CourseSection;
import com.mobile.umontreal.schedule.parsing.UDMJsonData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DetailsCourseActivity extends ActionBarActivity
        implements ActionBar.OnNavigationListener, Callable {

    // Log view class
    private String LOG_TAG = DetailsCourseActivity.class.getSimpleName();

    // Action bar
    private ActionBar actionBar;
    // ArrayList for sections
    private List<CourseSection> courseSectionList;
    private TextView description;
    // Course title
    private TextView courseTitre;
    // Course dates
    private TextView dateCancellation;
    private TextView dateDrop;
    private TextView dateDropLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_course);

        courseTitre = (TextView) findViewById(R.id.course_title);
        description = (TextView) findViewById(R.id.course_description);
        dateCancellation = (TextView) findViewById(R.id.course_cancellation_value);
        dateDrop = (TextView) findViewById(R.id.course_drop_value);
        dateDropLimit = (TextView) findViewById(R.id.course_drop_limit_value);


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
        courseSectionList = new ArrayList<CourseSection>();

        try {
            for (JSONObject c : data.getItems())
                courseSectionList.add(new CourseSection(c));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        if (courseSectionList.size() == 0) {
            Toast.makeText(getApplicationContext(), R.string.DATA_IS_NOT_AVAILABLE,
                    Toast.LENGTH_LONG).show();
        } else {

            CourseSection section = courseSectionList.get(0).getCoursesSection().get(0);

            description.setText(section.getDescription());
            dateCancellation.setText(DateFormat.format(Config.DATE_FORMAT_OUT, section.getCancel()).toString());
            dateDrop.setText(DateFormat.format(Config.DATE_FORMAT_OUT, section.getDrop()).toString());
            dateDropLimit.setText(DateFormat.format(Config.DATE_FORMAT_OUT, section.getDropLimit()).toString());

        }
    }
}
