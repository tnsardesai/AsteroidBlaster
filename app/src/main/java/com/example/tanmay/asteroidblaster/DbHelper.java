package com.example.tanmay.asteroidblaster;

import android.content.ContentValues;
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
    private static final String SHOP_KEY_ID = "id";
    private static final String SHOP_ITEM = "item";
    private static final String SHOP_SELECTED = "selected";
    private static final String SHOP_BOUGHT = "bought";

    private static final String TABLE_COIN = "coin";
    private static final String COIN_ID = "id";
    private static final String COIN_AMOUNT = "coins";

    private static final String TABLE_SOUND = "sound";
    private static final String SOUND_ID = "id";
    private static final String SOUND_STATE = "state";

    private static final String SQL_CREATE_HIGH =
            "CREATE TABLE " + TABLE_HIGH + " (" +
                    HIGH_KEY_ID + " INTEGER PRIMARY KEY," +
                    HIGH_SCORE + " INTEGER," +
                    HIGH_NAME + " TEXT)";

    private static final String SQL_CREATE_SHOP =
            "CREATE TABLE " + TABLE_SHOP + " (" +
                    SHOP_KEY_ID + " INTEGER PRIMARY KEY," +
                    SHOP_ITEM + " TEXT," +
                    SHOP_SELECTED + " INTEGER," +
                    SHOP_BOUGHT + " INTEGER)";

    private static final String SQL_CREATE_COINS =
            "CREATE TABLE " + TABLE_COIN + " (" +
                    COIN_ID + " INTEGER PRIMARY KEY," +
                    COIN_AMOUNT + " INTEGER)";

    private static final String SQL_CREATE_SOUND =
            "CREATE TABLE " + TABLE_SOUND + " (" +
                    SOUND_ID + " INTEGER PRIMARY KEY," +
                    SOUND_STATE + " INTEGER)";

    private static final String SQL_DELETE_HIGH =
            "DROP TABLE IF EXISTS " + TABLE_HIGH;

    private static final String SQL_DELETE_SHOP =
            "DROP TABLE IF EXISTS " + TABLE_SHOP;

    private static final String SQL_DELETE_COIN =
            "DROP TABLE IF EXISTS " + TABLE_COIN;

    private static final String SQL_DELETE_SOUND =
            "DROP TABLE IF EXISTS " + TABLE_SOUND;

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SHOP);
        db.execSQL(SQL_CREATE_HIGH);
        db.execSQL(SQL_CREATE_COINS);
        db.execSQL(SQL_CREATE_SOUND);

        ContentValues coins = new ContentValues();
        coins.put(COIN_AMOUNT, 0);
        db.insert(TABLE_COIN, null, coins);

        ContentValues sound = new ContentValues();
        sound.put(SOUND_STATE, 1);
        db.insert(TABLE_SOUND, null, sound);

        ContentValues values1 = new ContentValues();
        values1.put(SHOP_ITEM, "default");
        values1.put(SHOP_SELECTED, 1);
        values1.put(SHOP_BOUGHT,1);
        db.insert(TABLE_SHOP, null, values1);

        ContentValues values2 = new ContentValues();
        values2.put(SHOP_ITEM, "cookiemonster");
        values2.put(SHOP_SELECTED, 0);
        values2.put(SHOP_BOUGHT,0);
        db.insert(TABLE_SHOP, null, values2);

        ContentValues values3 = new ContentValues();
        values3.put(SHOP_ITEM, "breakingbad");
        values3.put(SHOP_SELECTED, 0);
        values3.put(SHOP_BOUGHT,0);
        db.insert(TABLE_SHOP, null, values3);

        ContentValues values4 = new ContentValues();
        values4.put(SHOP_ITEM, "starwars");
        values4.put(SHOP_SELECTED, 0);
        values4.put(SHOP_BOUGHT,0);
        db.insert(TABLE_SHOP, null, values4);

        ContentValues values5 = new ContentValues();
        values5.put(SHOP_ITEM, "khalili");
        values5.put(SHOP_SELECTED, 0);
        values5.put(SHOP_BOUGHT,0);
        db.insert(TABLE_SHOP, null, values5);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_HIGH);
        db.execSQL(SQL_DELETE_SHOP);
        db.execSQL(SQL_DELETE_COIN);
        db.execSQL(SQL_DELETE_SOUND);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
