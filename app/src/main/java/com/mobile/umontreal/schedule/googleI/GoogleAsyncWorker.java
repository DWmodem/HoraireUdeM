package com.mobile.umontreal.schedule.googleI;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.mobile.umontreal.schedule.Config;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Philippe on 02/04/2015.
 */
public class GoogleAsyncWorker extends AsyncTask{

    Activity activity;
    String taskID;

    public GoogleAsyncWorker(Activity activity, String taskID){
        this.activity = activity;
        this.taskID = taskID;

    }


    @Override
    protected Object doInBackground(Object[] params) {

        Log.v("GOOGLE_I", "Initiate doInBackground");

        switch(taskID){
            case "getUsername":{

                Log.v("GOOGLE_I", "Case: getUsername");

                String token;
                token = fetchToken();
                Log.v("GOOGLE_I", "fetched token");

                if (token != null) {
                    Log.v("GOOGLE_I", "Token is not nulL! yay!");
                    Log.v("GOOGLE_I", "Token is: "+token);

                    //do stuff

                }
                break;
            }
            default:{
                break;
            }

        }
        return null;
    }

    protected String fetchToken(){

        try{
            return GoogleAuthUtil.getToken(activity, Config.userEmail, Config.SCOPE);

        } catch (UserRecoverableAuthException e) {

            //This exception can be recovered by the user if needed
        } catch (GoogleAuthException e) {

            //Unrecoverable exception

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
