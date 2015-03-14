package com.example.leto.horaireudem;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import android.widget.Toast;

import com.example.leto.horaireudem.objects.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for Departments
 * */
public class MainActivity extends ActionBarActivity {

    // List view
    private ListView listView;

    // List view Adapter
    ArrayAdapter<Department> adapter;

    // Search EditText
    EditText inputSearch;

    // ArrayList for departments
    List<Department> departmentList;

    UDMDatabaseManager dbh;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_departments);
        inputSearch = (EditText) findViewById(R.id.input_search);

        dbh = new UDMDatabaseManager(this);
        db = dbh.getWritableDatabase();
        fillLists();

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
                MainActivity.this.adapter.getFilter().filter(s);
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

    private void fillLists() {

        // TODO une fonctione generique... qui va ramplir les listes avec les donnees

        Department d1 = new Department();
        d1.setSigle("act");
        d1.setTitle("Actuariat");
        d1.setCourses(null);

        Department d2 = new Department();
        d2.setSigle("aeg");
        d2.setTitle("Animation");
        d2.setCourses(null);

        Department d3 = new Department();
        d3.setSigle("cep");
        d3.setTitle("Communication et politique");
        d3.setCourses(null);

        Department d4 = new Department();
        d4.setSigle("ift");
        d4.setTitle("Informatique");
        d4.setCourses(null);

        List<Department> listDepratments = new ArrayList<Department>();
        listDepratments.add(d4);
        listDepratments.add(d1);
        listDepratments.add(d2);
        listDepratments.add(d3);
        listDepratments.add(d2);
        listDepratments.add(d1);

        adapter = new ArrayAdapter<Department>(this, R.layout.department_item, listDepratments);
        listView.setAdapter(adapter);

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
}
