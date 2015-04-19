package com.example.leto.horaireudem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Justin on 2015-03-26.
 */
public class ActionBarHelper {
    public static void openSettings(Activity activity){
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }
}
