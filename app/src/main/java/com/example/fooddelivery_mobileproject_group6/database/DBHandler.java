package com.example.fooddelivery_mobileproject_group6.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "foodorderdb";
    private static final int DB_VERSION = 2;
    private static final String TABLE_NAME = "usertable";
    private static final String FNAME_COL = "fname";
    private static final String LNAME_COL = "lname";
    private static final String EMAIL_COL = "email";
    private static final String PASS_COL = "password";

    private static final String LOC_TABLE_NAME = "locationtable";
    private static final String BRANCH_NAME_COL = "branchname";
    private static final String BRANCH_ID_COL = "id";
    private static final String LATITUDE_COL = "latitude";
    private static final String LONGITUDE_COL = "longitude";


   public DBHandler(Context context){
       super(context,DB_NAME, null, DB_VERSION);
   }

   /*Creates the table with a column for email, first name, last name, and password*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + EMAIL_COL + " TEXT PRIMARY KEY, "
                + FNAME_COL + " TEXT,"
                + LNAME_COL + " TEXT,"
                + PASS_COL + " TEXT)";

        db.execSQL(query);

        String locTableQuery = "CREATE TABLE " + LOC_TABLE_NAME + " ("
                + BRANCH_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BRANCH_NAME_COL + " TEXT, "
                + LATITUDE_COL + " REAL,"
                + LONGITUDE_COL + " REAL)";

        db.execSQL(locTableQuery);
        
    }
    /*Method for inserting the data*/
    public Boolean insertData(String email, String fname, String lname, String password){
       SQLiteDatabase mydb = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();

       /*Uses content values to put the values to their respective columns in the table*/
       contentValues.put(EMAIL_COL, email);
       contentValues.put(FNAME_COL, fname);
       contentValues.put(LNAME_COL, lname);
       contentValues.put(PASS_COL, password);

       long result = mydb.insert(TABLE_NAME, null, contentValues);

       if(result == -1){
           return false;
       } else {
           return true;
       }
    }

    /*Method to check if the email entered already exists in the database or not*/
    public Boolean checkEmail(String email){
       SQLiteDatabase mydb = this.getWritableDatabase();
       Cursor cursor = mydb.rawQuery("Select * from " + TABLE_NAME + " where " + EMAIL_COL + " =?", new String[] {email});
       if(cursor.getCount() > 0){
           cursor.close();
           mydb.close();
           return true;
       } else {
           cursor.close();
           mydb.close();
           return false;
       }
    }

    /*Method to check if the entered password already exists in the database or not*/
    public Boolean checkPassword(String email, String password){
       SQLiteDatabase mydb = this.getWritableDatabase();
       Cursor cursor = mydb.rawQuery("Select * from " + TABLE_NAME + " where " + EMAIL_COL + " =?" + " and " + PASS_COL + " =?", new String[] {email, password});
        if(cursor.getCount() > 0){
            cursor.close();
            mydb.close();
            return true;
        } else {
            cursor.close();
            mydb.close();
            return false;
        }
    }
    //Method for inserting location data
    public Boolean insertLocationData(String branchName, double latitude, double longitude){
        SQLiteDatabase mydb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(BRANCH_NAME_COL, branchName);
        contentValues.put(LATITUDE_COL, latitude);
        contentValues.put(LONGITUDE_COL, longitude);

        long result = mydb.insert(LOC_TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

     public List<RestaurantMod> getAllLocations(){
        List<RestaurantMod> locations = new ArrayList<>();

        String selectQuery = "SELECT * FROM " +
                LOC_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){

            do {
                RestaurantMod location = new RestaurantMod();
                location.setBranchName(cursor.getString(1));
                location.setLatitude(cursor.getDouble(2));
                location.setLongitude(cursor.getDouble(3));

                locations.add(location);
            } while (cursor.moveToNext());
        }

        return locations;
    }
//onUpgrade method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LOC_TABLE_NAME);
       onCreate(db);
    }
}
