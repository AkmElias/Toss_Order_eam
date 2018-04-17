package com.hellologic.toss_order_team.models;

import android.util.Log;

public class Logger {

    private static boolean shouldLog=true;
    private static String TAG = "my_tag";
    public static void log(String val) {
        if(shouldLog) {
            Log.d(TAG, val);
        }
    }
}
