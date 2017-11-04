package tong.cau.com.cautong;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.gson.Gson;

public class PlayerPrefs {

    private SharedPreferences setting = null;
    private SharedPreferences.Editor editor = null;

    private static PlayerPrefs instance = null;

    public static PlayerPrefs getTestInstance(MainActivity activity) {
        if (instance == null)
            instance = new PlayerPrefs(activity);
        return instance;
    }

    private PlayerPrefs(MainActivity activity) {
        if (setting == null) setting = activity.getSharedPreferences("dialogSetting", 0);
        if (editor == null) editor = setting.edit();
    }

    public static PlayerPrefs getInstance() {
        if (instance == null)
            instance = new PlayerPrefs();
        return instance;
    }

    private PlayerPrefs() {
        if (setting == null)
            setting = MainActivity.instance.getSharedPreferences("dialogSetting", 0);
        if (editor == null) editor = setting.edit();
    }

    public void removeKey(String key) {
        editor.remove(key);
    }

    public boolean hasKey(String key) {
        return setting.contains(key);
    }

    public void setFloat(String key, float value) {
        editor.putFloat(key, value);
    }

    public void setString(String key, String value) {
        editor.putString(key, value);
    }

    public void save() {
        editor.commit();
    }

    public void setInt(String key, int value) {
        editor.putInt(key, value);
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
    }

    public float getFloat(String key) {
        float ret = 0;
        ret = setting.getFloat(key, ret);
        return ret;
    }

    public String getString(String key) {
        String ret = "";
        ret = setting.getString(key, ret);
        return ret;
    }

    public boolean getBoolean(String key) {
        boolean ret = false;
        ret = setting.getBoolean(key, ret);
        return ret;
    }

    public int getInt(String key) {
        int ret = 0;
        ret = setting.getInt(key, ret);
        return ret;
    }
}
