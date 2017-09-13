package com.pack.pack.application.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Saurav on 16-05-2017.
 */
public class PreferenceManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "squill-intro";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String IS_FIRST_TIME_LOGIN = "IsFirstTimeLogin";

    public PreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        //return true;
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLogin(boolean isFirstTimeLogin) {
        editor.putBoolean(IS_FIRST_TIME_LOGIN, isFirstTimeLogin);
        editor.commit();
    }

    public boolean isFirstTimeLogin() {
        return pref.getBoolean(IS_FIRST_TIME_LOGIN, false);
    }

    public String getNotificationCount(int notificationID) {
        return pref.getString(String.valueOf(notificationID), null);
    }

    public void setNotificationCount(int notificationID, String count) {
        editor.putString(String.valueOf(notificationID), count);
        editor.commit();
    }
}
