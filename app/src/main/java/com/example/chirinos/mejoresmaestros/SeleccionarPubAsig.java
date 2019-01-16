package com.example.chirinos.mejoresmaestros;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class SeleccionarPubAsig extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerSeleccionar;
    private Spinner spinnerSeleccionar;
    private ArrayList<ConstructorPublicadores> listSeleccionarPub, listAsignacion1, listAyudante1, listAsignacion2, listAyudante2, listAsignacion3, listAyudante3;
    private AdapterSelecPubAsignacion adapterSeleccionar;
    private FloatingActionButton fabClose, fabBack;
    private CalendarView calendar;
    private Calendar almanaque;
    private EditText etBuscar;
    private TextView tvselecFecha;
    private CheckBox cbVisita, cbAsamblea;
    private Button btAsignar;
    private RadioGroup grupocb;
    private String genero, seleccion1, seleccion2, seleccion3, evento;
    private Integer idLector, idEncargado1, idAyudante1, idEncargado2, idAyudante2, idEncargado3, idAyudante3, dia, mes, anual, diaActual, mesActual, anualActual;
    private ArrayAdapter<String> adapterSpSeleccionar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_pub_asig);

        spinnerSeleccionar = (Spinner) findViewById(R.id.spinnerSelec);
        calendar = (CalendarView) findViewById(R.id.calendarSelec);
        tvselecFecha = (TextView) findViewById(R.id.tvFecha);
        etBuscar = (EditText) findViewById(R.id.etBuscar);
        grupocb = (RadioGroup) findViewById(R.id.groupcb);
        btAsignar = (Button) findViewById(R.id.buttonAsignar);
        cbVisita = (CheckBox) findViewById(R.id.cbVisita);
        cbAsamblea = (CheckBox) findViewById(R.id.cbAsamblea);
        recyclerSeleccionar = (RecyclerView) findViewById(R.id.recyclerSeleccionar);
        almanaque = Calendar.getInstance();
        LinearLayoutManager llM = new LinearLayoutManager(this);
        recyclerSeleccionar.setLayoutManager(llM);
        recyclerSeleccionar.setHasFixedSize(true);
        listSeleccionarPub = new ArrayList<>();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, llM.getOrientation());
        recyclerSeleccionar.addItemDecoration(dividerItemDecoration);

        String [] spSelecccionar = {"Seleccionar Asignación", "Primera Conversación", "Primera Revisita", "Segunda Revisita", "Curso Bíblico", "Discurso", "Sin asignación"};
        adapterSpSeleccionar = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spSelecccionar);
        spinnerSeleccionar.setAdapter(adapterSpSeleccionar);
        spinnerSeleccionar.setOnItemSelectedListener(this);

        fabBack = (FloatingActionButton) findViewById(R.id.fabBackSelec);
        fabClose = (FloatingActionButton) findViewById(R.id.fabCloseSelec);
        fabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



        selecFecha(1);

    }




    @SuppressLint("RestrictedApi")
    private void selecFecha (final int f) {
        etBuscar.setVisibility(View.INVISIBLE);
        fabBack.setVisibility(View.INVISIBLE);
        spinnerSeleccionar.setVisibility(View.INVISIBLE);
        recyclerSeleccionar.setVisibility(View.INVISIBLE);
        btAsignar.setVisibility(View.VISIBLE);
        tvselecFecha.setVisibility(View.VISIBLE);
        grupocb.setVisibility(View.VISIBLE);
        calendar.setVisibility(View.VISIBLE);

        diaActual = almanaque.get(Calendar.DAY_OF_MONTH);
        mesActual = almanaque.get(Calendar.MONTH);
        anualActual = almanaque.get(Calendar.YEAR);


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
                    if (((mes-1) >= mesActual) && (anual >= anualActual)) {
                        if (cbAsamblea.isChecked() && cbVisita.isChecked()) {
                            Toast.makeText(getApplicationContext(), "No puede haber Visita y Asamblea el mismo día", Toast.LENGTH_LONG).show();
                        } else {
                            if (cbAsamblea.isChecked()) {
                                evento = "Asamblea";
                                llenarSala1Asamblea();
                                Toast.makeText(getApplicationContext(), "Sin Asignaciones por Asamblea", Toast.LENGTH_SHORT).show();

                            } else {
                                if (cbVisita.isChecked()) {
                                    if (f == 1) {
                                        llenarListaLectura(1);
                                    } else if (f == 0) {
                                        llenarListaLectura(0);
                                    }
                                    evento = "Visita";
                                    Snackbar.make(v, "Seleccione al Lector", Snackbar.LENGTH_INDEFINITE).show();
                                } else {
                                    if (f == 1) {
                                        llenarListaLectura(1);
                                    } else if (f == 0) {
                                        llenarListaLectura(0);
                                    }
                                    evento = "";
                                    Snackbar.make(v, "Seleccione al Lector", Snackbar.LENGTH_INDEFINITE).show();
                                }
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No se puede programar una fecha anterior a la actual", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Debe escoger un día", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @SuppressLint("RestrictedApi")
    private void llenarListaLectura(final int l) {
        etBuscar.setVisibility(View.VISIBLE);
        fabBack.setVisibility(View.VISIBLE);
        recyclerSeleccionar.setVisibility(View.VISIBLE);
        btAsignar.setVisibility(View.INVISIBLE);
        tvselecFecha.setVisibility(View.INVISIBLE);
        grupocb.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        spinnerSeleccionar.setVisibility(View.INVISIBLE);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();
        if (l == 1) {

            Cursor cursor = db.rawQuery("SELECT * FROM publicadores WHERE genero='Hombre' ORDER BY anualasignacion ASC, mesasignacion ASC, diaasignacion ASC", null);

            while (cursor.moveToNext()) {
                ConstructorPublicadores publi = new ConstructorPublicadores();
                publi.setIdPublicador(cursor.getInt(0));
                publi.setNombrePublicador(cursor.getString(1));
                publi.setApellidoPublicador(cursor.getString(2));
                publi.setGenero(cursor.getString(5));
                publi.setUltAsignacion(String.valueOf(cursor.getInt(7)) + "/" + String.valueOf(cursor.getInt(8)) + "/" + String.valueOf(cursor.getInt(9)));

                listSeleccionarPub.add(publi);
            }
        }
        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listaBuscarLector (s.toString());
            }
        });


        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecFecha(0);
                Snackbar.make(v, "Seleccione el día de la asignación", Snackbar.LENGTH_INDEFINITE).show();
            }
        });
        adapterSeleccionar = new AdapterSelecPubAsignacion(listSeleccionarPub, this);
        recyclerSeleccionar.setAdapter(adapterSeleccionar);

        adapterSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idLector = listSeleccionarPub.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador();
                Snackbar.make(view, "Seleccione al Encargado de la Primera Asignación", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Action", null).show();
                llenarListaEncargado1();

            }
        });
        db.close();
    }

    @SuppressLint("RestrictedApi")
    private void llenarListaEncargado1 () {
        fabBack.setVisibility(View.VISIBLE);
        recyclerSeleccionar.setVisibility(View.VISIBLE);
        btAsignar.setVisibility(View.INVISIBLE);
        tvselecFecha.setVisibility(View.INVISIBLE);
        grupocb.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        spinnerSeleccionar.setVisibility(View.VISIBLE);
        spinnerSeleccionar.setAdapter(adapterSpSeleccionar);

        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();
        listAsignacion1 = new ArrayList<>();

            Cursor cursor = db.rawQuery("SELECT * FROM publicadores ORDER BY anualasignacion ASC, mesasignacion ASC, diaasignacion ASC", null);

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

            etBuscar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    listBuscarAsig1(s.toString());
                }
            });


        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llenarListaLectura(0);
                Snackbar.make(v, "Seleccione al Lector", Snackbar.LENGTH_INDEFINITE).show();
            }
        });

        spinnerSeleccionar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                seleccion1 = spinnerSeleccionar.getSelectedItem().toString();
                if (seleccion1.equals("Sin asignación")) {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(SeleccionarPubAsig.this, R.style.Theme_Dialog_Publicador);
                    dialog.setTitle("Confirmar");
                    dialog.setMessage("¿Desea pasar por alto esta asignación?");

                    dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Snackbar.make(view, "Seleccione al Encargado de la Segunda Asignación", Snackbar.LENGTH_INDEFINITE).show();
                            llenarListaEncargado2();
                            idEncargado1 = 1000;
                            idAyudante1 = 1000;
                            dialog.dismiss();

                        }
                    });

                    dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapterSeleccionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listAsignacion1.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador() == idLector) {
                        Toast.makeText(getApplicationContext(), "Este publicador ya tiene asignación en esta Sala", Toast.LENGTH_SHORT).show();
                    } else {
                        if (seleccion1.equals("Seleccionar Asignación")) {
                            Toast.makeText(getApplicationContext(), "Debe escoger el tipo de asignación", Toast.LENGTH_SHORT).show();
                        } else {

                            if (seleccion1.equals("Discurso")) {
                                if (listAsignacion1.get(recyclerSeleccionar.getChildAdapterPosition(view)).getGenero().equals("Hombre")) {
                                    Snackbar.make(view, "Seleccione al Encargado de la Segunda Asignación", Snackbar.LENGTH_INDEFINITE).show();
                                    idEncargado1 = listAsignacion1.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador();
                                    llenarListaEncargado2();
                                    idAyudante1 = 1000;
                                } else if (listAsignacion1.get(recyclerSeleccionar.getChildAdapterPosition(view)).getGenero().equals("Mujer")) {
                                    Toast.makeText(getApplicationContext(), "Debe escoger publicador masculino", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Snackbar.make(view, "Seleccione al Ayudante de la Primera Asignación", Snackbar.LENGTH_INDEFINITE).show();
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

    @SuppressLint("RestrictedApi")
    private void llenarListaAyudante1 () {
        fabBack.setVisibility(View.VISIBLE);
        recyclerSeleccionar.setVisibility(View.VISIBLE);
        btAsignar.setVisibility(View.INVISIBLE);
        tvselecFecha.setVisibility(View.INVISIBLE);
        grupocb.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        spinnerSeleccionar.setVisibility(View.INVISIBLE);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();
        listAyudante1 = new ArrayList<>();
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

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listBuscarAyu1(s.toString());
            }
        });

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llenarListaEncargado1();
                Snackbar.make(v, "Seleccione al Encargado de la Primera Asignación", Snackbar.LENGTH_INDEFINITE).show();
            }
        });

        adapterSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((listAyudante1.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idLector) ||
                        (listAyudante1.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idEncargado1)) {
                    Toast.makeText(getApplicationContext(), "Este publicador ya tiene asignación en esta Sala", Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(v, "Seleccione al Encargado de la Segunda Asignación", Snackbar.LENGTH_INDEFINITE).show();
                    idAyudante1 = listAyudante1.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador();
                    llenarListaEncargado2();
                }
            }
        });
        db.close();
    }

    @SuppressLint("RestrictedApi")
    private void llenarListaEncargado2 () {
        fabBack.setVisibility(View.VISIBLE);
        recyclerSeleccionar.setVisibility(View.VISIBLE);
        btAsignar.setVisibility(View.INVISIBLE);
        tvselecFecha.setVisibility(View.INVISIBLE);
        grupocb.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        spinnerSeleccionar.setVisibility(View.VISIBLE);
        spinnerSeleccionar.setAdapter(adapterSpSeleccionar);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();
        listAsignacion2 = new ArrayList<>();

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

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listBuscarAsig2(s.toString());
            }
        });

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llenarListaEncargado1();
                Snackbar.make(v, "Seleccione al Encargado de la Primera Asignación", Snackbar.LENGTH_INDEFINITE).show();
            }
        });

        spinnerSeleccionar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                seleccion2 = spinnerSeleccionar.getSelectedItem().toString();

                if (seleccion2.equals("Sin asignación")) {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(SeleccionarPubAsig.this, R.style.Theme_Dialog_Publicador);
                    dialog.setTitle("Confirmar");
                    dialog.setMessage("¿Desea pasar por alto esta asignación?");

                    dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Snackbar.make(view, "Seleccione al Encargado de la Tercera Asignación", Snackbar.LENGTH_INDEFINITE).show();
                            llenarListaEncargado3();
                            idEncargado2 = 1000;
                            idAyudante2 = 1000;
                            dialog.dismiss();

                        }
                    });

                    dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }
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
                    Toast.makeText(getApplicationContext(), "Este publicador ya tiene asignación en esta Sala", Toast.LENGTH_SHORT).show();
                } else {
                    if (seleccion2.equals("Seleccionar Asignación")) {
                        Toast.makeText(getApplicationContext(), "Debe escoger el tipo de asignación", Toast.LENGTH_SHORT).show();
                    } else {
                        if (seleccion2.equals("Discurso")) {
                            if (listAsignacion2.get(recyclerSeleccionar.getChildAdapterPosition(view)).getGenero().equals("Hombre")) {
                                Snackbar.make(view, "Seleccione al Encargado de la Tercera Asignación", Snackbar.LENGTH_INDEFINITE).show();
                                idEncargado2 = listAsignacion2.get(recyclerSeleccionar.getChildAdapterPosition(view)).getIdPublicador();
                                llenarListaEncargado3();
                                idAyudante2 = 1000;
                            } else if (listAsignacion2.get(recyclerSeleccionar.getChildAdapterPosition(view)).getGenero().equals("Mujer")) {
                                Toast.makeText(getApplicationContext(), "Debe escoger publicador masculino", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Snackbar.make(view, "Seleccione al Ayudante de la Segunda Asignación", Snackbar.LENGTH_INDEFINITE).show();
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

    @SuppressLint("RestrictedApi")
    private void llenarListaAyudante2 () {
        fabBack.setVisibility(View.VISIBLE);
        recyclerSeleccionar.setVisibility(View.VISIBLE);
        btAsignar.setVisibility(View.INVISIBLE);
        tvselecFecha.setVisibility(View.INVISIBLE);
        grupocb.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        spinnerSeleccionar.setVisibility(View.INVISIBLE);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();
        listAyudante2 = new ArrayList<>();
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

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listBuscarAyu2(s.toString());
            }
        });

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llenarListaEncargado2();
                Snackbar.make(v, "Seleccione al Encargado de la Segunda Asignación", Snackbar.LENGTH_INDEFINITE).show();
            }
        });

        adapterSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((listAyudante2.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idLector) ||
                        (listAyudante2.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idEncargado1) ||
                        (listAyudante2.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idAyudante1) ||
                        (listAyudante2.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idEncargado2)) {
                    Toast.makeText(getApplicationContext(), "Este publicador ya tiene asignación en esta Sala", Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(v, "Seleccione al Encargado de la Tercera Asignación", Snackbar.LENGTH_INDEFINITE).show();
                    idAyudante2 = listAyudante2.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador();
                    llenarListaEncargado3();
                }
            }
        });
        db.close();
    }

    @SuppressLint("RestrictedApi")
    private void llenarListaEncargado3 () {
        fabBack.setVisibility(View.VISIBLE);
        recyclerSeleccionar.setVisibility(View.VISIBLE);
        btAsignar.setVisibility(View.INVISIBLE);
        tvselecFecha.setVisibility(View.INVISIBLE);
        grupocb.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        spinnerSeleccionar.setAdapter(adapterSpSeleccionar);
        spinnerSeleccionar.setVisibility(View.VISIBLE);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();
        listAsignacion3 = new ArrayList<>();

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

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listBuscarAsig3(s.toString());
            }
        });

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llenarListaEncargado2();
                Snackbar.make(v, "Seleccione al Encargado de la Segunda Asignación", Snackbar.LENGTH_INDEFINITE).show();
            }
        });

        spinnerSeleccionar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seleccion3 = spinnerSeleccionar.getSelectedItem().toString();

                if (seleccion3.equals("Sin asignación")) {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(SeleccionarPubAsig.this, R.style.Theme_Dialog_Publicador);
                    dialog.setTitle("Confirmar");
                    dialog.setMessage("¿Desea pasar por alto esta asignación?");

                    dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            idEncargado3 = 1000;
                            idAyudante3 = 1000;
                            llenarSala1();
                            dialog.dismiss();

                        }
                    });

                    dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }
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
                    Toast.makeText(getApplicationContext(), "Este publicador ya tiene asignación en esta Sala", Toast.LENGTH_SHORT).show();
                } else {
                    if (seleccion3.equals("Seleccionar Asignación")) {
                        Toast.makeText(getApplicationContext(), "Debe escoger el tipo de asignación", Toast.LENGTH_SHORT).show();
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
                            Snackbar.make(view, "Seleccione al Ayudante de la Tercera Asignación", Snackbar.LENGTH_INDEFINITE).show();
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

    @SuppressLint("RestrictedApi")
    private void llenarListaAyudante3() {
        fabBack.setVisibility(View.VISIBLE);
        recyclerSeleccionar.setVisibility(View.VISIBLE);
        btAsignar.setVisibility(View.INVISIBLE);
        tvselecFecha.setVisibility(View.INVISIBLE);
        grupocb.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        spinnerSeleccionar.setVisibility(View.INVISIBLE);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getReadableDatabase();
        listAyudante3 = new ArrayList<>();
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

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listBuscarAyu3(s.toString());
            }
        });

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llenarListaEncargado3();
                Snackbar.make(v, "Seleccione al Encargado de la Tercera Asignación", Snackbar.LENGTH_INDEFINITE).show();
            }
        });

        adapterSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((listAyudante3.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idLector) ||
                        (listAyudante3.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idEncargado1) ||
                        (listAyudante3.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idAyudante1) ||
                        (listAyudante3.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idEncargado2) ||
                        (listAyudante3.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idAyudante2) ||
                        (listAyudante3.get(recyclerSeleccionar.getChildAdapterPosition(v)).getIdPublicador() == idEncargado3)) {
                    Toast.makeText(getApplicationContext(), "Este publicador ya tiene asignación en esta Sala", Toast.LENGTH_SHORT).show();
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
        myBundle.putString("evento", evento);

        myintent.putExtras(myBundle);
        startActivity(myintent);
        finish();
}

    private void llenarSala1Asamblea() {
        idLector = 1000;
        idEncargado1 = 1000;
        idAyudante1 = 1000;
        idEncargado2 = 1000;
        idAyudante2 = 1000;
        idEncargado3 = 1000;
        idAyudante3 = 1000;
        seleccion1 = "Sin asignación";
        seleccion2 = "Sin asignación";
        seleccion3 = "Sin asignación";


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
        myBundle.putString("evento", evento);

        myintent.putExtras(myBundle);
        startActivity(myintent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void listaBuscarLector (String text) {
        String userInput = text.toLowerCase();
        ArrayList<ConstructorPublicadores> newList = new ArrayList<>();

        for (ConstructorPublicadores name : listSeleccionarPub) {

            if (name.getNombrePublicador().toLowerCase().contains(userInput) || name.getApellidoPublicador().toLowerCase().contains(userInput)) {

                newList.add(name);
            }
        }

        adapterSeleccionar.updateListSelec(newList);
    }

    private void listBuscarAsig1 (String text) {
        String userInput = text.toLowerCase();
        ArrayList<ConstructorPublicadores> newList = new ArrayList<>();

        for (ConstructorPublicadores name : listAsignacion1) {

            if (name.getNombrePublicador().toLowerCase().contains(userInput) || name.getApellidoPublicador().toLowerCase().contains(userInput)) {

                newList.add(name);
            }
        }

        adapterSeleccionar.updateListSelec(newList);
    }

    private void listBuscarAsig2 (String text) {
        String userInput = text.toLowerCase();
        ArrayList<ConstructorPublicadores> newList = new ArrayList<>();

        for (ConstructorPublicadores name : listAsignacion2) {

            if (name.getNombrePublicador().toLowerCase().contains(userInput) || name.getApellidoPublicador().toLowerCase().contains(userInput)) {

                newList.add(name);
            }
        }

        adapterSeleccionar.updateListSelec(newList);
    }

    private void listBuscarAsig3 (String text) {
        String userInput = text.toLowerCase();
        ArrayList<ConstructorPublicadores> newList = new ArrayList<>();

        for (ConstructorPublicadores name : listAsignacion3) {

            if (name.getNombrePublicador().toLowerCase().contains(userInput) || name.getApellidoPublicador().toLowerCase().contains(userInput)) {

                newList.add(name);
            }
        }

        adapterSeleccionar.updateListSelec(newList);
    }

    private void listBuscarAyu1 (String text) {
        String userInput = text.toLowerCase();
        ArrayList<ConstructorPublicadores> newList = new ArrayList<>();

        for (ConstructorPublicadores name : listAyudante1) {

            if (name.getNombrePublicador().toLowerCase().contains(userInput) || name.getApellidoPublicador().toLowerCase().contains(userInput)) {

                newList.add(name);
            }
        }

        adapterSeleccionar.updateListSelec(newList);
    }

    private void listBuscarAyu2 (String text) {
        String userInput = text.toLowerCase();
        ArrayList<ConstructorPublicadores> newList = new ArrayList<>();

        for (ConstructorPublicadores name : listAyudante2) {

            if (name.getNombrePublicador().toLowerCase().contains(userInput) || name.getApellidoPublicador().toLowerCase().contains(userInput)) {

                newList.add(name);
            }
        }

        adapterSeleccionar.updateListSelec(newList);
    }

    private void listBuscarAyu3 (String text) {
        String userInput = text.toLowerCase();
        ArrayList<ConstructorPublicadores> newList = new ArrayList<>();

        for (ConstructorPublicadores name : listAyudante3) {

            if (name.getNombrePublicador().toLowerCase().contains(userInput) || name.getApellidoPublicador().toLowerCase().contains(userInput)) {

                newList.add(name);
            }
        }

        adapterSeleccionar.updateListSelec(newList);
    }
}
