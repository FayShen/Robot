package com.kefujiqiren.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kefujiqiren.R;

public class SettingActivity extends AppCompatActivity {


    public static void activityStart(Context context){
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
}
