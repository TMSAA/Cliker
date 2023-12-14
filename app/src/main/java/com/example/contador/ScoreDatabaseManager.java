package com.example.contador;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ScoreDatabaseManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public ScoreDatabaseManager(Context c) {
        context = c;
    }

    public ScoreDatabaseManager open() {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }
    public boolean checkIfUserExists(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COLUMN_USERNAME + " = ?", new String[]{username});

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        return exists;
    }
    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COLUMN_USERNAME + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?", new String[]{username, password});

        boolean authenticated = cursor.getCount() > 0;

        cursor.close();
        return authenticated;
    }
    public long insertScoreWithPassword(String username, String password, int score) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.COLUMN_USERNAME, username);
        contentValue.put(DatabaseHelper.COLUMN_PASSWORD, password);
        contentValue.put(DatabaseHelper.COLUMN_SCORE, score);
        return db.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    @SuppressLint("Range")
    public int getLastScoreForUser(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int lastScore = 0;

        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_SCORE + " FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COLUMN_USERNAME + " = ? ORDER BY " + DatabaseHelper.COLUMN_ID + " DESC LIMIT 1", new String[]{username});

        if (cursor.moveToFirst()) {
            lastScore = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SCORE));
        }

        cursor.close();
        return lastScore;
    }

    public long insertScore(String username,String password, int score) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.COLUMN_USERNAME, username);
        contentValue.put(DatabaseHelper.COLUMN_PASSWORD, password);
        contentValue.put(DatabaseHelper.COLUMN_SCORE, score);
        return db.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }
}
