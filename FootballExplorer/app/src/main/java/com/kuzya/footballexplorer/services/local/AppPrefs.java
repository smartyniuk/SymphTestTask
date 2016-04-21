package com.kuzya.footballexplorer.services.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by kuzya on 21.04.2016.
 */
@SuppressWarnings("ALL")
public class AppPrefs {
    private final SharedPreferences _sp;

    public AppPrefs(Context context, String fileName) {
        _sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public AppPrefs(Context context) {
        _sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SharedPreferences getSharedPreferences() {
        return _sp;
    }

    public String get(String key, String defValue) {
        return _sp.getString(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return _sp.getBoolean(key, defValue);
    }

    public float get(String key, float defValue) {
        return _sp.getFloat(key, defValue);
    }

    public int get(String key, int defValue) {
        return _sp.getInt(key, defValue);
    }

    public long get(String key, long defValue) {
        return _sp.getLong(key, defValue);
    }

    public void put(String key, String value) {
        if (value == null) {
            _sp.edit().remove(key).apply();
        } else {
            _sp.edit().putString(key, value).apply();
        }
    }

    public void put(String key, boolean value) {
        _sp.edit().putBoolean(key, value).apply();
    }

    public void put(String key, float value) {
        _sp.edit().putFloat(key, value).apply();
    }

    public void put(String key, long value) {
        _sp.edit().putLong(key, value).apply();
    }

    public void put(String key, int value) {
        _sp.edit().putInt(key, value).apply();
    }
}
