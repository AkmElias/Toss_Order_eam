package com.hellologic.toss_order_team.models;

import android.util.Log;

public class Utils {

    private static boolean shouldLog=true;
    private static String TAG = "my_tag";
    public static void log(String val) {
        if(shouldLog) {
            Log.d(TAG, val);
        }
    }

    public static String removeSpaceFromString(String str){
        String ret = "";
        for(int i=0; i< str.length(); i++) {
            if(str.charAt(i) != ' ')
            {
                ret+= str.charAt(i);
            }
            else{
                ret+= "_";
            }
        }
        return ret;
    }

    public static int getRotationAns() {
        int ret=0;
        int random  = (int) (Math.random()*100) +1;
        for(int i=0; i<10; i++)
            ret += (int) (Math.random()*10);
        if(ret/random >= 5)
            return 1;

        return 0;
    }
}
