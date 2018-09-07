package com.gamecodeschool.databaseminiapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;

public class DataManager {
    private SQLiteDatabase db ;

    /*
Next we have a public static final string for
each row/table that we need to refer to both
inside and outside this class
*/
    public static final String TABLE_ROW_ID = "_id";
    public static final String TABLE_ROW_NAME = "name";
    public static final String TABLE_ROW_AGE = "age";

    /*
Next we have a private static final strings for
each row/table that we need to refer to just
inside this class
*/
    private static final String DB_NAME = "address_book_db" ;
    private static final int DB_VERSION = 1 ;
    private static final String TABLE_N_AND_A = "names and addresses";

    public DataManager (Context context){
        // Create an instance of our internal
//CustomSQLiteOpenHelper class
        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
        // Get a writable database
        db = helper.getWritableDatabase();

    }
    // Here are all our helper methods
    // Insert a record
    public void insert(String name, String age){
// Add all the details to the table

        try {
            // Add all the details to the table
            String query = "INSERT INTO " + TABLE_N_AND_A + " (" +
                    TABLE_ROW_NAME + ", " +
                    TABLE_ROW_AGE + ") " +
                    "VALUES (" +
                    "'" + name + "'" + ", " +
                    "'" + age + "'" +
                    ")";

            Log.i("insert() = ", query);
            db.execSQL(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // Delete a record
    public void delete(String name){
        try {

/// Delete the details from the table if already exists
            String query = "DELETE FROM " + TABLE_N_AND_A +
                    " WHERE " + TABLE_ROW_NAME +
                    " = '" + name + "';";
            Log.i("delete() = ", query);
            db.execSQL(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // Get all the records
    public Cursor selectAll()  throws CursorIndexOutOfBoundsException { try {


        Cursor c = db.rawQuery("SELECT *" +" from " +
                TABLE_N_AND_A, null);
        return c;

    }finally {

    }
    }
    // Find a specific record
    public Cursor searchName(String name)throws CursorIndexOutOfBoundsException {

try {


    String query = "SELECT " +
            TABLE_ROW_ID + ", " +
            TABLE_ROW_NAME +
            ", " + TABLE_ROW_AGE +
            " from " +
            TABLE_N_AND_A + " WHERE " +
            TABLE_ROW_NAME + " = '" + name + "';";
    Log.i("searchName() = ", query);
    Cursor c = db.rawQuery(query, null);
    return c;

}finally {

}
    }
    // This class i s created when our DataManager is initialized
    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper{

        public CustomSQLiteOpenHelper(Context context) {
            super(context,DB_NAME ,null,DB_VERSION);
        }

        // This method only runs the first time the database is created
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {

// Create a table for photos and all their details
                String newTableQueryString = "create table "
                        + TABLE_N_AND_A + " ("
                        + TABLE_ROW_ID
                        + " integer primary key autoincrement not null,"
                        + TABLE_ROW_NAME
                        + " text not null,"
                        + TABLE_ROW_AGE
                        + " text not null);";
                db.execSQL(newTableQueryString);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        // This method only runs when we increment DB_VERSION
// We will look at this in chapter 26
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}