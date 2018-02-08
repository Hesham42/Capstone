package com.example.hesham.moves.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Hesham on 9/27/2017.
 */

public class InternetConnection {
    public static boolean checkConnection(Context context) {
        if (((ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo()!=null)
        {
            return true;
        }else
        {

            return false ;

        }
    }
}
