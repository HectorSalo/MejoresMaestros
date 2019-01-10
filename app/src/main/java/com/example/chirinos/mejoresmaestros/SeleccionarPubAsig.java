package com.example.chirinos.mejoresmaestros;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private Integer idLector, idEncargado1, abrirLecutra, abrirEncargado1;

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
                Snackbar.make(view, "Selecciono a: " + listSeleccionarPub.get(recyclerSeleccionar.getChildAdapterPosition(view)).getNombrePublicador() + " " + listSeleccionarPub.get(recyclerSeleccionar.getChildAdapterPosition(view)).getApellidoPublicador()+ " como Lector", Snackbar.LENGTH_LONG)
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
                idEncargado1 = listAsignacion1.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador();
                Intent myintent = new Intent(getApplicationContext(), Sala1Activity.class);
                Bundle myBundle = new Bundle();
                myBundle.putInt("lectura", 1);
                myBundle.putInt("eAsignacion1", 1);
                myBundle.putInt("idLector", idLector);
                myBundle.putInt("idEncargado1", idEncargado1);
                myintent.putExtras(myBundle);
                startActivity(myintent);
                finish();
            }
        });
        db.close();
    }


}
