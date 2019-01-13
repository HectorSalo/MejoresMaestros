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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Sala1Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView tvLectura, tvFecha, tvEncargado1, tvAyudante1, tvEncargado2, tvAyudante2, tvEncargado3, tvAyudante3, tvAsignacion1, tvAsignacion2, tvAsignacion3;
    private Integer llenarSala1, idLector, idEncargado1, idAyudante1, idEncargado2, idAyudante2, idEncargado3, idAyudante3, dia, mes, anual, diaAsignacion, mesAsignacion, anualAsignacion;
    private Bundle bundleRecibir;
    private CheckBox checkAsamblea, checkVisita;
    private String asignacion1, asignacion2, asignacion3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSalas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        checkAsamblea = (CheckBox) findViewById(R.id.checkBoxAsamblea);
        checkVisita = (CheckBox) findViewById(R.id.checkBoxVisita);

        tvLectura = (TextView)findViewById(R.id.lecturaSala1);
        tvEncargado1 = (TextView) findViewById(R.id.encargado1Sala1);
        tvAyudante1 = (TextView) findViewById(R.id.ayudante1Sala1);
        tvEncargado2 = (TextView) findViewById(R.id.encargado2Sala1);
        tvAyudante2 = (TextView) findViewById(R.id.ayudante2Sala1);
        tvEncargado3 = (TextView) findViewById(R.id.encargado3Sala1);
        tvAyudante3 = (TextView) findViewById(R.id.ayudante3Sala1);
        tvFecha = (TextView) findViewById(R.id.fechaSala1);
        tvAsignacion1 = (TextView) findViewById(R.id.asignacion1);
        tvAsignacion2 = (TextView) findViewById(R.id.asignacion2);
        tvAsignacion3 = (TextView) findViewById(R.id.asignacion3);


        bundleRecibir = this.getIntent().getExtras();
        llenarSala1 = bundleRecibir.getInt("llenarSala1");
        idLector = bundleRecibir.getInt("idLector");
        idEncargado1 = bundleRecibir.getInt("idEncargado1");
        idAyudante1 = bundleRecibir.getInt("idAyudante1");
        idEncargado2 = bundleRecibir.getInt("idEncargado2");
        idAyudante2 = bundleRecibir.getInt("idAyudante2");
        idEncargado3 = bundleRecibir.getInt("idEncargado3");
        idAyudante3 = bundleRecibir.getInt("idAyudante3");
        asignacion1 = bundleRecibir.getString("asignacion1");
        asignacion2 = bundleRecibir.getString("asignacion2");
        asignacion3 = bundleRecibir.getString("asignacion3");

        if (llenarSala1 == 1) {
            cargarLector();
            cargarEncargado1();
            if (idAyudante1 == 1000) {
                tvAyudante1.setVisibility(View.INVISIBLE);
            } else {
                cargarAyudante1();
            }
            cargarEncargado2();
            if (idAyudante2 == 1000) {
                tvAyudante2.setVisibility(View.INVISIBLE);
            } else {
                cargarAyudante2();
            }
            cargarEncargado3();
            if (idAyudante3 == 1000) {
                tvAyudante3.setVisibility(View.INVISIBLE);
            } else {
                cargarAyudante3();
            }
            tvAsignacion1.setText(asignacion1);
            tvAsignacion2.setText(asignacion2);
            tvAsignacion3.setText(asignacion3);
        }



        tvFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecFecha();
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
        String sid = String.valueOf(idLector);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sid, null);

        if(fila.moveToFirst()) {
            tvLectura.setText(fila.getString(1) + " " + fila.getString(2));
        }
    }

    private void cargarEncargado1 () {
        String sid = String.valueOf(idEncargado1);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sid, null);

        if(fila.moveToFirst()) {
            tvEncargado1.setText(fila.getString(1) + " " + fila.getString(2));
        }
    }

    private void cargarAyudante1 () {
        String sid = String.valueOf(idAyudante1);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sid, null);

        if(fila.moveToFirst()) {
            tvAyudante1.setText(fila.getString(1) + " " + fila.getString(2));
        }
    }

    private void cargarEncargado2 () {
        String sid = String.valueOf(idEncargado2);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sid, null);

        if(fila.moveToFirst()) {
            tvEncargado2.setText(fila.getString(1) + " " + fila.getString(2));
        }
    }

    private void cargarAyudante2 () {
        String sid = String.valueOf(idAyudante2);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sid, null);

        if(fila.moveToFirst()) {
            tvAyudante2.setText(fila.getString(1) + " " + fila.getString(2));
        }
    }

    private void cargarEncargado3 () {
        String sid = String.valueOf(idEncargado3);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sid, null);

        if(fila.moveToFirst()) {
            tvEncargado3.setText(fila.getString(1) + " " + fila.getString(2));
        }
    }

    private void cargarAyudante3 () {
        String sid = String.valueOf(idAyudante3);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sid, null);

        if(fila.moveToFirst()) {
            tvAyudante3.setText(fila.getString(1) + " " + fila.getString(2));
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
        if (checkAsamblea.isChecked()) {
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.menu_save) {
                Toast.makeText(getApplicationContext(), "Semana sin asignaciones por Asamblea", Toast.LENGTH_LONG).show();
                finish();
                return true;
            } else if (id == R.id.menu_cancel) {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }
        } else {

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
