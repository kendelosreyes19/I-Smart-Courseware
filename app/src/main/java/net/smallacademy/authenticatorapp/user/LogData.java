package net.smallacademy.authenticatorapp.user;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;

public class LogData {


    Activity activity;

    public LogData(Activity activity) {
        this.activity = activity;
    }

    public void setLogin(String data, String type) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(type, MODE_PRIVATE).edit();
        editor.putString(type, data);
        editor.apply();
    }

    public String getLogin(String type) {
        SharedPreferences prefs = activity.getSharedPreferences(type, MODE_PRIVATE);
        return prefs.getString(type, "");
    }


}
