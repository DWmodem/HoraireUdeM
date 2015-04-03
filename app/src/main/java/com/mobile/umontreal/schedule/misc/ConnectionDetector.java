package com.mobile.umontreal.schedule.misc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Corneliu on 02-Apr-2015.
 */
public class ConnectionDetector {

    private Context _context;

    public ConnectionDetector(Context context){
        this._context = context;
    }

    /**
     * Checking for all possible internet providers
     * **/
    public boolean isConnectingToInternet() {

        ConnectivityManager connectivity =
                (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = connectivity.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            return true;
        }
        return false;
    }
}
