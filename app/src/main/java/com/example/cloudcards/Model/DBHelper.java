package com.example.cloudcards.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.cloudcards.Card;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "cloudcards.db";

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table users (id INTEGER primary key AUTOINCREMENT, username TEXT, password TEXT)");
        db.execSQL("create Table cards (userID INTEGER, " +
                "id INTEGER, " +
                "card_name TEXT, " +
                "card_img TEXT, " +
                "card_mana TEXT, " +
                "card_text TEXT, " +
                "power INTEGER, " +
                "toughness INTEGER, " +
                "type TEXT, "+
                "identity TEXT, "+
                "primary key (userID, id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists users");
        db.execSQL("drop Table if exists cards");
    }

    public Boolean insertCard(int userID, Card card) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", userID);
        contentValues.put("id", card.getCard_id());
        contentValues.put("card_img", card.getCard_img());
        contentValues.put("card_name", card.getCard_name());
        contentValues.put("card_mana", card.getCard_mana());
        contentValues.put("card_text", card.getCard_text());
        contentValues.put("power", card.getPower());
        contentValues.put("toughness", card.getToughness());
        contentValues.put("type", card.getType());
        contentValues.put("identity", card.getCard_colour_identity());
        long result = db.insert("cards", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Card> getCardsByUserID(int userID){
        ArrayList<Card> cards = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from cards where userID = ?", new String[] {String.valueOf(userID)});
        if(cursor.moveToFirst()) {
            do {
                cards.add(
                        new Card(Integer.parseInt(cursor.getString(1)),
                        cursor.getString(2),
                                cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        Integer.parseInt(cursor.getString(6)),
                        Integer.parseInt(cursor.getString(7)),
                                cursor.getString(8),
                                cursor.getString(9))
                );
            } while (cursor.moveToNext());
        }
        return cards;
    };

    public ArrayList<Card> getCardsByName(String search_val, int userID) {
        ArrayList<Card> cards = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from cards where userID = ? " +
                "and card_name LIKE '%"+search_val+"%' ", new String[] {String.valueOf(userID)});
        if(cursor.moveToFirst()) {
            do {
                cards.add(
                        new Card(Integer.parseInt(cursor.getString(1)),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                Integer.parseInt(cursor.getString(6)),
                                Integer.parseInt(cursor.getString(7)))
                );
            } while (cursor.moveToNext());
        }
        return cards;
    }

    public Boolean insertUser(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", email);
        contentValues.put("password", password);
        long result = db.insert("users", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public int getUserID(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username = ? and password = ?", new String[] {username, password});
        int id = -1;
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return id;
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username = ?", new String[] {username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username = ? and password = ?", new String[] {username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}
