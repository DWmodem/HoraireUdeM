package com.example.leto.horaireudem;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.leto.horaireudem.misc.UDMJsonData;

/**
 * Main Activity for tests ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * */
public class MainActivity extends ActionBarActivity {

    public static final String URL_API_UDEM = "http://www-labs.iro.umontreal.ca/";

    public static final String TAG_TRIMESTER  = "trimestre";
    public static final String TAG_ACRONYM    = "sigle";
    public static final String TAG_COURSE_NUM = "section";
    public static final String TAG_SECTION    = "coursnum";

    public static final String TAG_TITLE      = "titre";

    UDMDatabaseManager dbh;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(), "On peut aller dans une autre activity", Toast.LENGTH_LONG).show();

        // Connection DataBase
        dbh = new UDMDatabaseManager(this);
        db = dbh.getWritableDatabase();

//        // Launching new Activity
//        Intent intent = new Intent(getApplicationContext(), DepartmentsActivity.class);
////        Intent intent = new Intent(getApplicationContext(), CoursesActivity.class);
//
//        // Sending data to new activity
//        intent.putExtra(TRIMESTER_PARAM, "A14");
//        startActivity(intent);

//        Read the JSON files

        String uri = URL_API_UDEM + "~roys/horaires/json/sigles.json";

        UDMJsonData data = new UDMJsonData(uri, new String[]{TAG_TITLE, TAG_ACRONYM});
        data.execute();



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
