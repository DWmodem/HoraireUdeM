package com.mobile.umontreal.schedule.misc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.mobile.umontreal.schedule.Config;
import com.mobile.umontreal.schedule.DepartmentsActivity;
import com.mobile.umontreal.schedule.NavigationActivity;
import com.mobile.umontreal.schedule.R;
import com.mobile.umontreal.schedule.SettingsActivity;
import com.mobile.umontreal.schedule.googleI.GoogleIntegrationManager;

/**
 * Created by Philippe on 29/03/2015.
 */
public class MenuHelper {

    public MenuHelper(){
        //Base constructor
    }


    public static boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);

        //Set the menu to the proper login/logout button in accordance with the login state
        MenuItem loginItem = menu.findItem(R.id.action_login);
        MenuItem logoutItem = menu.findItem(R.id.action_logout);

        if(Config.isLoggedIn){

            //Logged in, so make log in button invisible
            loginItem.setEnabled(false);
            loginItem.setVisible(false);


            logoutItem.setEnabled(true);

        } else {
            //Logged out, so make log out button invisible
            loginItem.setEnabled(true);


            logoutItem.setEnabled(false);
            logoutItem.setVisible(false);
        }

        return true;
    }
    public static boolean onPrepareOptionsMenu (Menu menu) {

        //Set the menu to the proper login/logout button in accordance with the login state
        MenuItem loginItem = menu.findItem(R.id.action_login);
        MenuItem logoutItem = menu.findItem(R.id.action_logout);

        if(Config.isLoggedIn){
            loginItem.setEnabled(false);
            logoutItem.setEnabled(true);

            loginItem.setVisible(false);


        } else {
            loginItem.setEnabled(true);
            logoutItem.setEnabled(false);
            logoutItem.setVisible(false);
        }
        return true;
    }

    public static boolean onOptionsItemSelected(Context context, MenuItem item, Activity activity) {
        //
        //Handle action bar item clicks here. The action bar will
        //automatically handle clicks on the Home/Up button, so long
        //as you specify a parent activity in AndroidManifest.xml.
        //
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        Log.d("MENUITEM", "onOptionsItemSelected has been run");
        switch(item.getItemId()){
            case R.id.action_settings:
            {
                Log.d("MENUITEM", "Settings has been run");
//  Toast.makeText(context, "Settings Item clicked", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(activity, SettingsActivity.class);
                activity.startActivity(myIntent);

                return true;
            }
            case R.id.action_calendar:
            {
                // Launching new Activity
                Intent intent = new Intent(activity, NavigationActivity.class);
                // Sending data to NavigationActivity
                intent.putExtra(Config.SCHEDULE_KEY_PAGE, "schedule");
                // Start new Activity
                activity.startActivity(intent);
                return true;
            }
//            case R.id.action_schedule:
//            {
//                Intent myIntent= new Intent(activity, ScheduleActivity.class);
//                activity.startActivity(myIntent);
//                Toast.makeText(context, "Schedule Item clicked", Toast.LENGTH_SHORT).show();
//                Log.d("MENUITEM", "Schedule has been run");
//                return true;
//            }
//            case R.id.action_timetable:
//            {
//                Toast.makeText(context, "Timetable Item clicked", Toast.LENGTH_SHORT).show();
//                Log.d("MENUITEM", "Timetable has been run");
//                return true;
//            }
            case R.id.action_add_new_course:
            {
                // Launching new Activity on selecting single List Item

                Intent intent = new Intent(activity, DepartmentsActivity.class);
                activity.startActivity(intent);
                return true;
            }
            case R.id.action_synchronize:
            {
                Toast.makeText(context, "Synchronize Item clicked", Toast.LENGTH_SHORT).show();
                Log.d("MENUITEM", "Synchronize has been run");
                return true;
            }
            case R.id.action_share:
            {
                Toast.makeText(context, "Share Item clicked", Toast.LENGTH_SHORT).show();
                Log.d("MENUITEM", "Share has been run");
                return true;
            }
            case R.id.action_logout:
            {
                Toast.makeText(context, "Logout Item clicked", Toast.LENGTH_SHORT).show();
                Log.d("MENUITEM", "Logout has been run");
                return true;
            }
            case R.id.action_login:
            {
                Toast.makeText(context, "Login Item clicked", Toast.LENGTH_SHORT).show();
                Log.d("MENUITEM", "Login has been run");
                GoogleIntegrationManager.loginAction(activity);
                return true;
            }

            default:
            {

            }
        }

        return false;
    }


}
