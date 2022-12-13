package net.smallacademy.authenticatorapp.authenticatorapp.pref;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;

public class Quiz {



    Activity activity;

    public Quiz(Activity activity) {
        this.activity = activity;
    }

    public void setQuiz(String data, String type) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(type, MODE_PRIVATE).edit();
        editor.putString(type, data);
        editor.apply();
    }

    public String getQuiz(String type) {
        SharedPreferences prefs = activity.getSharedPreferences(type, MODE_PRIVATE);
        return prefs.getString(type, " ");
    }


}
