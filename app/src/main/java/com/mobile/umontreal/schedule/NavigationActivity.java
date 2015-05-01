package com.mobile.umontreal.schedule;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.mobile.umontreal.schedule.db.UDMDatabaseManager;
import com.mobile.umontreal.schedule.googleI.GoogleIntegrationManager;
import com.mobile.umontreal.schedule.misc.DataHolderClass;
import com.mobile.umontreal.schedule.misc.MenuHelper;
import com.mobile.umontreal.schedule.navigation.DrawerCallbacks;
import com.mobile.umontreal.schedule.navigation.MyDialogFragment;
import com.mobile.umontreal.schedule.navigation.NavDrawerFragment;
import com.mobile.umontreal.schedule.navigation.NavItem;
import com.mobile.umontreal.schedule.schedule.ScheduleCoursesFragment;

import java.util.List;

import static com.mobile.umontreal.schedule.R.id;


public class NavigationActivity extends ActionBarActivity
        implements DrawerCallbacks {

    //Tag for logging purposes
    private String LOG_TAG = NavigationActivity.class.getSimpleName();

    private Toolbar toolbar;
    private NavDrawerFragment navDrawerFragment;

    // Data Base
    private UDMDatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Connection DataBase
        db = new UDMDatabaseManager(this);

        //Before launching, verify the database state
        if (db.isEmpty()) {

            // Launching new Activity on selecting single List Item
            Intent intent = new Intent(getApplicationContext(), DepartmentsActivity.class);
            db.close();
            this.startActivity(intent);
        }

        toolbar = (Toolbar) findViewById(id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                }
                return false;
            }
        });

        navDrawerFragment = (NavDrawerFragment) getFragmentManager().findFragmentById(id.fragment_drawer);
        navDrawerFragment.setup(id.fragment_drawer, (DrawerLayout) findViewById(id.drawer), toolbar);

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

        return MenuHelper.onOptionsItemSelected(getApplicationContext(), item, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        GoogleIntegrationManager.onActivityResult(requestCode, resultCode, data, this);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        List<NavItem> _data = DataHolderClass.getInstance().getDistributor();

        if (_data != null) {

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ScheduleCoursesFragment fragment = new ScheduleCoursesFragment();
            Bundle args = new Bundle();
            args.putString(Config.SCHEDULE_KEY_PAGE, _data.get(position).getSession());
            fragment.setArguments(args);
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onBackPressed() {
        if (navDrawerFragment.isDrawerOpen())
            navDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    public void ToastMsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
