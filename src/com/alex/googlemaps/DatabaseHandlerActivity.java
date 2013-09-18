package com.alex.googlemaps;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandlerActivity extends SQLiteOpenHelper
{

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	
	// Database Name
	private static final String DATABASE_NAME ="friendslocator";

	// Contacts table name
	private static final String TABLE_LOCATIONS = "flocation";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_LAT = "latitude";
	private static final String KEY_LON = "longitude";
	private static final String KEY_TIME = "time";


	public DatabaseHandlerActivity(Context context)
	{
		super(context, DATABASE_NAME, null,DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// TODO Auto-generated method stub


		//creating table values

		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_LOCATIONS + "("
		+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_LAT + " TEXT,"
		+ KEY_LON + " TEXT," + KEY_TIME + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	//Upgrading table
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);

		// Create tables again
		onCreate(db);

	}


	// Adding new contact
	public void addLocation(Locations_viewer location)
	{
		SQLiteDatabase db = this.getWritableDatabase();// making writable database

		ContentValues values = new ContentValues();
		values.put(KEY_LAT, location.getLat()); // Contact Name
		values.put(KEY_LON, location.getLong()); // Contact Phone Number
		values.put(KEY_TIME, location.getTime());

		// Inserting Row
		db.insert(TABLE_LOCATIONS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	public Locations_viewer getLocation(int id) 
	{
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_LOCATIONS, new String[] { KEY_ID,KEY_LAT, KEY_LON,KEY_TIME }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Locations_viewer contact = new Locations_viewer(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2),cursor.getString(3));
		// return contact
		return contact;
	}

	// Getting All Contacts
	public List<Locations_viewer> getAllLocations() {
		List<Locations_viewer> contactList = new ArrayList<Locations_viewer>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Locations_viewer location = new Locations_viewer();
				location.setID(Integer.parseInt(cursor.getString(0)));
				location.setLAt(cursor.getString(1));
				location.setLongi(cursor.getString(2));
				location.setTime(cursor.getString(3));

				// Adding contact to list
				contactList.add(location);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}


	// Getting contacts Count
	public int getLocationsCount() 
	{
		String countQuery = "SELECT  * FROM " + TABLE_LOCATIONS;

		SQLiteDatabase db = this.getReadableDatabase();//database get readable
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}


	// Updating single contact
	public int updateLocation(Locations_viewer locations_viewer)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_LAT, locations_viewer.getLat());
		values.put(KEY_LON, locations_viewer.getLong());
		values.put(KEY_TIME, locations_viewer.getTime());
		Log.d("Latitude",locations_viewer.getLat());
		Log.d("Longitude",locations_viewer.getLong());
		Log.d("Time", locations_viewer.getTime());

		// updating row
		return db.update(TABLE_LOCATIONS, values, KEY_ID + " = ?",new String[] { String.valueOf(locations_viewer.getID()) });
	}

	// Deleting single contact
	public void deleteLocation(Locations_viewer locations_viewer) {SQLiteDatabase db = this.getWritableDatabase();
	db.delete(TABLE_LOCATIONS, KEY_ID + " = ?",new String[] { String.valueOf(locations_viewer.getID()) });
	db.close();


	}
	public void deleteAll()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		db.delete(TABLE_LOCATIONS, null, null);
	}
	public void delete_byID(int id)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		 db.delete(TABLE_LOCATIONS, KEY_ID+"="+id, null);
		}



}