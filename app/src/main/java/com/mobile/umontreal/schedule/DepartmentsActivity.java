package com.mobile.umontreal.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mobile.umontreal.schedule.adapters.SessionNavigationAdapter;
import com.mobile.umontreal.schedule.googleI.GoogleIntegrationManager;
import com.mobile.umontreal.schedule.misc.Callable;
import com.mobile.umontreal.schedule.misc.ConnectionDetector;
import com.mobile.umontreal.schedule.misc.MenuHelper;
import com.mobile.umontreal.schedule.objects.Department;
import com.mobile.umontreal.schedule.parsing.UDMJsonData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//Departments activity is the start launch activity
public class DepartmentsActivity extends ActionBarActivity
        implements ActionBar.OnNavigationListener, Callable {

    // Log view class
    private String LOG_TAG = DepartmentsActivity.class.getSimpleName();

    // Connection Internet Detector
    private ConnectionDetector connection;

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

        // Internet Connection is not present
        connection = new ConnectionDetector(getApplicationContext());

        listView = (ListView) findViewById(R.id.list_departments);
        inputSearch = (EditText) findViewById(R.id.input_search);

        // Action Bar settings
        actionBar = getSupportActionBar();
        // Show the action bar title
        actionBar.setDisplayShowTitleEnabled(true);

        if (!connection.isConnectingToInternet()) {

            Toast.makeText(getApplicationContext(),
                    R.string.INTERNET_CONNECTION_ERROR,
                    Toast.LENGTH_LONG).show();

            // stop executing code by return
            return;
        } else {
            Toast.makeText(getApplicationContext(),
                    "connection to interent : " + connection.isConnectingToInternet(),
                    Toast.LENGTH_LONG).show();
        }



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
                intent.putExtra(Config.JSON_SIGLE, dep.getSigle());
                intent.putExtra(Config.JSON_COURSE_TITLE, dep.getTitle());
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
            e.printStackTrace();
        }

        Collections.sort(departmentList, new Comparator<Department>() {
            @Override
            public int compare(Department lhs, Department rhs) {
                return lhs.getSigle().compareTo(rhs.getSigle());
            }
        });

//        Log.v(LOG_TAG, String.format("%s departments loaded.", departmentList.size()));
        departmentAdapter = new ArrayAdapter<Department>(this, R.layout.item_department, departmentList);
        listView.setAdapter(departmentAdapter);
    }
}
