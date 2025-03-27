package com.pomanager.economiccalender.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

public class PreferenceManager {
    private static PreferenceManager preferenceManager;

    private static SharedPreferences.Editor editor;
    private static SharedPreferences sharedPreferences;

    public static PreferenceManager getInstance(Context context) {

        if (preferenceManager == null) {
            preferenceManager = new PreferenceManager();
        }

        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("SharedPref", 0);
        }
        editor = sharedPreferences.edit();

        return preferenceManager;
    }

    public String getId() {
        return sharedPreferences.getString("id", "");
    }

    public void editor(String str, String str2) {
        editor.putString(str, str2);
        editor.apply();
    }

    public void editor(String str, int i) {
        editor.putInt(str, i);
        editor.apply();
    }

    public void editor(String str, boolean z) {
        editor.putBoolean(str, z);
        editor.apply();
    }

    public void editor(String str, long j) {
        editor.putLong(str, j);
        editor.apply();
    }

    public void remove(String str) {
        editor.remove(str);
        editor.apply();
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }

    String KEY_PRIORITY_FILTER = "KEY_PRIORITY_FILTER";

    public void setPriorityFilter(String data) {
        editor(KEY_PRIORITY_FILTER, data);
    }

    public String getPriorityFilter() {
        return sharedPreferences.getString(KEY_PRIORITY_FILTER, "1,2,3");
    }

    String KEY_COUNTRY_FILTER = "KEY_COUNTRY_FILTER";

    public void setSelectedCountries(String data) {
        editor(KEY_COUNTRY_FILTER, data);
    }

    public ArrayList<String> getSelectedCountries() {
        String[] countryArray = sharedPreferences.getString(KEY_COUNTRY_FILTER, CountriesFilter.getDefaultSelectedCountry()).split(",");
        return new ArrayList<>(Arrays.asList(countryArray));
    }

    public String getSelectedCountriesStr() {
        return String.join(",", getSelectedCountries());
    }

    String KEY_COUNTRY_TYPE_FILTER = "KEY_COUNTRY_TYPE_FILTER";

    public void setSelectedCountriesType(String data) {
        editor(KEY_COUNTRY_TYPE_FILTER, data);
    }

    public String getSelectedCountriesType() {
        return sharedPreferences.getString(KEY_COUNTRY_TYPE_FILTER, "all");
    }

    String KEY_CURRENT_TIMEZONE = "KEY_CURRENT_TIMEZONE";

    public void setSelectedTimezone(String data) {
        editor(KEY_CURRENT_TIMEZONE, data);
    }

    public String getSelectedTimezone() {
        return sharedPreferences.getString(KEY_CURRENT_TIMEZONE, TimeFormatter.getDefaultTimeZone());
    }

    String KEY_IS_FIRST_TIME = "KEY_IS_FIRST_TIME";

    public void setIsFirstTime(boolean data) {
        editor(KEY_IS_FIRST_TIME, data);
    }

    public boolean getIsFirstTime() {
        return sharedPreferences.getBoolean(KEY_IS_FIRST_TIME, true);
    }


    String KEY_API = "KEY_API";

    public void setApi(String data) {
        editor(KEY_API, data);
    }

    public String getApi() {
        return sharedPreferences.getString(KEY_API, "");
    }


    String KEY_TOKEN = "KEY_TOKEN";

    public void setToken(String data) {
        editor(KEY_TOKEN, data);
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, "");
    }

    String KEY_TYPE = "KEY_TYPE";

    public void setOutType(String data) {
        editor(KEY_TYPE, data);
    }

    public String getOutType() {
        return sharedPreferences.getString(KEY_TYPE, "");
    }

}
