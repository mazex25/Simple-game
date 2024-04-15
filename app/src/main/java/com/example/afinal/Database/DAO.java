package com.example.afinal.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.afinal.User.User;
import com.example.afinal.Database.DatabaseHelper;

public class DAO {
    DatabaseHelper databaseHelper;
    SQLiteDatabase MyDatabase;

    public DAO(Context context){
        databaseHelper = new DatabaseHelper(context);
        MyDatabase = databaseHelper.getReadableDatabase();
    }

    public void insertData(User user) {
        SQLiteDatabase MyDatabase = this.databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", user.getFullname());
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("previousScore", 0);
        contentValues.put("newScore", 0);
        long result = MyDatabase.insert("tblUser", null, contentValues);
        if (result != -1) {
            MyDatabase.close();
        }
    }

    public int updatePreviousScore(User user){
        SQLiteDatabase MyDatabase = this.databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("previousScore", user.getPreviousScore());
        return MyDatabase.update("tblUser", contentValues, "userId=?", new String[]{""+user.getUserId()});
    }

    public boolean UserExited(String username) {
        String query = "SELECT * FROM tblUser WHERE username='" + username + "'";
        Cursor cursor = databaseHelper.getData(query);
        return cursor.moveToNext();
    }

    public User checkUsernameAndPassword(String username, String password) {
        String query = "SELECT * FROM tblUser WHERE username='" + username + "' and password='" + password + "'";
        Cursor cursor = databaseHelper.getDataRow(query);

        if (cursor.getCount() > 0) {
            return new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getInt(4), cursor.getInt(5));
        }
        return null;
    }

    public boolean signIn(User user){
        User existedUser = checkUsernameAndPassword(user.getUsername(), user.getPassword());
        return existedUser != null;
    }
}
