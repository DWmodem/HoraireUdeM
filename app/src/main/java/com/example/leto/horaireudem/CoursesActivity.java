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
import android.widget.Toast;

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
    // Navigation Spinner [ Winter Summer Autumn ]
    ArrayList<Session> navSpinner;
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

        // Adding Drop-down Navigation
        addBarNavigation();

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
            }
        });
    }

    private void fillViewList(String url) {

        UDMJsonData data = new UDMJsonData(url);
        data.execute(this);

    }

    // Adding Drop-down Navigation
    private void addBarNavigation() {

        // Session navigation
        navSpinner = new ArrayList<Session>();
        navSpinner.add(createSession(-1));
        navSpinner.add(createSession(0));
        navSpinner.add(createSession(1));

        // Session adapter
        navAdapter = new SessionNavigationAdapter(getApplicationContext(), navSpinner);
        // Assigning the spinner navigation
        actionBar.setListNavigationCallbacks(navAdapter, this);
        // Assigning default value
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

    private Session createSession (int type) {

        Time time = new Time();
        time.setToNow();

        int year = time.year;
        Session session = new Session();

        switch (time.month) {

            case 1: case 2: case 3: case 4:

                if (type < 0) {
                    session.setSeason(SessionSeason.Autumn);
                    year = year - 1;
                }
                else if (type == 0) {
                    session.setSeason(SessionSeason.Winter);
                } else {
                    session.setSeason(SessionSeason.Summer);
                }
                break;

            case 5: case 6: case 7: case 8:
                if (type < 0) {
                    session.setSeason(SessionSeason.Winter);
                }
                else if (type == 0) {
                    session.setSeason(SessionSeason.Summer);
                } else {
                    session.setSeason(SessionSeason.Autumn);
                }
                break;

            case 9: case 10: case 11: case 12:
                if (type < 0) {
                    session.setSeason(SessionSeason.Summer);
                }
                else if (type == 0) {
                    session.setSeason(SessionSeason.Autumn);
                } else {
                    session.setSeason(SessionSeason.Winter);
                    year = year + 1;
                }
                break;
        }

        session.setYear(year);
        return session;
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {

        Session selectedItem = navSpinner.get(itemPosition);

        Bundle extras = getIntent().getExtras();
        String tagTitle = extras.getString(Config.TAG_TITLE);
        String tagSigle = extras.getString(Config.TAG_SIGLE);

        // Set Department title
        DepartmentTitle.setText(tagTitle);

        Toast.makeText(getApplicationContext(), selectedItem.toString(), Toast.LENGTH_SHORT).show();

        // Read JSON Data
        String url = Config.URL_API_UDEM + selectedItem.toString() + "-"
                + tagSigle.toLowerCase().trim() + ".json";
        fillViewList(url);

        return true;
    }
}
