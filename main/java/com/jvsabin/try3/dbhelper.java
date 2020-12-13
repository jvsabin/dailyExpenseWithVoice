package com.jvsabin.try3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbhelper extends SQLiteOpenHelper {

    public  static  final  String DATABASE_NAME = "myDB.db";
    public  static  final  String TABLE_NAME  = "expense";
    public  static  final  String TABLE_NAME1  = "balance";



    public  static  final  String COL_1  = "ID";
    public  static  final  String COL_2  = "TYPE";
    public  static  final  String COL_3  = "DESCR";
    public  static  final  String COL_4  = "AMOUNT";
    public  static  final  String COL_5  = "BALANCE";


    public dbhelper(Context context) {
        super(context, DATABASE_NAME , null , 1);

        SQLiteDatabase database = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,TYPE TEXT ,DESCR TEXT, AMOUNT INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE balance (ID INTEGER PRIMARY KEY AUTOINCREMENT, BALANCE INTEGER)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME1);

        //sqLiteDatabase.close();
        onCreate(sqLiteDatabase);
    }
    public boolean insertData(String type, String desc, int amount){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_2,type);
        cv.put(COL_3,desc);
        cv.put(COL_4,amount);
        //cv.put(COL_5,balance);
        long result= db.insert(TABLE_NAME,null,cv);
        db.close();
        if(result==-1){
            return false;
        }
        else {
            return true;
        }

    }

    public boolean insertDataB(int amount){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();


        cv.put(COL_5,amount);
        long result= db.insert(TABLE_NAME1,null,cv);
        db.close();
        if(result==-1){
            return false;
        }
        else {
            return true;
        }

    }




    public Cursor viewData(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query= "Select * from expense";
        Cursor cursor=db.rawQuery(query, null);

        return cursor;
    }

    public Cursor viewDataB(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query= "Select * from balance";
        Cursor cursor=db.rawQuery(query, null);

        return cursor;
    }


    public String viewData2(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query= "Select * from expense";
        Cursor cursor=db.rawQuery(query, null);
        String a="";
        if (cursor.moveToFirst()) {
            do {
                //a += cursor.getString(0);// each item will be stored
                //return a;	// returning the required value
                a += cursor.getString(1);
                a += cursor.getString(2);
                a += cursor.getString(3);
                a+="  ";
            }while(cursor.moveToNext());
        }
        return a;
    }

}