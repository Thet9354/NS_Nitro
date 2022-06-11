package com.example.nsapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserDataBaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_USER_EMAIL = "USER_EMAIL";
    public static final String COLUMN_USER_PASSWORD = "USER_PASSWORD";
    public static final String COLUMN_USER_FULLNAME = "USER_FULLNAME";
    public static final String COLUMN_USER_NIRC = "USER_NIRC";
    public static final String COLUMN_USER_DATOFBIRTH = "USER_DATOFBIRTH";
    public static final String COLUMN_ID = "ID";

    public UserDataBaseHelper(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " + COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT, "+ COLUMN_USER_FULLNAME + " TEXT, " + COLUMN_USER_NIRC + " TEXT, " + COLUMN_USER_DATOFBIRTH + " TEXT)";

        db.execSQL(createUserTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOneUser(UserModel userModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, userModel.getUsername());
        cv.put(COLUMN_USER_EMAIL, userModel.getEmail());
        cv.put(COLUMN_USER_PASSWORD, userModel.getPassword());
        cv.put(COLUMN_USER_FULLNAME, userModel.getFullName());
        cv.put(COLUMN_USER_NIRC, userModel.getNIRC());
        cv.put(COLUMN_USER_DATOFBIRTH, userModel.getDateOfBirth());

        long insert = db.insert(USER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean DeleteOneUser(UserModel userModel)
    {
        // find customerModel in the database. if it found, delete it and return true.
        // if it is not found, return false.

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM" + USER_TABLE + "WHERE" + COLUMN_ID + " = " + userModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }
        else {
            return false;
        }
    }

    public List<UserModel> getEveryUser()
    {
        List<UserModel> returnList = new ArrayList<>();

        // get data from the database

        String queryString = "SELECT * FROM " + USER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            // loop through the cursor (result set) and create new customer objects. Put them into the return list.
            do {
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String userEmail = cursor.getString(2);
                String userPassword = cursor.getString(3);
                String userFullName = cursor.getString(4);
                String userNIRC = cursor.getString(5);
                String userDateOfBirth = cursor.getString(6);

                UserModel newUser = new UserModel(userID, userName, userEmail, userPassword, userFullName, userNIRC, userDateOfBirth);
                returnList.add(newUser);
            } while (cursor.moveToNext());
        }
        else {
            // failure. do not add anything to the list.
        }

        // close both the cursor and the db when done.
        cursor.close();
        db.close();
        return returnList;
    }

    private boolean isValueExist(String value)
    {
        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_USER_NAME + " = ?";
        String[] whereArgs = {value};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        int count = cursor.getCount();

        cursor.close();

        return count >= 1;
    }
}
