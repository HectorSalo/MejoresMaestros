package com.example.chirinos.mejoresmaestros;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import java.util.ArrayList;

public class EditAsignacionesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerSustituciones;
    private ArrayList<ConstructorPublicadores> listSelecSust;
    private AdapterSelecPubAsignacion adapterSust;
    private Spinner spinnerEncargados;
    private EditText etBuscar;
    private Bundle bundleRecibir;
    private Integer semana, dia, mes, anual, idSala;
    private String spinnerSeleccion, lector1, encargado1Sala1, ayudante1Sala1, encargado2Sala1, ayudante2Sala1, encargado3Sala1, ayudante3Sala1, lector2, encargado1Sala2, ayudante1Sala2, encargado2Sala2, ayudante2Sala2, encargado3Sala2, ayudante3Sala2;
    private ArrayList<String> listEncargados;
    private FloatingActionButton fabClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_asignaciones);

        recyclerSustituciones = (RecyclerView) findViewById(R.id.recyclerSustituciones);
        LinearLayoutManager llM = new LinearLayoutManager(this);
        recyclerSustituciones.setLayoutManager(llM);
        recyclerSustituciones.setHasFixedSize(true);
        listSelecSust = new ArrayList<>();
        fabClose = (FloatingActionButton)findViewById(R.id.fabCloseedit);

        fabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
        dia = bundleRecibir.getInt("dia");
        mes = (bundleRecibir.getInt("mes") + 1);
        anual = bundleRecibir.getInt("anual");
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
            lector1 = fila.getString(1);
            if (fila.getString(2) != null) {
                listEncargados.add(fila.getString(2));
                encargado1Sala1 = fila.getString(2);
            }
            if (fila.getString(3) != null) {
                listEncargados.add(fila.getString(3));
                ayudante1Sala1 = fila.getString(3);
            }
            if (fila.getString(4) != null) {
                listEncargados.add(fila.getString(4));
                encargado2Sala1 = fila.getString(4);
            }
            if (fila.getString(5) != null) {
                listEncargados.add(fila.getString(5));
                ayudante2Sala1 = fila.getString(5);
            }
            if (fila.getString(6) != null) {
                listEncargados.add(fila.getString(6));
                encargado3Sala1 = fila.getString(6);
            }
            if (fila.getString(7) != null) {
                listEncargados.add(fila.getString(7));
                ayudante3Sala1 = fila.getString(7);
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
            lector2 = fila.getString(1);
            if (fila.getString(2) != null) {
                listEncargados.add(fila.getString(2));
                encargado1Sala2 = fila.getString(2);
            }
            if (fila.getString(3) != null) {
                listEncargados.add(fila.getString(3));
                ayudante1Sala2 = fila.getString(3);
            }
            if (fila.getString(4) != null) {
                listEncargados.add(fila.getString(4));
                encargado2Sala2 = fila.getString(4);
            }
            if (fila.getString(5) != null) {
                listEncargados.add(fila.getString(5));
                ayudante2Sala2 = fila.getString(5);
            }
            if (fila.getString(6) != null) {
                listEncargados.add(fila.getString(6));
                encargado3Sala2 = fila.getString(6);
            }
            if (fila.getString(7) != null) {
                listEncargados.add(fila.getString(7));
                ayudante3Sala2 = fila.getString(7);
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
            publi.setIdPublicador(cursor.getString(0));
            publi.setNombrePublicador(cursor.getString(1));
            publi.setApellidoPublicador(cursor.getString(2));
            publi.setGenero(cursor.getString(5));
            publi.setUltAsignacion(String.valueOf(cursor.getInt(13)) + "/" + String.valueOf(cursor.getInt(14)) + "/" + String.valueOf(cursor.getInt(15)));

            listSelecSust.add(publi);
        }
        adapterSust = new AdapterSelecPubAsignacion(listSelecSust, this);
        recyclerSustituciones.setAdapter(adapterSust);
        db.close();

        adapterSust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (spinnerSeleccion.equals("Publicador a cambiar")) {
                    Toast.makeText(getApplicationContext(), "Debe escoger a quién se cambiará", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(EditAsignacionesActivity.this, R.style.Theme_Dialog_Publicador);
                    dialog.setTitle("Confirmar");
                    dialog.setMessage("¿Desea sustituir a " + spinnerSeleccion + " por " + listSelecSust.get(recyclerSustituciones.getChildAdapterPosition(v)).getNombrePublicador() + " " + listSelecSust.get(recyclerSustituciones.getChildAdapterPosition(v)).getApellidoPublicador() + "?");

                    dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cambiarfechaSustitucion(listSelecSust.get(recyclerSustituciones.getChildAdapterPosition(v)).getIdPublicador());
                            guardarSustitucion(listSelecSust.get(recyclerSustituciones.getChildAdapterPosition(v)).getNombrePublicador() + " " + listSelecSust.get(recyclerSustituciones.getChildAdapterPosition(v)).getApellidoPublicador());
                            Toast.makeText(getApplicationContext(), "Sustitución exitosa", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            finish();
                            Intent myIntent = new Intent(getApplicationContext(), PrincipalActivity.class);
                            startActivity(myIntent);
                        }
                    });
                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    private void guardarSustitucion(String name) {
        if (idSala == 1) {
            String sSemana = String.valueOf(semana);
            AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
            SQLiteDatabase dbEditar = conect.getWritableDatabase();

            ContentValues registro = new ContentValues();
            if (spinnerSeleccion.equals(lector1)) {
                registro.put("lector", name);
            } else if (spinnerSeleccion.equals(encargado1Sala1)) {
                registro.put("encargado1", name);
            } else if (spinnerSeleccion.equals(ayudante1Sala1)) {
                registro.put("ayudante1", name);
            } else if (spinnerSeleccion.equals(encargado2Sala1)) {
                registro.put("encargado2", name);
            } else if (spinnerSeleccion.equals(ayudante2Sala1)) {
                registro.put("ayudante2", name);
            } else if (spinnerSeleccion.equals(encargado3Sala1)) {
                registro.put("encargado3", name);
            } else if (spinnerSeleccion.equals(ayudante3Sala1)) {
                registro.put("ayudante3", name);
            }

            dbEditar.update("sala1", registro, "semana=" + sSemana, null);
            dbEditar.close();


        } else if (idSala == 2) {
            String sSemana = String.valueOf(semana);
            AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
            SQLiteDatabase dbEditar = conect.getWritableDatabase();

            ContentValues registro = new ContentValues();
            if (spinnerSeleccion.equals(lector2)) {
                registro.put("lector", name);
            } else if (spinnerSeleccion.equals(encargado1Sala2)) {
                registro.put("encargado1", name);
            } else if (spinnerSeleccion.equals(ayudante1Sala2)) {
                registro.put("ayudante1", name);
            } else if (spinnerSeleccion.equals(encargado2Sala2)) {
                registro.put("encargado2", name);
            } else if (spinnerSeleccion.equals(ayudante2Sala2)) {
                registro.put("ayudante2", name);
            } else if (spinnerSeleccion.equals(encargado3Sala2)) {
                registro.put("encargado3", name);
            } else if (spinnerSeleccion.equals(ayudante3Sala2)) {
                registro.put("ayudante3", name);
            }

            dbEditar.update("sala2", registro, "semana=" + sSemana, null);
            dbEditar.close();
        }
    }

    private void cambiarfechaSustitucion (String i) {
        String sidPub = String.valueOf(i);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase dbEditar = conect.getWritableDatabase();

        String diaSust = String.valueOf(dia);
        String mesSust = String.valueOf(mes);
        String anualSust = String.valueOf(anual);

        ContentValues registro = new ContentValues();
        registro.put("diasust", diaSust);
        registro.put("messust", mesSust);
        registro.put("anualsust", anualSust);

        dbEditar.update("publicadores", registro, "idPublicador=" + sidPub, null);
        dbEditar.close();
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
        spinnerSeleccion = spinnerEncargados.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
