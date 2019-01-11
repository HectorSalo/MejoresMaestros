package com.example.chirinos.mejoresmaestros;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class SeleccionarPubAsig extends AppCompatActivity {

    private RecyclerView recyclerSeleccionar;
    private ArrayList<ConstructorPublicadores> listSeleccionarPub;
    private AdapterSelecPubAsignacion adapterSeleccionar;
    private FloatingActionButton fabClose;
    private Bundle bundleRecibir;
    private String genero;
    private Integer idLector, idEncargado1, idAyudante1, idEncargado2, idAyudante2, idEncargado3, idAyudante3, abrirLecutra, abrirEncargado1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_pub_asig);

        recyclerSeleccionar = (RecyclerView) findViewById(R.id.recyclerSeleccionar);
        LinearLayoutManager llM = new LinearLayoutManager(this);
        recyclerSeleccionar.setLayoutManager(llM);
        recyclerSeleccionar.setHasFixedSize(true);
        listSeleccionarPub = new ArrayList<>();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, llM.getOrientation());
        recyclerSeleccionar.addItemDecoration(dividerItemDecoration);


        llenarListaLectura();

        fabClose = (FloatingActionButton) findViewById(R.id.fabCloseSelec);
        fabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void llenarListaLectura() {
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();

        Cursor cursor =db.rawQuery("SELECT * FROM publicadores WHERE genero='Hombre' ORDER BY anualasignacion ASC, mesasignacion ASC, diaasignacion ASC", null);

        while (cursor.moveToNext()) {
            ConstructorPublicadores publi = new ConstructorPublicadores();
            publi.setIdPublicador(cursor.getInt(0));
            publi.setNombrePublicador(cursor.getString(1));
            publi.setApellidoPublicador(cursor.getString(2));
            publi.setGenero(cursor.getString(5));
            publi.setUltAsignacion(String.valueOf(cursor.getInt(7)) + "/" + String.valueOf(cursor.getInt(8)) + "/" + String.valueOf(cursor.getInt(9)));

            listSeleccionarPub.add(publi);
        }
        adapterSeleccionar = new AdapterSelecPubAsignacion(listSeleccionarPub, this);
        recyclerSeleccionar.setAdapter(adapterSeleccionar);

        adapterSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idLector = listSeleccionarPub.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador();
                Snackbar.make(view, "Seleccione al Encargado de la Primera Asignacion", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                llenarListaEncargado1();

            }
        });
        db.close();
    }

    private void llenarListaEncargado1 () {
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();
        final ArrayList<ConstructorPublicadores> listAsignacion1 = new ArrayList<>();

        Cursor cursor =db.rawQuery("SELECT * FROM publicadores ORDER BY anualasignacion ASC, mesasignacion ASC, diaasignacion ASC", null);

        while (cursor.moveToNext()) {
            ConstructorPublicadores publi = new ConstructorPublicadores();
            publi.setIdPublicador(cursor.getInt(0));
            publi.setNombrePublicador(cursor.getString(1));
            publi.setApellidoPublicador(cursor.getString(2));
            publi.setGenero(cursor.getString(5));
            publi.setUltAsignacion(String.valueOf(cursor.getInt(7)) + "/" + String.valueOf(cursor.getInt(8)) + "/" + String.valueOf(cursor.getInt(9)));

            listAsignacion1.add(publi);
        }
        adapterSeleccionar.updateListSelec(listAsignacion1);


        adapterSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Seleccione al Ayudante de la Primera Asignacion", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                idEncargado1 = listAsignacion1.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador();
                genero = listAsignacion1.get(recyclerSeleccionar.getChildAdapterPosition(view)).getGenero();
                llenarListaAyudante1();
           }
        });
        db.close();
    }

    private void llenarListaAyudante1 () {
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();
        final ArrayList<ConstructorPublicadores> listAyudante1 = new ArrayList<>();
        String elegirGenero = "";

        if (genero.equals("Hombre")) {
            elegirGenero = "SELECT * FROM publicadores WHERE genero='Hombre' ORDER BY anualayudante ASC, mesayudante ASC, diaayudante ASC";
        } else if (genero.equals("Mujer")) {
            elegirGenero = "SELECT * FROM publicadores WHERE genero='Mujer' ORDER BY anualayudante ASC, mesayudante ASC, diaayudante ASC";
        }

        Cursor cursor = db.rawQuery(elegirGenero, null);

        while (cursor.moveToNext()) {
            ConstructorPublicadores publi = new ConstructorPublicadores();
            publi.setIdPublicador(cursor.getInt(0));
            publi.setNombrePublicador(cursor.getString(1));
            publi.setApellidoPublicador(cursor.getString(2));
            publi.setGenero(cursor.getString(5));
            publi.setUltAsignacion(String.valueOf(cursor.getInt(10)) + "/" + String.valueOf(cursor.getInt(11)) + "/" + String.valueOf(cursor.getInt(12)));

            listAyudante1.add(publi);
        }
        adapterSeleccionar.updateListSelec(listAyudante1);

        adapterSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Seleccione al Encargado de la Segunda Asignacion", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                idAyudante1 = listAyudante1.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador();
                llenarListaEncargado2();
            }
        });
        db.close();
    }

    private void llenarListaEncargado2 () {
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();
        final ArrayList<ConstructorPublicadores> listAsignacion2 = new ArrayList<>();

        Cursor cursor =db.rawQuery("SELECT * FROM publicadores ORDER BY anualasignacion ASC, mesasignacion ASC, diaasignacion ASC", null);

        while (cursor.moveToNext()) {
            ConstructorPublicadores publi = new ConstructorPublicadores();
            publi.setIdPublicador(cursor.getInt(0));
            publi.setNombrePublicador(cursor.getString(1));
            publi.setApellidoPublicador(cursor.getString(2));
            publi.setGenero(cursor.getString(5));
            publi.setUltAsignacion(String.valueOf(cursor.getInt(7)) + "/" + String.valueOf(cursor.getInt(8)) + "/" + String.valueOf(cursor.getInt(9)));

            listAsignacion2.add(publi);
        }
        adapterSeleccionar.updateListSelec(listAsignacion2);


        adapterSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Seleccione al Ayudante de la Segunda Asignacion", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                idEncargado2 = listAsignacion2.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador();
                genero = listAsignacion2.get(recyclerSeleccionar.getChildAdapterPosition(view)).getGenero();
                llenarListaAyudante2();
            }
        });
        db.close();
    }

    private void llenarListaAyudante2 () {
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();
        final ArrayList<ConstructorPublicadores> listAyudante2 = new ArrayList<>();
        String elegirGenero = "";

        if (genero.equals("Hombre")) {
            elegirGenero = "SELECT * FROM publicadores WHERE genero='Hombre' ORDER BY anualayudante ASC, mesayudante ASC, diaayudante ASC";
        } else if (genero.equals("Mujer")) {
            elegirGenero = "SELECT * FROM publicadores WHERE genero='Mujer' ORDER BY anualayudante ASC, mesayudante ASC, diaayudante ASC";
        }

        Cursor cursor = db.rawQuery(elegirGenero, null);

        while (cursor.moveToNext()) {
            ConstructorPublicadores publi = new ConstructorPublicadores();
            publi.setIdPublicador(cursor.getInt(0));
            publi.setNombrePublicador(cursor.getString(1));
            publi.setApellidoPublicador(cursor.getString(2));
            publi.setGenero(cursor.getString(5));
            publi.setUltAsignacion(String.valueOf(cursor.getInt(10)) + "/" + String.valueOf(cursor.getInt(11)) + "/" + String.valueOf(cursor.getInt(12)));

            listAyudante2.add(publi);
        }
        adapterSeleccionar.updateListSelec(listAyudante2);

        adapterSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Seleccione al Encargado de la Tercera Asignacion", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                idAyudante2 = listAyudante2.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador();
                llenarListaEncargado3();
            }
        });
        db.close();
    }

    private void llenarListaEncargado3 () {
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();
        final ArrayList<ConstructorPublicadores> listAsignacion3 = new ArrayList<>();

        Cursor cursor =db.rawQuery("SELECT * FROM publicadores ORDER BY anualasignacion ASC, mesasignacion ASC, diaasignacion ASC", null);

        while (cursor.moveToNext()) {
            ConstructorPublicadores publi = new ConstructorPublicadores();
            publi.setIdPublicador(cursor.getInt(0));
            publi.setNombrePublicador(cursor.getString(1));
            publi.setApellidoPublicador(cursor.getString(2));
            publi.setGenero(cursor.getString(5));
            publi.setUltAsignacion(String.valueOf(cursor.getInt(7)) + "/" + String.valueOf(cursor.getInt(8)) + "/" + String.valueOf(cursor.getInt(9)));

            listAsignacion3.add(publi);
        }
        adapterSeleccionar.updateListSelec(listAsignacion3);


        adapterSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Seleccione al Ayudante de la Tercera Asignacion", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                idEncargado3 = listAsignacion3.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador();
                genero = listAsignacion3.get(recyclerSeleccionar.getChildAdapterPosition(view)).getGenero();
                llenarListaAyudante3();
            }
        });
        db.close();
    }

    private void llenarListaAyudante3() {
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();
        final ArrayList<ConstructorPublicadores> listAyudante3 = new ArrayList<>();
        String elegirGenero = "";

        if (genero.equals("Hombre")) {
            elegirGenero = "SELECT * FROM publicadores WHERE genero='Hombre' ORDER BY anualayudante ASC, mesayudante ASC, diaayudante ASC";
        } else if (genero.equals("Mujer")) {
            elegirGenero = "SELECT * FROM publicadores WHERE genero='Mujer' ORDER BY anualayudante ASC, mesayudante ASC, diaayudante ASC";
        }

        Cursor cursor = db.rawQuery(elegirGenero, null);

        while (cursor.moveToNext()) {
            ConstructorPublicadores publi = new ConstructorPublicadores();
            publi.setIdPublicador(cursor.getInt(0));
            publi.setNombrePublicador(cursor.getString(1));
            publi.setApellidoPublicador(cursor.getString(2));
            publi.setGenero(cursor.getString(5));
            publi.setUltAsignacion(String.valueOf(cursor.getInt(10)) + "/" + String.valueOf(cursor.getInt(11)) + "/" + String.valueOf(cursor.getInt(12)));

            listAyudante3.add(publi);
        }
        adapterSeleccionar.updateListSelec(listAyudante3);

        adapterSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idAyudante3 = listAyudante3.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador();
                llenarSala1();
            }
        });
        db.close();
    }
    private void llenarSala1 () {
        Intent myintent = new Intent(getApplicationContext(), Sala1Activity.class);
        Bundle myBundle = new Bundle();
        myBundle.putInt("llenarSala1", 1);
        myBundle.putInt("idLector", idLector);
        myBundle.putInt("idEncargado1", idEncargado1);
        myBundle.putInt("idAyudante1", idAyudante1);
        myBundle.putInt("idEncargado2", idEncargado2);
        myBundle.putInt("idAyudante2", idAyudante2);
        myBundle.putInt("idEncargado3", idEncargado3);
        myBundle.putInt("idAyudante3", idAyudante3);

        myintent.putExtras(myBundle);
        startActivity(myintent);
}

}
