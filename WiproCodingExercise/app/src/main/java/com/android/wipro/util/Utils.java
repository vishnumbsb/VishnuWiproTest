package com.android.wipro.util;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    private static Context _context = null;


    public static Boolean isOnline(Context context) {
        _context = context;
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                /*for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }*/

                if (connectivity.getActiveNetworkInfo() != null
                        && connectivity.getActiveNetworkInfo().isAvailable()
                        && connectivity.getActiveNetworkInfo().isConnected()) {

                    return true;
                }

        }
        return false;
    }

    public static boolean isTablet(Context context) {
        //return context.getResources().getConfiguration().smallestScreenWidthDp >= 600;
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
