package com.example.leto.horaireudem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.leto.horaireudem.misc.SessionNavigationAdapter;
import com.example.leto.horaireudem.misc.UDMJsonData;
import com.example.leto.horaireudem.objects.Department;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class DepartmentsActivity extends ActionBarActivity implements ActionBar.OnNavigationListener, Callable {

    // Log view class
    private String LOG_TAG = DepartmentsActivity.class.getSimpleName();

    // Action bar
    private ActionBar actionBar;
    // List view Adapter
    private ArrayAdapter<Department> departmentAdapter;
    // Navigation adapter
    private SessionNavigationAdapter navAdapter;
    // ArrayList for departments
    private List<Department> departmentList;

    // List view
    private ListView listView;
    // Search EditText
    private EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);

        listView = (ListView) findViewById(R.id.list_departments);
        inputSearch = (EditText) findViewById(R.id.input_search);

        // Action Bar settings
        actionBar = getSupportActionBar();
        // Hide the action bar title
        actionBar.setDisplayShowTitleEnabled(true);

        // Adding Drop-down Navigation
        addBarNavigation();

        // Build a URL for json file
        String url = Config.URL_API_UDEM + "sigles.json";

        // Fill the View List
        fillViewList(url);

        /**
         * Listening to single list item on click
         * */

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // Selected item
                Department dep = (Department) parent.getItemAtPosition(position);

                // Launching new Activity on selecting single List Item
                Intent intent = new Intent(getApplicationContext(), CoursesActivity.class);

                // Sending data to CourseActivity
                intent.putExtra(Config.TAG_SIGLE, dep.getSigle());
                intent.putExtra(Config.TAG_TITLE, dep.getTitle());
                startActivity(intent);
            }



        });

        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int lengthBefore, int lengthAfter) {
                // When user changed the Text
                if (DepartmentsActivity.this.departmentAdapter != null) {
                    DepartmentsActivity.this.departmentAdapter.getFilter().filter(s);
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

    /**
     * Fill all lists: Departments, Courses, Sessions
     * */

    private void fillViewList(String url) {
        UDMJsonData data = new UDMJsonData(url);
        data.execute(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                ActionBarHelper.openSettings(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Adding Drop-down Navigation
    private void addBarNavigation() {

    }


    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        return false;
    }

    @Override
    public void OnCallback(UDMJsonData data) {
        // Convert JSON to object. Sort the list.
        departmentList = new ArrayList<Department>();
        try {
            for (JSONObject d : data.getItems())
                departmentList.add(new Department(d));
        }
        catch (JSONException e)
        {
            throw new RuntimeException(e);
        }

        Collections.sort(departmentList, new Comparator<Department>() {
            @Override
            public int compare(Department lhs, Department rhs) {
                return lhs.getSigle().compareTo(rhs.getSigle());
            }
        });

        Log.v(LOG_TAG, String.format("%s departments loaded.", departmentList.size()));
        departmentAdapter = new ArrayAdapter<Department>(this, R.layout.item_department, departmentList);
        listView.setAdapter(departmentAdapter);
    }
}
