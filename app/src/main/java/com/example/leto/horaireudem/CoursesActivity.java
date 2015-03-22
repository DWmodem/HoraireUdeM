package com.example.leto.horaireudem;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leto.horaireudem.misc.SessionNavigationAdapter;
import com.example.leto.horaireudem.misc.UDMJsonData;
import com.example.leto.horaireudem.objects.Course;
import com.example.leto.horaireudem.objects.Session;
import com.example.leto.horaireudem.objects.SessionSeason;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class CoursesActivity extends ActionBarActivity
        implements ActionBar.OnNavigationListener, Callable {

    // Log view class
    private String LOG_TAG = CoursesActivity.class.getSimpleName();

    // Action bar
    private ActionBar actionBar;
    // List view Adapter
    private ArrayAdapter<Course> courseAdapter;
    // Navigation adapter
    private SessionNavigationAdapter navAdapter;
    // ArrayList for departments
    private List<Course> courseList;

    // List view
    private ListView listView;
    // Search EditText
    private EditText inputSearch;
    // Department Title
    private TextView DepartmentTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        // Action Bar settings
        actionBar = getSupportActionBar();
        // Hide the action bar title
        actionBar.setDisplayShowTitleEnabled(false);
        // Enabling Spinner dropdown navigation
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        listView = (ListView) findViewById(R.id.list_view_courses);
        inputSearch = (EditText) findViewById(R.id.input_search);
        DepartmentTitle = (TextView) findViewById(R.id.title_department);

        Bundle extras = getIntent().getExtras();
        String tagTitle = extras.getString(MainActivity.TAG_TITLE);
        String tagSigle = extras.getString(MainActivity.TAG_SIGLE);

        // Set Department title
        DepartmentTitle.setText(tagTitle);

        // Adding Drop-down Navigation
        addBarNavigation();

        String url = MainActivity.URL_API_UDEM + "A14-" + tagSigle.toLowerCase().trim() + ".json";
        fillViewList(url);

        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int lengthBefore, int lengthAfter) {
                // When user changed the Text
                if (CoursesActivity.this.courseAdapter != null) {
                    CoursesActivity.this.courseAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int lengthBefore, int lengthAfter) {
                // TODO beforeTextChanged method
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // TODO afterTextChanged method
//                if (inputSearch.getText().toString().trim().length() > 0) {
//                    if (Integer.parseInt(inputSearch.getText().toString().trim()) < 20 ||
//                            Integer.parseInt(inputSearch.getText().toString().trim()) > 120) {
//                        inputSearch.setTextColor(Color.GREEN);
//                    } else {
//                        inputSearch.setTextColor(Color.RED);
//                    }
//                }

            }

        });
    }

    private void fillViewList(String url) {

        UDMJsonData data = new UDMJsonData(url);
        data.execute(this);

    }

    // Adding Drop-down Navigation
    private void addBarNavigation() {

        // title drop down adapter
        navAdapter = new SessionNavigationAdapter(getApplicationContext(), addSessions());
        // Assigning the spinner navigation
        actionBar.setListNavigationCallbacks(navAdapter, this);
        actionBar.setSelectedNavigationItem(1);

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
    public void OnCallback(UDMJsonData data) {

        // Convert JSON to object. Sort the list.
        courseList = new ArrayList<Course>();
        try {
            for (JSONObject c : data.getItems())
                courseList.add(new Course(c));
        }
        catch (JSONException e)
        {
            throw new RuntimeException(e);
        }

        Collections.sort(courseList, new Comparator<Course>() {

            @Override
            public int compare(Course first, Course second) {
                return first.getTitle().compareTo(second.getTitle());
            }
        });

        Log.v(LOG_TAG, String.format("%s courses loaded.", courseList.size()));
        courseAdapter = new ArrayAdapter<Course>(this, R.layout.item_course, courseList);
        listView.setAdapter(courseAdapter);
    }

    private ArrayList<Session> addSessions() {

        // Navigation Spinner [ Winter Summer Autumn ]
        ArrayList<Session> navSpinner;

        Time time = new Time();
        time.setToNow();

        int currentPeriod = time.year;
        int lastPeriod = 0;
        int nextPeriod = 0;

        Session lastSession = new Session();
        Session currentSession= new Session();
        Session nextSession = new Session();

        switch (time.month) {

            case 1: case 2: case 3: case 4:
                lastSession.setSeason(SessionSeason.Autumn);
                currentSession.setSeason(SessionSeason.Winter);
                nextSession.setSeason(SessionSeason.Summer);
                lastPeriod = currentPeriod - 1;
                nextPeriod = currentPeriod;
                break;
            case 5: case 6: case 7: case 8:
                lastSession.setSeason(SessionSeason.Winter);
                currentSession.setSeason(SessionSeason.Summer);
                nextSession.setSeason(SessionSeason.Autumn);
                lastPeriod = currentPeriod;
                nextPeriod = currentPeriod;
                break;
            case 9: case 10: case 11: case 12:
                lastSession.setSeason(SessionSeason.Summer);
                currentSession.setSeason(SessionSeason.Autumn);
                nextSession.setSeason(SessionSeason.Winter);
                lastPeriod = currentPeriod;
                nextPeriod = currentPeriod + 1;
                break;
        }

        // Spinner navigation settings
        navSpinner = new ArrayList<Session>();

        lastSession.setYear(lastPeriod);
        currentSession.setYear(currentPeriod);
        nextSession.setYear(nextPeriod);

        navSpinner.add(lastSession);
        navSpinner.add(currentSession);
        navSpinner.add(nextSession);

        return navSpinner;

    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {

        return false;
    }
}
