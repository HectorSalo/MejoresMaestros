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
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class SeleccionarPubAsig extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerSeleccionar;
    private Spinner spinnerSeleccionar;
    private ArrayList<ConstructorPublicadores> listSeleccionarPub;
    private AdapterSelecPubAsignacion adapterSeleccionar;
    private FloatingActionButton fabClose;
    private CalendarView calendar;
    private TextView tvselecFecha;
    private CheckBox cbVisita, cbAsamblea;
    private Button btAsignar;
    private RadioGroup grupocb;
    private String genero, seleccion1, seleccion2, seleccion3;
    private Integer idLector, idEncargado1, idAyudante1, idEncargado2, idAyudante2, idEncargado3, idAyudante3, dia, mes, anual;
    private ArrayAdapter<String> adapterSpSeleccionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_pub_asig);

        spinnerSeleccionar = (Spinner) findViewById(R.id.spinnerSelec);
        calendar = (CalendarView) findViewById(R.id.calendarSelec);
        tvselecFecha = (TextView) findViewById(R.id.tvFecha);
        grupocb = (RadioGroup) findViewById(R.id.groupcb);
        btAsignar = (Button) findViewById(R.id.buttonAsignar);
        cbVisita = (CheckBox) findViewById(R.id.checkBoxVisita);
        cbAsamblea = (CheckBox) findViewById(R.id.checkBoxAsamblea);
        recyclerSeleccionar = (RecyclerView) findViewById(R.id.recyclerSeleccionar);
        LinearLayoutManager llM = new LinearLayoutManager(this);
        recyclerSeleccionar.setLayoutManager(llM);
        recyclerSeleccionar.setHasFixedSize(true);
        listSeleccionarPub = new ArrayList<>();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, llM.getOrientation());
        recyclerSeleccionar.addItemDecoration(dividerItemDecoration);

        String [] spSelecccionar = {"Seleccionar Asignacion", "Primera Conversacion", "Primera Revisita", "Segunda Revisita", "Curso Biblico", "Discurso", "Sin asignacion"};
        adapterSpSeleccionar = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spSelecccionar);
        spinnerSeleccionar.setAdapter(adapterSpSeleccionar);
        spinnerSeleccionar.setOnItemSelectedListener(this);

        selecFecha();

        fabClose = (FloatingActionButton) findViewById(R.id.fabCloseSelec);
        fabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    private void selecFecha () {
        spinnerSeleccionar.setVisibility(View.INVISIBLE);
        recyclerSeleccionar.setVisibility(View.INVISIBLE);
        btAsignar.setVisibility(View.VISIBLE);
        tvselecFecha.setVisibility(View.VISIBLE);
        grupocb.setVisibility(View.VISIBLE);
        calendar.setVisibility(View.VISIBLE);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                tvselecFecha.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                dia = dayOfMonth;
                mes = month+1;
                anual = year;
            }
        });

        btAsignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((dia != null) && (mes != null) && (anual != null)) {
                    if (cbAsamblea.isChecked()) {
                        Toast.makeText(getApplicationContext(), "Sin Asignaciones por Asamblea", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        llenarListaLectura();
                        Snackbar.make(v, "Seleccione al Lector", Snackbar.LENGTH_INDEFINITE).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Debe escoger un dia", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void llenarListaLectura() {
        recyclerSeleccionar.setVisibility(View.VISIBLE);
        btAsignar.setVisibility(View.INVISIBLE);
        tvselecFecha.setVisibility(View.INVISIBLE);
        grupocb.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        spinnerSeleccionar.setVisibility(View.INVISIBLE);
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
                Snackbar.make(view, "Seleccione al Encargado de la Primera Asignacion", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Action", null).show();
                llenarListaEncargado1();

            }
        });
        db.close();
    }

    private void llenarListaEncargado1 () {
        recyclerSeleccionar.setVisibility(View.VISIBLE);
        btAsignar.setVisibility(View.INVISIBLE);
        tvselecFecha.setVisibility(View.INVISIBLE);
        grupocb.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        spinnerSeleccionar.setVisibility(View.VISIBLE);
        spinnerSeleccionar.setAdapter(adapterSpSeleccionar);

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


        spinnerSeleccionar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seleccion1 = spinnerSeleccionar.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapterSeleccionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listAsignacion1.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador() == idLector) {
                        Toast.makeText(getApplicationContext(), "Este publicador ya tiene asignacion en esta Sala", Toast.LENGTH_SHORT).show();
                    } else {
                        if (seleccion1.equals("Seleccionar Asignacion")) {
                            Toast.makeText(getApplicationContext(), "Debe escoger el tipo de asignacion", Toast.LENGTH_SHORT).show();
                        } else {

                            if (seleccion1.equals("Discurso")) {
                                if (listAsignacion1.get(recyclerSeleccionar.getChildAdapterPosition(view)).getGenero().equals("Hombre")) {
                                    Snackbar.make(view, "Seleccione al Encargado de la Segunda Asignacion", Snackbar.LENGTH_INDEFINITE).show();
                                    idEncargado1 = listAsignacion1.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador();
                                    llenarListaEncargado2();
                                    idAyudante1 = 1000;
                                } else if (listAsignacion1.get(recyclerSeleccionar.getChildAdapterPosition(view)).getGenero().equals("Mujer")) {
                                    Toast.makeText(getApplicationContext(), "Debe escoger publicador masculino", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Snackbar.make(view, "Seleccione al Ayudante de la Primera Asignacion", Snackbar.LENGTH_INDEFINITE).show();
                                idEncargado1 = listAsignacion1.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador();
                                genero = listAsignacion1.get(recyclerSeleccionar.getChildAdapterPosition(view)).getGenero();
                                llenarListaAyudante1();
                            }
                        }
                    }
                }
            });

        db.close();
    }

    private void llenarListaAyudante1 () {
        recyclerSeleccionar.setVisibility(View.VISIBLE);
        btAsignar.setVisibility(View.INVISIBLE);
        tvselecFecha.setVisibility(View.INVISIBLE);
        grupocb.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        spinnerSeleccionar.setVisibility(View.INVISIBLE);
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
                if ((listAyudante1.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idLector) ||
                        (listAyudante1.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idEncargado1)) {
                    Toast.makeText(getApplicationContext(), "Este publicador ya tiene asignacion en esta Sala", Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(v, "Seleccione al Encargado de la Segunda Asignacion", Snackbar.LENGTH_INDEFINITE).show();
                    idAyudante1 = listAyudante1.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador();
                    llenarListaEncargado2();
                }
            }
        });
        db.close();
    }

    private void llenarListaEncargado2 () {
        recyclerSeleccionar.setVisibility(View.VISIBLE);
        btAsignar.setVisibility(View.INVISIBLE);
        tvselecFecha.setVisibility(View.INVISIBLE);
        grupocb.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        spinnerSeleccionar.setVisibility(View.VISIBLE);
        spinnerSeleccionar.setAdapter(adapterSpSeleccionar);
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

        spinnerSeleccionar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seleccion2 = spinnerSeleccionar.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapterSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((listAsignacion2.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador() == idLector) ||
                        (listAsignacion2.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador() == idEncargado1) ||
                        (listAsignacion2.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador() == idAyudante1)) {
                    Toast.makeText(getApplicationContext(), "Este publicador ya tiene asignacion en esta Sala", Toast.LENGTH_SHORT).show();
                } else {
                    if (seleccion2.equals("Seleccionar Asignacion")) {
                        Toast.makeText(getApplicationContext(), "Debe escoger el tipo de asignacion", Toast.LENGTH_SHORT).show();
                    } else {
                        if (seleccion2.equals("Discurso")) {
                            if (listAsignacion2.get(recyclerSeleccionar.getChildAdapterPosition(view)).getGenero().equals("Hombre")) {
                                Snackbar.make(view, "Seleccione al Encargado de la Tercera Asignacion", Snackbar.LENGTH_INDEFINITE).show();
                                idEncargado2 = listAsignacion2.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador();
                                llenarListaEncargado3();
                                idAyudante2 = 1000;
                            } else if (listAsignacion2.get(recyclerSeleccionar.getChildAdapterPosition(view)).getGenero().equals("Mujer")) {
                                Toast.makeText(getApplicationContext(), "Debe escoger publicador masculino", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Snackbar.make(view, "Seleccione al Ayudante de la Segunda Asignacion", Snackbar.LENGTH_INDEFINITE).show();
                            idEncargado2 = listAsignacion2.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador();
                            genero = listAsignacion2.get(recyclerSeleccionar.getChildAdapterPosition(view)).getGenero();
                            llenarListaAyudante2();
                        }
                    }
                }
            }
        });
        db.close();
    }

    private void llenarListaAyudante2 () {
        recyclerSeleccionar.setVisibility(View.VISIBLE);
        btAsignar.setVisibility(View.INVISIBLE);
        tvselecFecha.setVisibility(View.INVISIBLE);
        grupocb.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        spinnerSeleccionar.setVisibility(View.INVISIBLE);
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
                if ((listAyudante2.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idLector) ||
                        (listAyudante2.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idEncargado1) ||
                        (listAyudante2.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idAyudante1) ||
                        (listAyudante2.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idEncargado2)) {
                    Toast.makeText(getApplicationContext(), "Este publicador ya tiene asignacion en esta Sala", Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(v, "Seleccione al Encargado de la Tercera Asignacion", Snackbar.LENGTH_INDEFINITE).show();
                    idAyudante2 = listAyudante2.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador();
                    llenarListaEncargado3();
                }
            }
        });
        db.close();
    }

    private void llenarListaEncargado3 () {
        recyclerSeleccionar.setVisibility(View.VISIBLE);
        btAsignar.setVisibility(View.INVISIBLE);
        tvselecFecha.setVisibility(View.INVISIBLE);
        grupocb.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        spinnerSeleccionar.setAdapter(adapterSpSeleccionar);
        spinnerSeleccionar.setVisibility(View.VISIBLE);
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

        spinnerSeleccionar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seleccion3 = spinnerSeleccionar.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapterSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((listAsignacion3.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador() == idLector) ||
                        (listAsignacion3.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador() == idEncargado1) ||
                        (listAsignacion3.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador() == idAyudante1) ||
                        (listAsignacion3.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador() == idEncargado2) ||
                        (listAsignacion3.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador() == idAyudante2)) {
                    Toast.makeText(getApplicationContext(), "Este publicador ya tiene asignacion en esta Sala", Toast.LENGTH_SHORT).show();
                } else {
                    if (seleccion3.equals("Seleccionar Asignacion")) {
                        Toast.makeText(getApplicationContext(), "Debe escoger el tipo de asignacion", Toast.LENGTH_SHORT).show();
                    } else {
                        if (seleccion3.equals("Discurso")) {
                            if (listAsignacion3.get(recyclerSeleccionar.getChildAdapterPosition(view)).getGenero().equals("Hombre")) {
                                idEncargado3 = listAsignacion3.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador();
                                idAyudante3 = 1000;
                                llenarSala1();
                            } else if (listAsignacion3.get(recyclerSeleccionar.getChildAdapterPosition(view)).getGenero().equals("Mujer")) {
                                Toast.makeText(getApplicationContext(), "Debe escoger publicador masculino", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Snackbar.make(view, "Seleccione al Ayudante de la Tercera Asignacion", Snackbar.LENGTH_INDEFINITE).show();
                            idEncargado3 = listAsignacion3.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador();
                            genero = listAsignacion3.get(recyclerSeleccionar.getChildAdapterPosition(view)).getGenero();
                            llenarListaAyudante3();
                        }
                    }
                }
            }
        });
        db.close();
    }

    private void llenarListaAyudante3() {
        recyclerSeleccionar.setVisibility(View.VISIBLE);
        btAsignar.setVisibility(View.INVISIBLE);
        tvselecFecha.setVisibility(View.INVISIBLE);
        grupocb.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        spinnerSeleccionar.setVisibility(View.INVISIBLE);
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
                if ((listAyudante3.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idLector) ||
                        (listAyudante3.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idEncargado1) ||
                        (listAyudante3.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idAyudante1) ||
                        (listAyudante3.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idEncargado2) ||
                        (listAyudante3.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idAyudante2) ||
                        (listAyudante3.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idEncargado3)) {
                    Toast.makeText(getApplicationContext(), "Este publicador ya tiene asignacion en esta Sala", Toast.LENGTH_SHORT).show();
                } else {
                    idAyudante3 = listAyudante3.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador();
                    llenarSala1();
                }
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
        myBundle.putInt("dia", dia);
        myBundle.putInt("mes", mes);
        myBundle.putInt("anual", anual);
        myBundle.putString("asignacion1", seleccion1);
        myBundle.putString("asignacion2", seleccion2);
        myBundle.putString("asignacion3", seleccion3);

        myintent.putExtras(myBundle);
        startActivity(myintent);
}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
