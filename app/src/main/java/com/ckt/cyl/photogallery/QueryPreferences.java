package com.ckt.cyl.photogallery;

import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceManager;

/**
 * Created by D22434 on 2017/7/31.
 */

public class QueryPreferences {
    private static final String PRE_SEARCH_QUERY = "searchQuery";

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
}
