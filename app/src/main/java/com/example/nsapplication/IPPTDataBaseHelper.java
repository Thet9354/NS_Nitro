package com.example.nsapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class IPPTDataBaseHelper extends SQLiteOpenHelper {


    public static final String IPPT_TABLE = "IPPT_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_IPPT_VOCATION = "IPPT_VOCATION";
    public static final String COLUMN_IPPT_AGE = "IPPT_AGE";
    public static final String COLUMN_IPPT_PUSHUPPOINTS = "IPPT_PUSHUPPOINTS";
    public static final String COLUMN_IPPT_SITUPPOINTS = "IPPT_SITUPPOINTS";
    public static final String COLUMN_IPPT_RUNPOINTS = "IPPT_RUNPOINTS";
    public static final String COLUMN_IPPT_TOTALPOINTS = "IPPT_TOTALPOINTS";
    public static final String COLUMN_IPPT_STATUS = "IPPT_STATUS";

    public IPPTDataBaseHelper(@Nullable Context context) {
        super(context, "IPPT.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createIPPTTableStatement = "CREATE TABLE " + IPPT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_IPPT_VOCATION + " TEXT, " + COLUMN_IPPT_AGE + " TEXT, " + COLUMN_IPPT_PUSHUPPOINTS + " TEXT, " + COLUMN_IPPT_SITUPPOINTS + ", " + COLUMN_IPPT_RUNPOINTS + " TEXT, " + COLUMN_IPPT_TOTALPOINTS + " TEXT, " + COLUMN_IPPT_STATUS + " TEXT)";

        db.execSQL(createIPPTTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOneData(IPPTModel ipptModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_IPPT_VOCATION, ipptModel.getVocation());
        cv.put(COLUMN_IPPT_AGE, ipptModel.getAge());
        cv.put(COLUMN_IPPT_PUSHUPPOINTS, ipptModel.getPushUpPoints());
        cv.put(COLUMN_IPPT_SITUPPOINTS, ipptModel.getSitUpPoints());
        cv.put(COLUMN_IPPT_RUNPOINTS, ipptModel.getRunPoints());
        cv.put(COLUMN_IPPT_TOTALPOINTS, ipptModel.getTotalPoints());
        cv.put(COLUMN_IPPT_STATUS, ipptModel.getStatus());

        long insert = db.insert(IPPT_TABLE, null, cv);
        if (insert == -1)
            return false;
        else
            return true;
    }

    public List<IPPTModel> getEveryIPPTData()
    {
        List<IPPTModel> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM " + IPPT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst())
        {
            // loop through the cursor (result set) and create new customer objects. Put them into the return list.
            do
            {
                int ipptID = cursor.getInt(0);
                String ipptVocation = cursor.getString(1);
                String ipptAge = cursor.getString(2);
                String ipptPushUpPoints = cursor.getString(3);
                String ipptSitUpPoints = cursor.getString(4);
                String ipptRunPoints = cursor.getString(5);
                String ipptTotalPoints = cursor.getString(6);
                String ipptStatus = cursor.getString(7);

                IPPTModel newData = new IPPTModel(ipptID, ipptVocation, ipptAge, ipptPushUpPoints, ipptSitUpPoints, ipptRunPoints, ipptTotalPoints, ipptStatus);
                returnList.add(newData);
            } while (cursor.moveToNext());
        }
        else
        {
            // failure. do not add anything to the list.
        }
        // close both the cursor and the db when done.
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean DeleteOneUser(IPPTModel ipptModel)
    {
        // find customerModel in the database. if it found, delete it and return true.
        // if it is not found, return false.

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM" + IPPT_TABLE + "WHERE" + COLUMN_ID + " = " + ipptModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }
        else {
            return false;
        }
    }
}
