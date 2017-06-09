package com.kefujiqiren.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by 殇痕 on 2017/3/21.
 */

public class UserDB{
    private static UserDB userDB;
    private SQLiteDatabase db;
    public static final String DB_NAME = "user.db";
    public int VERSION = 1;
    public UserDB(Context context){
        DBUserOpenHelpter dbHelpter = new DBUserOpenHelpter(context, DB_NAME, null, VERSION);
        db = dbHelpter.getWritableDatabase();
        Log.d("hello", "new database");
    }
    /*
	 * 保证全局范围内只有一个db实例。。
	 */
    public static synchronized UserDB getInstance(Context context){
        if(userDB == null){
            userDB = new UserDB(context);
        }
     return userDB;
    }
    public boolean RegisterUser(String name, String password){
        String sql = "insert into user(username, password)values(?,?)";
        ContentValues values = new ContentValues();
        values.put("username", name);
        values.put("password", password);
        Long rt = db.insert("user", null, values);
        if(rt>0){
            return true;
        }
        return  false;
    }
    public boolean hasUserLogin(String username, String password){
        String sql = "select * from user where username=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username, password});
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }
        return false;
    }

    public int getUserId(String username){
        String sql = "select userid from user where username=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        if(cursor.moveToFirst()){
            return cursor.getInt(cursor.getColumnIndex("userid"));
        }
        return -1;
    }

    public void changeUserName(int userid, String username){
        String sql = "update user set username=? where userid=?";
        db.execSQL(sql, new String[]{username, String.valueOf(userid)});
    }

    public void changePassword(int userid, String password){
        String sql = "update user set password=? where userid=?";
        db.execSQL(sql, new String[]{password, String.valueOf(userid)});
    }
}
