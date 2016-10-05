package com.genericregistrationsystem;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * This is the class for checking network connectivity.
 * This will be used by the activities for checking connectivities.
 *
 * Created by Alan Dimaano on 8/16/2015.
 * Rhiza C. Roque
 */

public class NetworkUtility {

    /**
     * This a method that returns a boolean value
     * for checking is network.
     *
     * @param context
     * @return
     */
        public static Boolean isNetWorking(Context context) {
            try {

                //Declaration for the Connectivitymanager
                //For sole checking of connectivity.
                ConnectivityManager ConMgr = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                if (ConMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED
                        || ConMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                        .getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                } else {
                    return false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }
}
