package com.example.nsapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EventDataBaseHelper extends SQLiteOpenHelper {


    public static final String COLUMN_EVENT_TABLE = "EVENT_TABLE";
    public static final String COLUMN_EVENT_NAME = "EVENT_NAME";
    public static final String COLUMN_EVENT_NOTES = "EVENT_NOTES";
    public static final String COLUMN_EVENT_DATE = "EVENT_DATE";
    public static final String COLUMN_EVENT_TIME = "EVENT_TIME";
    public static final String COLUMN_EVENT_ISPRIORITIZED = "EVENT_ISPRIORITIZED";
    public static final String COLUMN_ID = "ID";

    public EventDataBaseHelper(@Nullable Context context) {
        super(context, "event.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createEventTableStatement = "CREATE TABLE " + COLUMN_EVENT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EVENT_NAME + " TEXT, " + COLUMN_EVENT_NOTES + " TEXT, " + COLUMN_EVENT_DATE + " TEXT, " + COLUMN_EVENT_TIME + " TEXT, " + COLUMN_EVENT_ISPRIORITIZED + " BOOL)";

        db.execSQL(createEventTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " +  COLUMN_EVENT_TABLE);

        // Create tables again
        onCreate(db);
    }

    public boolean addOneEvent(EventModel eventModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EVENT_NAME, eventModel.getEvent());
        cv.put(COLUMN_EVENT_NOTES, eventModel.getNotes());
        cv.put(COLUMN_EVENT_DATE, eventModel.getDate());
        cv.put(COLUMN_EVENT_TIME, eventModel.getTime());
        cv.put(COLUMN_EVENT_ISPRIORITIZED, eventModel.isPrioritized());

        long insert = db.insert(COLUMN_EVENT_TABLE, null, cv);
        if (insert == -1)
        {
            return false;
        }
        else
            return true;
    }

    public boolean DeleteOneEvent(EventModel eventModel)
    {
        // find eventModel in the database. if it found, delete it and return true.
        // if it is not found, return false.

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM" + COLUMN_EVENT_TABLE + "WHERE" + COLUMN_ID + " = " + eventModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }
        else {
            return false;
        }
    }

    public List<EventModel> getEveryEvent()
    {
        List<EventModel> returnList = new ArrayList<>();

        // get data from the database

        String queryString = "SELECT * FROM " + COLUMN_EVENT_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            // loop through the cursor (result set) and create new customer objects. Put them into the return list.
            do {
                int eventID = cursor.getInt(0);
                String eventName = cursor.getString(1);
                String eventNote = cursor.getString(2);
                String eventDate = cursor.getString(3);
                String eventTime = cursor.getString(4);
                boolean event_isPrioritized = cursor.getInt(5) == 1 ? true: false;


                EventModel newEvent = new EventModel(eventID, eventName, eventNote, eventDate, eventTime, event_isPrioritized);
                returnList.add(newEvent);
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


}
