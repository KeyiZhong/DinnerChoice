package com.example.dinnerchoice;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Iterator;
import java.util.Random;

import com.example.dinnerchoice.datastructures.concrete.KVPair;
import com.example.dinnerchoice.datastructures.concrete.dictionaries.ArrayDictionary;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "restuarant.db";
    public static final String TABLE_NAME = "restuarant_table";
    public static final String COL_1 = "restuarant_name";
    public static final String COL_2 = "weight";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (restuarant_name STRING PRIMARY KEY, weight INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String weight) {
        if(Integer.valueOf(weight) < 7 && Integer.valueOf(weight) > 0) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, name);
            contentValues.put(COL_2, weight);
            long result = db.insert(TABLE_NAME, null, contentValues);
            return result != -1;
        }
        return false;
    }

    public Cursor retrieve() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

}
