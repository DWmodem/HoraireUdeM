package com.mobile.umontreal.schedule.misc;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.AccountPicker;
import com.mobile.umontreal.schedule.Config;
import com.mobile.umontreal.schedule.R;
import com.google.android.gms.auth.GoogleAuthUtil;
/**
 * Created by Philippe on 30/03/2015.
 */
public class GoogleIntegrationManager {

    //Constructor
    public GoogleIntegrationManager(){

    }

    //Prompt the user to enter their google account
    public static void loginAction(Activity currentActivity){

        String[] accountTypes = new String[]{"com.google"};

        Intent intent;
        intent = AccountPicker.newChooseAccountIntent(null, null, accountTypes, false, null, null, null, null);

        currentActivity.startActivityForResult(intent, Config.REQUEST_CODE_PICK_ACCOUNT);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data, Activity activity){

        //If the activity's result comes from the account picker
        if (requestCode == Config.REQUEST_CODE_PICK_ACCOUNT) {

            //If the activity's result worked
            if (resultCode == Config.RESULT_OK) {

                Config.userEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                Toast.makeText(activity.getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();

                //The user is logged in now!
                Config.isLoggedIn = true;
                ActivityCompat.invalidateOptionsMenu(activity);
                // With the account name acquired, go get the auth token


            } else if (resultCode == Config.RESULT_CANCELED) {
                // The account picker dialog closed without selecting an account.

            }
        }
        // Later, more code will go here to handle the result from some exceptions...
    }
}
