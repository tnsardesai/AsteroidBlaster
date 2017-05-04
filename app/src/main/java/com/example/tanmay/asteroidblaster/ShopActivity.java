package com.example.tanmay.asteroidblaster;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {


    public DbHelper mDbHelper = new DbHelper(this);


    private static final String TABLE_SHOP = "shop";
    private static final String SHOP_KEY_ID = "ID";
    private static final String SHOP_ITEM = "item";
    private static final String SHOP_SELECTED = "selected";
    private static final String SHOP_BOUGHT = "bought";


    private static final String TABLE_COIN = "coin";
    private static final String COIN_ID = "id";
    private static final String COIN_AMOUNT = "coins";

    ArrayList<Button> item = new ArrayList<Button>(5);
    TextView coins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);


        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Grinched.ttf");


        item.add((Button) findViewById(R.id.default_button));
        item.add((Button) findViewById(R.id.cookie_button));
        item.add((Button) findViewById(R.id.breakingbad_button));
        item.add((Button) findViewById(R.id.starwars_button));
        item.add((Button) findViewById(R.id.khalili_button));

        coins = (TextView) findViewById(R.id.coins);

        for (int i=0; i<5;i++){
            item.get(i).setTypeface(custom_font);
        }

        coins.setTypeface(custom_font);

        set();
    }

    public void set(){

        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        String[] projection = {
                SHOP_KEY_ID,
                SHOP_ITEM,
                SHOP_SELECTED,
                SHOP_BOUGHT
        };

        Cursor cursor = db.query(
                TABLE_SHOP,
                projection,
                null,
                null,
                null,
                null,
                null
        );



        int i=0;
        while (cursor.moveToNext()){
            if(cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_BOUGHT)) == 1){
                item.get(i).setText("SELECT");
            }

            if (cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_SELECTED)) == 1 && cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_BOUGHT)) == 1){
                item.get(i).setBackgroundColor(Color.GREEN);

            }
            else if (cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_SELECTED)) == 0 && cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_BOUGHT)) == 1){
                item.get(i).setBackgroundColor(Color.RED);
            }
            else if (cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_SELECTED)) == 0 && cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_BOUGHT)) == 0){
                item.get(i).setBackgroundColor(Color.YELLOW);
            }

            Log.d("SHOP","name: " + cursor.getString(cursor.getColumnIndexOrThrow(SHOP_ITEM))
                    + "selected: " + String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_SELECTED)))
                    + "bought: " + String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_BOUGHT))));

            i++;
        }
        cursor.close();

        String[] projection1 = {
                COIN_AMOUNT
        };

        Cursor cursor1 = db.query(
                TABLE_COIN,
                projection1,
                null,
                null,
                null,
                null,
                null
        );

        cursor1.moveToNext();
        coins.setText("COINS: "+ String.valueOf(cursor1.getInt(cursor1.getColumnIndexOrThrow(COIN_AMOUNT))));

    }

    public void openMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void clickDefault(View view){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues val1 = new ContentValues();
        val1.put(SHOP_SELECTED,1);
        db.update(TABLE_SHOP,val1,SHOP_KEY_ID+"=1",null);

        ContentValues val2 = new ContentValues();
        val2.put(SHOP_SELECTED,0);
        db.update(TABLE_SHOP,val2,SHOP_KEY_ID+"=2",null);

        ContentValues val3 = new ContentValues();
        val3.put(SHOP_SELECTED,0);
        db.update(TABLE_SHOP,val3,SHOP_KEY_ID+"=3",null);

        ContentValues val4 = new ContentValues();
        val4.put(SHOP_SELECTED,0);
        db.update(TABLE_SHOP,val4,SHOP_KEY_ID+"=4",null);

        ContentValues val5 = new ContentValues();
        val5.put(SHOP_SELECTED,0);
        db.update(TABLE_SHOP,val5,SHOP_KEY_ID+"=5",null);

        set();
    }

    public void clickCookie(View view){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String[] projection = {
                SHOP_KEY_ID,
                SHOP_ITEM,
                SHOP_SELECTED,
                SHOP_BOUGHT
        };

        String selection = SHOP_KEY_ID + " = ?";
        String[] selectionArgs = {"2"};

        Cursor cursor = db.query(
                TABLE_SHOP,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        cursor.moveToNext();
        int selected = (int)cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_SELECTED));
        int bought = (int)cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_BOUGHT));

        if ( bought == 0){

            String[] projection1 = {
                    COIN_AMOUNT
            };

            Cursor cursor1 = db.query(
                    TABLE_COIN,
                    projection1,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            cursor1.moveToNext();
            int coins = cursor1.getInt(cursor1.getColumnIndexOrThrow(COIN_AMOUNT));

            if (coins >= 10){
                ContentValues coin_value = new ContentValues();
                coin_value.put(COIN_AMOUNT, coins-10);
                db.update(TABLE_COIN,coin_value,null,null);

                ContentValues val1 = new ContentValues();
                val1.put(SHOP_SELECTED,0);
                db.update(TABLE_SHOP,val1,SHOP_KEY_ID+"=1",null);

                ContentValues val2 = new ContentValues();
                val2.put(SHOP_SELECTED,1);
                val2.put(SHOP_BOUGHT,1);
                db.update(TABLE_SHOP,val2,SHOP_KEY_ID+"=2",null);

                ContentValues val3 = new ContentValues();
                val3.put(SHOP_SELECTED,0);
                db.update(TABLE_SHOP,val3,SHOP_KEY_ID+"=3",null);

                ContentValues val4 = new ContentValues();
                val4.put(SHOP_SELECTED,0);
                db.update(TABLE_SHOP,val4,SHOP_KEY_ID+"=4",null);

                ContentValues val5 = new ContentValues();
                val5.put(SHOP_SELECTED,0);
                db.update(TABLE_SHOP,val5,SHOP_KEY_ID+"=5",null);
            }

            cursor1.close();
        }
        else if (selected == 0 && bought == 1){
            Log.d("SHOP","entered");
            ContentValues val1 = new ContentValues();
            val1.put(SHOP_SELECTED,0);
            db.update(TABLE_SHOP,val1,SHOP_KEY_ID+"=1",null);

            ContentValues val2 = new ContentValues();
            val2.put(SHOP_SELECTED,1);
            db.update(TABLE_SHOP,val2,SHOP_KEY_ID+"=2",null);

            ContentValues val3 = new ContentValues();
            val3.put(SHOP_SELECTED,0);
            db.update(TABLE_SHOP,val3,SHOP_KEY_ID+"=3",null);

            ContentValues val4 = new ContentValues();
            val4.put(SHOP_SELECTED,0);
            db.update(TABLE_SHOP,val4,SHOP_KEY_ID+"=4",null);

            ContentValues val5 = new ContentValues();
            val5.put(SHOP_SELECTED,0);
            db.update(TABLE_SHOP,val5,SHOP_KEY_ID+"=5",null);
        }

        set();
    }

    public void clickBB(View view){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String[] projection = {
                SHOP_KEY_ID,
                SHOP_ITEM,
                SHOP_SELECTED,
                SHOP_BOUGHT
        };

        String selection = SHOP_KEY_ID + " = ?";
        String[] selectionArgs = {"3"};

        Cursor cursor = db.query(
                TABLE_SHOP,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        cursor.moveToNext();
        int selected = (int)cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_SELECTED));
        int bought = (int)cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_BOUGHT));

        if ( bought == 0){

            String[] projection1 = {
                    COIN_AMOUNT
            };

            Cursor cursor1 = db.query(
                    TABLE_COIN,
                    projection1,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            cursor1.moveToNext();
            int coins = cursor1.getInt(cursor1.getColumnIndexOrThrow(COIN_AMOUNT));

            if (coins >= 20){
                ContentValues coin_value = new ContentValues();
                coin_value.put(COIN_AMOUNT, coins-20);
                db.update(TABLE_COIN,coin_value,null,null);

                ContentValues val1 = new ContentValues();
                val1.put(SHOP_SELECTED,0);
                db.update(TABLE_SHOP,val1,SHOP_KEY_ID+"=1",null);

                ContentValues val2 = new ContentValues();
                val2.put(SHOP_SELECTED,0);
                db.update(TABLE_SHOP,val2,SHOP_KEY_ID+"=2",null);

                ContentValues val3 = new ContentValues();
                val3.put(SHOP_SELECTED,1);
                val3.put(SHOP_BOUGHT,1);
                db.update(TABLE_SHOP,val3,SHOP_KEY_ID+"=3",null);

                ContentValues val4 = new ContentValues();
                val4.put(SHOP_SELECTED,0);
                db.update(TABLE_SHOP,val4,SHOP_KEY_ID+"=4",null);

                ContentValues val5 = new ContentValues();
                val5.put(SHOP_SELECTED,0);
                db.update(TABLE_SHOP,val5,SHOP_KEY_ID+"=5",null);
            }

            cursor1.close();
        }
        else if (selected == 0 && bought == 1){
            Log.d("SHOP","entered");
            ContentValues val1 = new ContentValues();
            val1.put(SHOP_SELECTED,0);
            db.update(TABLE_SHOP,val1,SHOP_KEY_ID+"=1",null);

            ContentValues val2 = new ContentValues();
            val2.put(SHOP_SELECTED,0);
            db.update(TABLE_SHOP,val2,SHOP_KEY_ID+"=2",null);

            ContentValues val3 = new ContentValues();
            val3.put(SHOP_SELECTED,1);
            db.update(TABLE_SHOP,val3,SHOP_KEY_ID+"=3",null);

            ContentValues val4 = new ContentValues();
            val4.put(SHOP_SELECTED,0);
            db.update(TABLE_SHOP,val4,SHOP_KEY_ID+"=4",null);

            ContentValues val5 = new ContentValues();
            val5.put(SHOP_SELECTED,0);
            db.update(TABLE_SHOP,val5,SHOP_KEY_ID+"=5",null);
        }

        set();
    }

    public void clickSW(View view){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String[] projection = {
                SHOP_KEY_ID,
                SHOP_ITEM,
                SHOP_SELECTED,
                SHOP_BOUGHT
        };

        String selection = SHOP_KEY_ID + " = ?";
        String[] selectionArgs = {"4"};

        Cursor cursor = db.query(
                TABLE_SHOP,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        cursor.moveToNext();
        int selected = (int)cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_SELECTED));
        int bought = (int)cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_BOUGHT));

        if ( bought == 0){

            String[] projection1 = {
                    COIN_AMOUNT
            };

            Cursor cursor1 = db.query(
                    TABLE_COIN,
                    projection1,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            cursor1.moveToNext();
            int coins = cursor1.getInt(cursor1.getColumnIndexOrThrow(COIN_AMOUNT));

            if (coins >= 30){
                ContentValues coin_value = new ContentValues();
                coin_value.put(COIN_AMOUNT, coins-30);
                db.update(TABLE_COIN,coin_value,null,null);

                ContentValues val1 = new ContentValues();
                val1.put(SHOP_SELECTED,0);
                db.update(TABLE_SHOP,val1,SHOP_KEY_ID+"=1",null);

                ContentValues val2 = new ContentValues();
                val2.put(SHOP_SELECTED,0);
                db.update(TABLE_SHOP,val2,SHOP_KEY_ID+"=2",null);

                ContentValues val3 = new ContentValues();
                val3.put(SHOP_SELECTED,0);
                db.update(TABLE_SHOP,val3,SHOP_KEY_ID+"=3",null);

                ContentValues val4 = new ContentValues();
                val4.put(SHOP_SELECTED,1);
                val4.put(SHOP_BOUGHT,1);
                db.update(TABLE_SHOP,val4,SHOP_KEY_ID+"=4",null);

                ContentValues val5 = new ContentValues();
                val5.put(SHOP_SELECTED,0);
                db.update(TABLE_SHOP,val5,SHOP_KEY_ID+"=5",null);
            }

            cursor1.close();
        }
        else if (selected == 0 && bought == 1){
            Log.d("SHOP","entered");
            ContentValues val1 = new ContentValues();
            val1.put(SHOP_SELECTED,0);
            db.update(TABLE_SHOP,val1,SHOP_KEY_ID+"=1",null);

            ContentValues val2 = new ContentValues();
            val2.put(SHOP_SELECTED,0);
            db.update(TABLE_SHOP,val2,SHOP_KEY_ID+"=2",null);

            ContentValues val3 = new ContentValues();
            val3.put(SHOP_SELECTED,0);
            db.update(TABLE_SHOP,val3,SHOP_KEY_ID+"=3",null);

            ContentValues val4 = new ContentValues();
            val4.put(SHOP_SELECTED,1);
            db.update(TABLE_SHOP,val4,SHOP_KEY_ID+"=4",null);

            ContentValues val5 = new ContentValues();
            val5.put(SHOP_SELECTED,0);
            db.update(TABLE_SHOP,val5,SHOP_KEY_ID+"=5",null);
        }

        set();
    }


    public void clickKhalili(View view){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String[] projection = {
                SHOP_KEY_ID,
                SHOP_ITEM,
                SHOP_SELECTED,
                SHOP_BOUGHT
        };

        String selection = SHOP_KEY_ID + " = ?";
        String[] selectionArgs = {"5"};

        Cursor cursor = db.query(
                TABLE_SHOP,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        cursor.moveToNext();
        int selected = (int)cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_SELECTED));
        int bought = (int)cursor.getLong(cursor.getColumnIndexOrThrow(SHOP_BOUGHT));

        if ( bought == 0){

            String[] projection1 = {
                    COIN_AMOUNT
            };

            Cursor cursor1 = db.query(
                    TABLE_COIN,
                    projection1,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            cursor1.moveToNext();
            int coins = cursor1.getInt(cursor1.getColumnIndexOrThrow(COIN_AMOUNT));

            if (coins >= 40){
                ContentValues coin_value = new ContentValues();
                coin_value.put(COIN_AMOUNT, coins-40);
                db.update(TABLE_COIN,coin_value,null,null);

                ContentValues val1 = new ContentValues();
                val1.put(SHOP_SELECTED,0);
                db.update(TABLE_SHOP,val1,SHOP_KEY_ID+"=1",null);

                ContentValues val2 = new ContentValues();
                val2.put(SHOP_SELECTED,0);
                db.update(TABLE_SHOP,val2,SHOP_KEY_ID+"=2",null);

                ContentValues val3 = new ContentValues();
                val3.put(SHOP_SELECTED,0);
                db.update(TABLE_SHOP,val3,SHOP_KEY_ID+"=3",null);

                ContentValues val4 = new ContentValues();
                val4.put(SHOP_SELECTED,0);
                db.update(TABLE_SHOP,val4,SHOP_KEY_ID+"=4",null);

                ContentValues val5 = new ContentValues();
                val5.put(SHOP_SELECTED,1);
                val5.put(SHOP_BOUGHT,1);
                db.update(TABLE_SHOP,val5,SHOP_KEY_ID+"=5",null);
            }

            cursor1.close();
        }
        else if (selected == 0 && bought == 1){
            Log.d("SHOP","entered");
            ContentValues val1 = new ContentValues();
            val1.put(SHOP_SELECTED,0);
            db.update(TABLE_SHOP,val1,SHOP_KEY_ID+"=1",null);

            ContentValues val2 = new ContentValues();
            val2.put(SHOP_SELECTED,0);
            db.update(TABLE_SHOP,val2,SHOP_KEY_ID+"=2",null);

            ContentValues val3 = new ContentValues();
            val3.put(SHOP_SELECTED,0);
            db.update(TABLE_SHOP,val3,SHOP_KEY_ID+"=3",null);

            ContentValues val4 = new ContentValues();
            val4.put(SHOP_SELECTED,0);
            db.update(TABLE_SHOP,val4,SHOP_KEY_ID+"=4",null);

            ContentValues val5 = new ContentValues();
            val5.put(SHOP_SELECTED,1);
            db.update(TABLE_SHOP,val5,SHOP_KEY_ID+"=5",null);
        }

        set();
    }



}
