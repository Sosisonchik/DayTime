package com.company.sosison.daytime;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "taskdb";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_TASK = "dtable";
    public static final String KEY_TASK = "dtask";
    public static final String KEY_TIME = "dtime";
    public static final String KEY_CHECK = "dcheck";
    public static final String KEY_MARK = "dmark";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_TASK+"( "+KEY_TASK+" text, "+KEY_TIME+" text, "+KEY_CHECK+" text,"+KEY_MARK+" text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
