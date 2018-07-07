package com.example.anurag.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.security.acl.Group;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="Student.db";
    public static final String TABLE_NAME ="student_table";
    public static final String COL_1="ID";
    public static final String COL_2="GROUP_TYPE";
    public static final String COL_3="MATCH_NO";
    public static final String COL_4="TEAM1";
    public static final String COL_5="TEAM1_IMAGE";
    public static final String COL_6="TEAM2";
    public static final String COL_7="TEAM2_IMAGE";

    public DataBaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,GROUP_TYPE TEXT, MATCH_NO INTEGER,TEAM1 TEXT,TEAM1_IMAGE TEXT,TEAM2 TEXT ,TEAM2_IMAGE TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String group, Integer MATCH_NO,String TEAM1,String TEAM1_IMAGE,String TEAM2,String TEAM2_IMAGE ){
        Log.d("DETAILS", group + " " + MATCH_NO + " " + TEAM1  + " " + TEAM2 + " ");
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, group);
        contentValues.put(COL_3, MATCH_NO);
        contentValues.put(COL_4, TEAM1);
        contentValues.put(COL_5, TEAM1_IMAGE);
        contentValues.put(COL_6, TEAM2);
        contentValues.put(COL_7, TEAM2_IMAGE);
        long result =db.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        } else{
            return true;
        }
    }

    public Cursor getalldata(){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public Integer deletedata(String id){
        SQLiteDatabase db =this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID=?",new String[] {id});


    }

    public boolean updatedata(String id ,String group, Integer MATCH_NO,String TEAM1,String TEAM1_IMAGE,String TEAM2,String TEAM2_IMAGE ){
        Log.d("DETAILS", group + " " + MATCH_NO + " " + TEAM1  + " " + TEAM2 + " ");

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, group);
        contentValues.put(COL_3, MATCH_NO);
        contentValues.put(COL_4, TEAM1);
        contentValues.put(COL_5, TEAM1_IMAGE);
        contentValues.put(COL_6, TEAM2);
        contentValues.put(COL_7, TEAM2_IMAGE);
        db.update(TABLE_NAME,contentValues,"ID=" + id,null);
        return true;
    }
}
