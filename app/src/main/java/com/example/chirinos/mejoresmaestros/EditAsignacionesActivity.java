package com.example.chirinos.mejoresmaestros;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class EditAsignacionesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerSustituciones;
    private ArrayList<ConstructorPublicadores> listSelecSust;
    private AdapterSelecPubAsignacion adapterSust;
    private Spinner spinnerEncargados;
    private EditText etBuscar;
    private Bundle bundleRecibir;
    private Integer semana, idSala;
    private ArrayList<String> listEncargados;
    private ArrayList<ConstructorSalas> dataEncargados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_asignaciones);

        recyclerSustituciones = (RecyclerView) findViewById(R.id.recyclerSustituciones);
        LinearLayoutManager llM = new LinearLayoutManager(this);
        recyclerSustituciones.setLayoutManager(llM);
        recyclerSustituciones.setHasFixedSize(true);
        listSelecSust = new ArrayList<>();

        etBuscar = (EditText) findViewById(R.id.etBuscarSust);
        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listafiltradaSust (s.toString());
            }
        });


        spinnerEncargados = (Spinner)findViewById(R.id.spinnerEncargados);



        bundleRecibir = this.getIntent().getExtras();
        semana = bundleRecibir.getInt("semana");
        idSala = bundleRecibir.getInt("sala");

        if (idSala == 1) {
            cargarEncargadosSala1(semana);
        } else if (idSala == 2) {
            cargarEncargadosSala2(semana);
        }

        cargarSustitutos();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, llM.getOrientation());
        recyclerSustituciones.addItemDecoration(dividerItemDecoration);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, listEncargados);
        spinnerEncargados.setAdapter(adapter);
        spinnerEncargados.setOnItemSelectedListener(this);


    }

    private void cargarEncargadosSala1(int i) {
        String strSemana = String.valueOf(i);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();

        Cursor fila =db.rawQuery("SELECT * FROM sala1 WHERE semana =" + strSemana, null);

        if (fila.moveToFirst()) {
            listEncargados = new ArrayList<>();
            listEncargados.add("Publicador a cambiar");
            listEncargados.add(fila.getString(1));
            if (fila.getString(2) != null) {
                listEncargados.add(fila.getString(2));
            }
            if (fila.getString(3) != null) {
                listEncargados.add(fila.getString(3));
            }
            if (fila.getString(4) != null) {
                listEncargados.add(fila.getString(4));
            }
            if (fila.getString(5) != null) {
                listEncargados.add(fila.getString(5));
            }
            if (fila.getString(6) != null) {
                listEncargados.add(fila.getString(6));
            }
            if (fila.getString(7) != null) {
                listEncargados.add(fila.getString(7));
            }

        }
        db.close();

    }

    private void cargarEncargadosSala2(int i) {
        String strSemana = String.valueOf(i);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();

        Cursor fila =db.rawQuery("SELECT * FROM sala2 WHERE semana =" + strSemana, null);

        if (fila.moveToFirst()) {
            listEncargados = new ArrayList<>();
            listEncargados.add("Publicador a cambiar");
            listEncargados.add(fila.getString(1));
            if (fila.getString(2) != null) {
                listEncargados.add(fila.getString(2));
            }
            if (fila.getString(3) != null) {
                listEncargados.add(fila.getString(3));
            }
            if (fila.getString(4) != null) {
                listEncargados.add(fila.getString(4));
            }
            if (fila.getString(5) != null) {
                listEncargados.add(fila.getString(5));
            }
            if (fila.getString(6) != null) {
                listEncargados.add(fila.getString(6));
            }
            if (fila.getString(7) != null) {
                listEncargados.add(fila.getString(7));
            }

        }
        db.close();

    }



    private void cargarSustitutos() {
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();

        Cursor cursor =db.rawQuery("SELECT * FROM publicadores ORDER BY anualsust ASC, messust ASC, diasust ASC", null);

        while (cursor.moveToNext()) {
            ConstructorPublicadores publi = new ConstructorPublicadores();
            publi.setIdPublicador(cursor.getInt(0));
            publi.setNombrePublicador(cursor.getString(1));
            publi.setApellidoPublicador(cursor.getString(2));
            publi.setGenero(cursor.getString(5));
            publi.setUltAsignacion(String.valueOf(cursor.getInt(13)) + "/" + String.valueOf(cursor.getInt(14)) + "/" + String.valueOf(cursor.getInt(15)));

            listSelecSust.add(publi);
        }
        adapterSust = new AdapterSelecPubAsignacion(listSelecSust, this);
        recyclerSustituciones.setAdapter(adapterSust);
        db.close();
    }

    private void listafiltradaSust (String text) {
        String userInput = text.toLowerCase();
        ArrayList<ConstructorPublicadores> newList = new ArrayList<>();

        for (ConstructorPublicadores name : listSelecSust) {

            if (name.getNombrePublicador().toLowerCase().contains(userInput) || name.getApellidoPublicador().toLowerCase().contains(userInput)) {

                newList.add(name);
            }
        }

        adapterSust.updateListSelec(newList);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
