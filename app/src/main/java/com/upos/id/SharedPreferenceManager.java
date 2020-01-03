package com.upos.id;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class SharedPreferenceManager {

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPreferenceManager(String SpApp, Context context){
        sp = context.getSharedPreferences(SpApp, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void setSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void setSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void setSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSpString(String keySp){
        return sp.getString(keySp, "");
    }

    public void setStringArrayPref(String key, ArrayList<String> values) {
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            spEditor.putString(key, a.toString());
        } else {
            spEditor.putString(key, null);
        }
        spEditor.commit();
    }

    public ArrayList<String> getStringArrayPref(Context context, String key) {

        String json = sp.getString(key, null);
        ArrayList<String> datas = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String data = a.optString(i);
                    datas.add(data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return datas;
    }
}
