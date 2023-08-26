package hehvph21007.poly.comicpoly.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class SharedPreferencesUtil {
    public static <T> T getObject(Context context, String key, Class<T> classType) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String base64 = preferences.getString(key, null);
        if (base64 != null) {
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
            String json = new String(bytes);
            try {
                return new Gson().fromJson(json, classType);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> void putObject(Context context, String key, T object) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        String json = new Gson().toJson(object);
        String base64 = Base64.encodeToString(json.getBytes(), Base64.DEFAULT);
        editor.putString(key, base64);
        editor.apply();
    }
    public static void removeObject(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }
}