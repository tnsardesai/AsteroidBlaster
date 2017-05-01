package com.example.tanmay.asteroidblaster;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tanmay on 4/26/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "AsteroidBlaster.db";

    private static final String TABLE_HIGH = "highscores";
    private static final String HIGH_KEY_ID = "id";
    private static final String HIGH_SCORE = "score";
    private static final String HIGH_NAME = "name";

    private static final String TABLE_SHOP = "shop";
    private static final String SHOP_KEY_ID = "ID";
    private static final String SHOP_ITEM = "item";
    private static final String SHOP_SELECTED = "selected";

    private static final String SQL_CREATE_HIGH =
            "CREATE TABLE " + TABLE_HIGH + " (" +
                    HIGH_KEY_ID + " INTEGER PRIMARY KEY," +
                    HIGH_SCORE + " INTEGER," +
                    HIGH_NAME + " TEXT)";

    private static final String SQL_CREATE_SHOP =
            "CREATE TABLE " + TABLE_SHOP + " (" +
                    SHOP_KEY_ID + " INTEGER PRIMARY KEY," +
                    SHOP_ITEM + " TEXT," +
                    SHOP_SELECTED + " INTEGER)";

    private static final String SQL_DELETE_HIGH =
            "DROP TABLE IF EXISTS " + TABLE_HIGH;

    private static final String SQL_DELETE_SHOP =
            "DROP TABLE IF EXISTS " + TABLE_SHOP;

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HIGH);
        db.execSQL(SQL_CREATE_SHOP);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_HIGH);
        db.execSQL(SQL_DELETE_SHOP);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
