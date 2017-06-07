package com.kefujiqiren.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 殇痕 on 2017/6/7.
 */

public class DBUserOpenHelpter extends SQLiteOpenHelper {

    private Context context;
    public static final String CREATE_USER_TABLE="create table user("
            +"userid integer primary key autoincrement,"
            +"username text not null unique,"
            +"password text not null)";

    public DBUserOpenHelpter(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        Toast.makeText(context, "create success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
