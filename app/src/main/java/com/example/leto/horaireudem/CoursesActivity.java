package com.example.leto.horaireudem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leto.horaireudem.objects.Course;
import com.example.leto.horaireudem.objects.Department;

import java.util.ArrayList;
import java.util.List;


public class CoursesActivity extends ActionBarActivity {

    // List view
    private ListView listView;

    // List view Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;

    // ArrayList for departments
    List<Course> departmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        listView = (ListView) findViewById(R.id.list_courses);
        inputSearch = (EditText) findViewById(R.id.input_search);

        Bundle extras = getIntent().getExtras();
        String inputString = extras.getString("key");
        TextView titleDepartment = (TextView) findViewById(R.id.title_department);
        titleDepartment.setText(inputString);

        fillLists();

        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int lengthBefore, int lengthAfter) {
                // When user changed the Text
                CoursesActivity.this.adapter.getFilter().filter(s);
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

    private void fillLists() {

        // TODO ramplir la liste avec des courses

        String listCourses[] = {
                "IFT1015 - Programmation 1",
                "IFT1025 - Programmation 2",
                "IFT1170 - Programmation Java et applications",
                "IFT2905 - Interfaces personne-machine",
                "IFT1015 - Programmation 1",
                "IFT1025 - Programmation 2",
                "IFT1170 - Programmation Java et applications"

        };



        adapter = new ArrayAdapter<String>(this, R.layout.course_item, listCourses);
        listView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_courses, menu);
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


}
