package com.example.vitalis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    //public static final String DATABASE_NAME="Employees";
    public static final String DATABASE_NAME="lODGING";
    public static final String TABLE_NAME="rOOMS";
    public static final String COL_1="ID";
    public static final String COL_2="Name";
    public static final String COL_3="Phone";
    public static final String COL_4="Room";
    public static final String COL_5="Fees";


    public DatabaseHelper(Context context){

        super( context, DATABASE_NAME, null,   1 );
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Phone INTEGER, Room TEXT, Fees INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
    public boolean insertData(String Name, String Phone, String Room, String Fees){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues ();
        contentValues.put(COL_2, Name);
        contentValues.put(COL_3, Phone);
        contentValues.put(COL_4, Room);
        contentValues.put(COL_5, Fees);

        long x= db.insert(TABLE_NAME, null, contentValues);
        if(x==-1)
            return  false;
        else
            return  true;

    }
    public Cursor getAllData(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
    public boolean updateData(String ID, String Name, String Phone, String Room, String Fees){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues ();
        contentValues.put(COL_1, ID);
        contentValues.put(COL_2, Name);
        contentValues.put(COL_3, Phone);
        contentValues.put(COL_4, Room);
        contentValues.put(COL_5, Fees);
        db.update(TABLE_NAME,  contentValues, "ID=?", new String[] {ID});
        return  true;
    }
    public Integer deleteData(String ID){
        SQLiteDatabase db =this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID=?", new String[] {ID});
    }

}
