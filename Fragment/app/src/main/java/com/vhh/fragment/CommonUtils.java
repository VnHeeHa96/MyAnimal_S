package com.vhh.fragment;

import android.content.Context;
import android.content.SharedPreferences;

public class CommonUtils {
    private static final String PREF_FILE = "pref_shared";
    private static CommonUtils instance;

    private CommonUtils() {
        //for Singleton
    }

    public static CommonUtils getInstance() {
        if (instance == null) {
            instance = new CommonUtils();
        }
        return instance;
    }

    public void savePref(String key, String data) {
        if (data == null || data.isEmpty()) return;
        if (key == null || key.isEmpty()) return;
        SharedPreferences pref = App.getInstance().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        pref.edit().putString(key, data).apply();
    }

    public void clearPref(String key) {
        if (key == null || key.isEmpty()) return;
        SharedPreferences pref = App.getInstance().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        pref.edit().remove(key).apply();
    }

    public String getPref(String key) {
        if (key == null || key.isEmpty()) return null;
        SharedPreferences pref = App.getInstance().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return pref.getString(key, null);
    }

}
