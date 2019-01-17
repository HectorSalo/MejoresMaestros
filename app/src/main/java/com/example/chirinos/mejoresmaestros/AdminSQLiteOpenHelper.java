package com.example.chirinos.mejoresmaestros;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final int VERSION = 13;


    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table publicadores (idPublicador integer primary key autoincrement not null, nombre varchar, apellido varchar, telefono varchar, correo varchar, genero varchar, imagen blob, diaasignacion int, mesasignacion int, anualasignacion int, diaayudante int, mesayudante int, anualayudante int, diasust int, messust int,  anualsust int, inhabilitar int)");
        db.execSQL("create table sala1 (semana integer primary key, lector varchar, encargado1 varchar, ayudante1 varchar, encargado2 varchar, ayudante2 varchar, encargado3 varchar, ayudante3 varchar, evento varchar, dia int, mes int, anual int, tipo1 varchar, tipo2 varchar, tipo3 varchar)");
        db.execSQL("create table sala2 (semana integer primary key, lector varchar, encargado1 varchar, ayudante1 varchar, encargado2 varchar, ayudante2 varchar, encargado3 varchar, ayudante3 varchar, evento varchar, dia int, mes int, anual int, tipo1 varchar, tipo2 varchar, tipo3 varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS publicadores");
        db.execSQL("DROP TABLE IF EXISTS sala1");
        db.execSQL("DROP TABLE IF EXISTS sala2");
        onCreate(db);

    }
}
