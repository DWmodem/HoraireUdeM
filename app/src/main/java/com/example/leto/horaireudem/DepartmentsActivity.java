package com.example.leto.horaireudem;

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
import android.widget.TextView;

import com.example.leto.horaireudem.misc.NavigationAdapter;
import com.example.leto.horaireudem.misc.SpinnerNavItem;
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
    // Navigation Spinner [ Winter Summer Autumn ]
    private ArrayList<SpinnerNavItem> navSpinner;
    // List view Adapter
    private ArrayAdapter<Department> departmentAdapter;
    // Navigation adapter
    private NavigationAdapter navAdapter;
    // List view
    private ListView listView;
    // Search EditText
    private EditText inputSearch;
    // ArrayList for departments
    private List<Department> departmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);

        listView = (ListView) findViewById(R.id.list_departments);
        inputSearch = (EditText) findViewById(R.id.input_search);


        // Action Bar settings
        actionBar = getSupportActionBar();
        // Hide the action bar title
        actionBar.setDisplayShowTitleEnabled(false);
        // Enabling Spinner dropdown navigation
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);


        // Adding Drop-down Navigation
        addBarNavigation();

        String uri = MainActivity.URL_API_UDEM + "sigles.json";

//        UDMJsonData data = new UDMJsonData(uri);
//        data.execute();
//        Log.v(LOG_TAG, "cornel : " + departments.size());

        // Fill the View List
        fillViewList();



        /**
         * Listening to single list item on click
         * */

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // Selected item
                CharSequence item = ((TextView) v).getText().toString();

                // Launching new Activity on selecting single List Item
                Intent intent = new Intent(getApplicationContext(), CoursesActivity.class);
//
//              // Sending data to new activity
                intent.putExtra("key", item);
                startActivity(intent);
//                Toast.makeText(context, item, Toast.LENGTH_SHORT).show();

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

    /**
     * Fill all lists: Departments, Courses, Sessions
     * */

    private void fillViewList() {
        UDMJsonData data = new UDMJsonData(MainActivity.URL_API_UDEM + "sigles.json");
        data.execute(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_departments, menu);
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

    // Adding Drop-down Navigation
    private void addBarNavigation() {

        // Spinner navigation settings
        navSpinner = new ArrayList<SpinnerNavItem>();
        navSpinner.add(new SpinnerNavItem("Autumn ", 2014));
        navSpinner.add(new SpinnerNavItem("Winter ", 2015));
        navSpinner.add(new SpinnerNavItem("Summer ", 2015));

        // title drop down adapter
        navAdapter = new NavigationAdapter(getApplicationContext(), navSpinner);
        // Set the selected navigation item
        // Assigning the spinner navigation
        actionBar.setListNavigationCallbacks(navAdapter, this);
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
        departmentAdapter = new ArrayAdapter<Department>(this, R.layout.department_item, departmentList);
        listView.setAdapter(departmentAdapter);
    }
}
