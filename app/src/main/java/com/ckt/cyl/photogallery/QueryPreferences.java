package com.ckt.cyl.photogallery;

import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceManager;

/**
 * Created by D22434 on 2017/7/31.
 */

public class QueryPreferences {
    private static final String PRE_SEARCH_QUERY = "searchQuery";
    private static final String PRE_LAST_RESULT_ID = "lastResultId";
    private static final String PRE_IS_ALARM_ON = "isAlarmOn";

    public static String getStoreQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PRE_SEARCH_QUERY, null);
    }

    public static void setStoreQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PRE_SEARCH_QUERY, query)
                .apply();
    }

    public static String getLastResultId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PRE_LAST_RESULT_ID, null);
    }

    public static void setLastResultId(Context context, String lastResultId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PRE_LAST_RESULT_ID, lastResultId)
                .apply();
    }

    /**
     * 获取定时器状态
     *
     * @param context
     * @return
     */
    public static boolean isAlarmOn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PRE_IS_ALARM_ON, false);
    }

    /**
     * 添加定时器状态
     *
     * @param context
     * @return
     */
    public static void setAlarmOn(Context context, boolean isOn) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PRE_IS_ALARM_ON, isOn)
                .apply();
    }


}
