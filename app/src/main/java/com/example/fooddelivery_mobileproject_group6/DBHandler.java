package com.example.fooddelivery_mobileproject_group6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "foodorderdb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "usertable";
    private static final String FNAME_COL = "fname";
    private static final String LNAME_COL = "lname";
    private static final String EMAIL_COL = "email";
    private static final String PASS_COL = "password";


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
//onUpgrade method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
       onCreate(db);
    }
}
