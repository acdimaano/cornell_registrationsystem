package com.genericregistrationsystem;
/*
 * SQLite Database Version Controllers*/
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GRSDBLoader extends SQLiteOpenHelper {

	// Database Name
	private static final String DATABASE_NAME = "genericregistrationsystem.db";
	// Master Tables
	private String DB_USER = "tbuser";
    private String DB_CUSTOMER = "tbcustomerdetails";

	// Database Version
	private static final int DATABASE_VERSION = 1;
	private static final String TAG = "GRSDBLoader class";

	public GRSDBLoader(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// this.myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase mydb) {
		try {
			Log.v(TAG, "onCreate Call");
			mydb.execSQL("create table if not exists "
					+ DB_USER
					+ "(user_id integer primary key autoincrement, oid text not null, first_name text not null, last_name text not null, middle_name text, specialty text, room_no text, time_avail text, days_avail text);");

            mydb.execSQL("create table if not exists "
                    + DB_CUSTOMER
                    + "(customer_id integer primary key autoincrement, oid text not null, first_name text not null, last_name text not null, address text, phone text, nhi text, gender text);");

            Log.i(DB_USER, " Created!");
		} catch (Exception e) {
            Log.e("DB Error ", e.getMessage());
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables

	}

}
