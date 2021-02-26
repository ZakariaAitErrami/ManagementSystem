package com.example.managementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context,"facturation.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE loginadmin(username TEXT primary key, password TEXT);");
        ContentValues content = new ContentValues();
        content.put("username","admin");
        content.put("password","123este456");
        long res= db.insert("loginadmin",null,content);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS loginadmin");
    }
    public Boolean checkusername(String username){
        //SQLiteDatabase myDB = this.getWritableDatabase();
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM loginadmin WHERE username = ?",new String[] {username});
        if(cursor.getCount()>0){
            return true;
        }
        else
            return false;
    }
    public Boolean checkusernamePassword(String username,String password){
       // SQLiteDatabase myDB = this.getWritableDatabase();
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM loginadmin WHERE username = ? AND password = ?",new String[] {username,password});
        if(cursor.getCount()>0){
            return true;
        }
        else
            return false;
    }
}
