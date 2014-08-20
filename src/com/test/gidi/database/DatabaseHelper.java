package com.test.gidi.database;

import java.util.ArrayList;
import java.util.List;

import com.test.gidi.Event;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
    private static final int DATABASE_VERSION = 1;
 
    private static final String DATABASE_NAME = "Events";
 
    // events table name
    private static final String TABLE_EVENTS = "events";
 
    // events Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADD = "address";
    private static final String KEY_DATE = "date";
    private static final String KEY_GPS = "gps";
    private static final String KEY_DESC = "description";
    private static final String KEY_TYPE = "type";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_ADD + " TEXT," + KEY_DATE + " DATETIME," + KEY_GPS + " TEXT," + KEY_DESC + " TEXT," + KEY_TYPE + " TEXT"  + ")";
		
        db.execSQL(CREATE_EVENT_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
		onCreate(db);
	}
	
	
	
	// Adding new event
	public void addEvent(Event event) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_NAME, event.getName());
	    values.put(KEY_ADD, event.getAddress());
	    values.put(KEY_DATE, event.getDate()+ " "+event.getTime());
	    values.put(KEY_DESC, event.getDescription());
	    values.put(KEY_GPS, event.getGps());
	    values.put(KEY_TYPE, event.getType());
	    
	    
	 
	    // Inserting Row
	    db.insert(TABLE_EVENTS, null, values);
	    db.close(); // Closing database connection
	}
	 
	// Getting single event
	public Event getEvent(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		 
	    Cursor cursor = db.query(TABLE_EVENTS, new String[] { KEY_ID,
	            KEY_NAME, KEY_ADD,KEY_DATE,KEY_DESC,KEY_TYPE,KEY_GPS }, KEY_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Event event = new Event();
	    event.setName(cursor.getString(0));
	    event.setAddress(cursor.getString(1));
	    event.setDate(cursor.getString(2));
	    event.setDescription(cursor.getString(3));
	    event.setType(cursor.getString(4));
	    event.setGps(cursor.getString(5));
	    
	    return event;
	}
	 
	// Getting All events
	public List<Event> getAllEvents() {
		List<Event> eventList = new ArrayList<Event>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_EVENTS + " ORDER BY date("+KEY_DATE+")";
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    Log.v("cursor", cursor.toString());
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	Event event = new Event();
	        	event.setId(cursor.getString(0));
	        	 event.setName(cursor.getString(1));
	     	    event.setAddress(cursor.getString(2));
	     	    event.setDate(cursor.getString(3));
	     	    event.setDescription(cursor.getString(5));
	     	    event.setType(cursor.getString(6));
	     	    event.setGps(cursor.getString(4));
	     	   eventList.add(event);
	        } while (cursor.moveToNext());
	    }
	 
	    // return event list
	    return eventList;
	}
	 
	// Getting events Count
	public int getEventsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
	}
	// Updating single event
	public int updateEvent(Event event) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_NAME, event.getName());
	    values.put(KEY_ADD, event.getAddress());
	    values.put(KEY_DATE, event.getDate());
	    values.put(KEY_GPS, event.getGps());
	    values.put(KEY_DESC, event.getDescription());
	    values.put(KEY_TYPE, event.getType());
	    
	 
	    // updating row
	    return db.update(TABLE_EVENTS, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(event.getId()) });
	}
	 
	// Deleting single event
	public void deleteEvent(Event event) {
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_EVENTS, KEY_ID + " = ?",
	            new String[] { String.valueOf(event.getId()) });
	    db.close();
	}

}
