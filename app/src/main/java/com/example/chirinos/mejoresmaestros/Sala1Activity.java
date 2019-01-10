package com.example.chirinos.mejoresmaestros;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Sala1Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerPrimeraAsignacion, spinnerSegundaAsignacion, spinnerTerceraAsignacion;
    private TextView tvLectura, tvFecha, tvEncargado1, tvAyudante1;
    private Integer idLector, idEncargado1, lectura, eAsignacion1, dia, mes, anual, diaAsignacion, mesAsignacion, anualAsignacion;
    private Bundle bundleRecibir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSalas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        tvLectura = (TextView)findViewById(R.id.lecturaSala1);
        tvEncargado1 = (TextView) findViewById(R.id.encargado1Sala1);
        tvAyudante1 = (TextView) findViewById(R.id.ayudante1Sala1);
        tvFecha = (TextView) findViewById(R.id.fechaSala1);

        spinnerPrimeraAsignacion = (Spinner)findViewById(R.id.spinnerPrimeraAsignacion);
        String [] spPrimeraAsignacion = {"Seleccionar Asignacion", "Primera Conversacion", "Primera Revisita", "Segunda Revisita", "Curso Biblico", "Discurso"};
        ArrayAdapter <String> adapterPrimeraAsignacion = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spPrimeraAsignacion);
        spinnerPrimeraAsignacion.setAdapter(adapterPrimeraAsignacion);
        spinnerPrimeraAsignacion.setOnItemSelectedListener(this);

        spinnerSegundaAsignacion = (Spinner)findViewById(R.id.spinnerSegundaAsignacion);
        String [] spSegundaAsignacion = {"Seleccionar Asignacion", "Primera Conversacion", "Primera Revisita", "Segunda Revisita", "Curso Biblico", "Discurso"};
        ArrayAdapter <String> adapterSegundaAsignacion = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spSegundaAsignacion);
        spinnerSegundaAsignacion.setAdapter(adapterSegundaAsignacion);
        spinnerSegundaAsignacion.setOnItemSelectedListener(this);

        spinnerTerceraAsignacion = (Spinner)findViewById(R.id.spinnerTerceraAsignacion);
        String [] spTerceraAsignacion = {"Seleccionar Asignacion", "Primera Conversacion", "Primera Revisita", "Segunda Revisita", "Curso Biblico", "Discurso"};
        ArrayAdapter <String> adapterTerceraAsignacion = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spTerceraAsignacion);
        spinnerTerceraAsignacion.setAdapter(adapterTerceraAsignacion);
        spinnerTerceraAsignacion.setOnItemSelectedListener(this);

        bundleRecibir = this.getIntent().getExtras();
        lectura = bundleRecibir.getInt("lectura");
        eAsignacion1 = bundleRecibir.getInt("eAsignacion1");
        idLector = bundleRecibir.getInt("idLector");
        idEncargado1 = bundleRecibir.getInt("idEncargado1");


        if (lectura == 1) {
            cargarLector();
        }

        if (eAsignacion1 == 1) {
            cargarEncargado1 ();
        }

        tvFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecFecha();
            }
        });

        tvLectura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(getApplicationContext(), SeleccionarPubAsig.class);
                Bundle myBundle = new Bundle();
                myBundle.putInt("abrirLectura", 1);
                myintent.putExtras(myBundle);
                startActivity(myintent);
            }
        });

    }

    private void selecFecha() {
        final Calendar calendario = Calendar.getInstance();

        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH);
        anual = calendario.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tvFecha.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                diaAsignacion = dayOfMonth;
                mesAsignacion = month + 1;
                anualAsignacion = year;
            }
        }, anual, mes , dia);
        datePickerDialog.show();
    }

    private void cargarLector () {
        String sidLector = String.valueOf(idLector);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sidLector, null);

        if(fila.moveToFirst()) {
            tvLectura.setText(fila.getString(1) + " " + fila.getString(2));
        }
    }

    private void cargarEncargado1 () {
        String sidLector = String.valueOf(idEncargado1);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sidLector, null);

        if(fila.moveToFirst()) {
            tvEncargado1.setText(fila.getString(1) + " " + fila.getString(2));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_salas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_save) {
            Toast.makeText(getApplicationContext(), "Informacion guardada", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_cancel) {
            Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
