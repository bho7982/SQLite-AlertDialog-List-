package com.example.inthetech.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by inthetech on 2017-01-06.
 */

public class myDB extends SQLiteOpenHelper {
    public myDB(Context context){
        super(context, "human", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table member (user_name Integer primart key, name cher(10))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS member");
        onCreate(db);
    }
}
