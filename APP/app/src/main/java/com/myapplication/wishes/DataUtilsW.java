package com.myapplication.wishes;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class DataUtilsW extends AppCompatActivity {

    public static String xmlFileName=null;
    public static void writeString(Context c, String name, String data){
        if(xmlFileName==null)xmlFileName="data";
        SharedPreferences pref = c.getSharedPreferences(xmlFileName,MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(name,data);
        editor.commit();
    }
    public static String readString(Context c, String name){

        if(xmlFileName==null)xmlFileName="data";
        SharedPreferences pref = c.getSharedPreferences(xmlFileName,MODE_PRIVATE);
        return pref.getString(name,null);//第二个参数为默认值
    }
}
