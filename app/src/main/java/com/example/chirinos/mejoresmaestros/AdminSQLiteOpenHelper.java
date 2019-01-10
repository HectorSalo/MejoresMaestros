package com.example.chirinos.mejoresmaestros;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final int VERSION = 11;


    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table publicadores (idPublicador integer primary key autoincrement not null, nombre varchar, apellido varchar, telefono varchar, correo varchar, genero varchar, imagen blob, diaasignacion int, mesasignacion int, anualasignacion int, diaayudante int, mesayudante int, anualayudante int, diasust int, messust int,  anualsust int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS publicadores");
        onCreate(db);

    }
}
