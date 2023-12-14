package com.example.contador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Base_Datos extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MiBaseDeDatos";
    private static final int DATABASE_VERSION = 1;

    public Base_Datos(Context contexto){
        super(contexto, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE MiTabla (usuario TEXT PRIMARY KEY, contraseña TEXT)";
        db.execSQL(createTable);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MiTabla");
        onCreate(db);
    }
}
