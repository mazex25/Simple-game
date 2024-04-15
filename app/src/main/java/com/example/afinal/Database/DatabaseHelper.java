package com.example.afinal.Database;

import static java.lang.String.format;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.afinal.User.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Signup.db";
    private List<User> userList;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void queryData(String sql) {
        SQLiteDatabase MyDatabase = getWritableDatabase();
        MyDatabase.execSQL(sql);
    }

    public Cursor getData(String sql) {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery(sql, null);
    }

    public Cursor getDataRow(String sql) {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        return cursor;
    }

    private void SampleData(){
        userList = new ArrayList<>();
        userList.add(new User(1,"Nguyen Van Nhan", "Nhan", "123456", 0, 0));
        userList.add(new User(2,"Truong Hieu Tam", "Tam", "987654",0,0));
    }

    private void addSampleData(SQLiteDatabase MyDatabase) {
        SampleData();

        // Add user
        for (User user : userList) {
            MyDatabase.execSQL(format("INSERT INTO tblUser VALUES(null, '%s', '%s', '%s', '%s', '%s')",
                    user.getFullname(), user.getUsername(), user.getPassword(), user.getPreviousScore(), user.getNewSore()));
        }
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        String createTableQuery ="create Table tblUser(userId INTEGER primary key AUTOINCREMENT,fullname TEXT, username TEXT, password TEXT, previousScore INTEGER, newScore INTEGER)";
        MyDatabase.execSQL(createTableQuery);
        Log.i("SQLite", "DATABASE CREATED");
        addSampleData(MyDatabase);
        Log.i("SQLite", "ADDED DATA");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int oldVersion, int newVersion) {
        Log.i("SQLite","Upgrade SQLite");
        MyDatabase.execSQL("drop Table if exists tblUser");
    }


}
