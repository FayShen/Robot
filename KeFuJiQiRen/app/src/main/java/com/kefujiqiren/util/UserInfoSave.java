package com.kefujiqiren.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ShangHen on 2017/6/6.
 */

public class UserInfoSave {
    public static void saveUserInfo(Context context, String _username, String _password){
        SharedPreferences.Editor editor = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE).edit();
        editor.putString("username", _username);
        editor.putString("password", _password);
        editor.commit();
    }
    public static String getUserName(Context context){
        SharedPreferences pref = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String name = pref.getString("username", "");
        return name;
    }

    public static String getPasswrod(Context context){
        SharedPreferences pref = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String password = pref.getString("password","");
        return password;
    }

    public static void setUserName(Context context, String _username){
        SharedPreferences.Editor editor = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE).edit();
        editor.putString("username", _username);
        editor.commit();
    }

    public static void setPassword(Context context, String _password){
        SharedPreferences.Editor editor = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE).edit();
        editor.putString("password", _password);
        editor.commit();
    }
}
