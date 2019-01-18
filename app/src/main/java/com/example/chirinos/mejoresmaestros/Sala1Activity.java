package com.example.chirinos.mejoresmaestros;

import android.app.DatePickerDialog;
import android.content.ContentValues;
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

    private TextView tvLectura, tvFecha, tvEncargado1, tvAyudante1, tvEncargado2, tvAyudante2, tvEncargado3, tvAyudante3, tvAsigLectura, tvAsignacion1, tvAsignacion2, tvAsignacion3;
    private Integer llenarSala1, idLector, idEncargado1, idAyudante1, idEncargado2, idAyudante2, idEncargado3, idAyudante3, diaAsignacion, mesAsignacion, anualAsignacion, numSemana;
    private Bundle bundleRecibir;
    private String lector, encargado1, ayudante1, encargado2, ayudante2, encargado3, ayudante3, asignacion1, asignacion2, asignacion3, tipoEvento;
    private Calendar calendar;

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
        tvEncargado2 = (TextView) findViewById(R.id.encargado2Sala1);
        tvAyudante2 = (TextView) findViewById(R.id.ayudante2Sala1);
        tvEncargado3 = (TextView) findViewById(R.id.encargado3Sala1);
        tvAyudante3 = (TextView) findViewById(R.id.ayudante3Sala1);
        tvFecha = (TextView) findViewById(R.id.fechaSala1);
        tvAsigLectura = (TextView) findViewById(R.id.tvAsigLectura);
        tvAsignacion1 = (TextView) findViewById(R.id.asignacion1);
        tvAsignacion2 = (TextView) findViewById(R.id.asignacion2);
        tvAsignacion3 = (TextView) findViewById(R.id.asignacion3);
        calendar = Calendar.getInstance();


        bundleRecibir = this.getIntent().getExtras();
        llenarSala1 = bundleRecibir.getInt("llenarSala1");
        idLector = bundleRecibir.getInt("idLector");
        idEncargado1 = bundleRecibir.getInt("idEncargado1");
        idAyudante1 = bundleRecibir.getInt("idAyudante1");
        idEncargado2 = bundleRecibir.getInt("idEncargado2");
        idAyudante2 = bundleRecibir.getInt("idAyudante2");
        idEncargado3 = bundleRecibir.getInt("idEncargado3");
        idAyudante3 = bundleRecibir.getInt("idAyudante3");
        diaAsignacion = bundleRecibir.getInt("dia");
        mesAsignacion = bundleRecibir.getInt("mes");
        anualAsignacion = bundleRecibir.getInt("anual");
        asignacion1 = bundleRecibir.getString("asignacion1");
        asignacion2 = bundleRecibir.getString("asignacion2");
        asignacion3 = bundleRecibir.getString("asignacion3");
        tipoEvento = bundleRecibir.getString("evento");

        if (llenarSala1 == 1) {
            cargarInformacion();
        }

    }

    private void cargarInformacion () {
        if (idLector == UtilidadesStatic.ID_VACIOS) {
            tvLectura.setVisibility(View.INVISIBLE);
        } else {
            cargarLector();
        }
        if (idEncargado1 == UtilidadesStatic.ID_VACIOS) {
            tvEncargado1.setVisibility(View.INVISIBLE);
        } else {
            cargarEncargado1();
        }

        if (idAyudante1 == UtilidadesStatic.ID_VACIOS) {
            tvAyudante1.setVisibility(View.INVISIBLE);
        } else {
            cargarAyudante1();
        }

        if (idEncargado2 == UtilidadesStatic.ID_VACIOS) {
            tvEncargado2.setVisibility(View.INVISIBLE);
        } else {
            cargarEncargado2();
        }

        if (idAyudante2 == UtilidadesStatic.ID_VACIOS) {
            tvAyudante2.setVisibility(View.INVISIBLE);
        } else {
            cargarAyudante2();
        }

        if (idEncargado3 == UtilidadesStatic.ID_VACIOS) {
            tvEncargado3.setVisibility(View.INVISIBLE);
        } else {
            cargarEncargado3();
        }

        if (idAyudante3 == UtilidadesStatic.ID_VACIOS) {
            tvAyudante3.setVisibility(View.INVISIBLE);
        } else {
            cargarAyudante3();
        }

        tvAsignacion1.setText(asignacion1);
        tvAsignacion2.setText(asignacion2);
        tvAsignacion3.setText(asignacion3);
        tvFecha.setText(diaAsignacion + "/" + mesAsignacion + "/" + anualAsignacion);
    }




    private void cargarLector () {
        String sid = String.valueOf(idLector);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sid, null);

        if(fila.moveToFirst()) {
            lector = fila.getString(1) + " " + fila.getString(2);
            tvLectura.setText(lector);
        }
    }

    private void cargarEncargado1 () {
        String sid = String.valueOf(idEncargado1);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sid, null);

        if(fila.moveToFirst()) {
            encargado1 = fila.getString(1) + " " + fila.getString(2);
            tvEncargado1.setText(encargado1);
        }
    }

    private void cargarAyudante1 () {
        String sid = String.valueOf(idAyudante1);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sid, null);

        if(fila.moveToFirst()) {
            ayudante1 = fila.getString(1) + " " + fila.getString(2);
            tvAyudante1.setText(ayudante1);
        }
    }

    private void cargarEncargado2 () {
        String sid = String.valueOf(idEncargado2);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sid, null);

        if(fila.moveToFirst()) {
            encargado2 = fila.getString(1) + " " + fila.getString(2);
            tvEncargado2.setText(encargado2);
        }
    }

    private void cargarAyudante2 () {
        String sid = String.valueOf(idAyudante2);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sid, null);

        if(fila.moveToFirst()) {
            ayudante2 = fila.getString(1) + " " + fila.getString(2);
            tvAyudante2.setText(ayudante2);
        }
    }

    private void cargarEncargado3 () {
        String sid = String.valueOf(idEncargado3);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sid, null);

        if(fila.moveToFirst()) {
            encargado3 = fila.getString(1) + " " + fila.getString(2);
            tvEncargado3.setText(encargado3);
        }
    }

    private void cargarAyudante3 () {
        String sid = String.valueOf(idAyudante3);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sid, null);

        if(fila.moveToFirst()) {
            ayudante3 = fila.getString(1) + " " + fila.getString(2);
            tvAyudante3.setText(ayudante3);
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
                guardarInformacion ();
                guradarSala1();
                Toast.makeText(getApplicationContext(), "Información guardada", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(this, PrincipalActivity.class);
                startActivity(myIntent);
                finish();

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

    private void guardarInformacion() {
        if (idLector != UtilidadesStatic.ID_VACIOS) {
            guardarLector();
        }
        if (idEncargado1 != UtilidadesStatic.ID_VACIOS) {
            guardarEncargado1();
        }

        if (idAyudante1 != UtilidadesStatic.ID_VACIOS) {
          guardarAyudante1();
        }

        if (idEncargado2 != UtilidadesStatic.ID_VACIOS) {
           guardarEncargado2();
        }

        if (idAyudante2 != UtilidadesStatic.ID_VACIOS) {
          guardarAyudante2();
        }

        if (idEncargado3 != UtilidadesStatic.ID_VACIOS) {
          guardarEncargado3();
        }

        if (idAyudante3 != UtilidadesStatic.ID_VACIOS) {
          guardarAyudante3();
        }
    }

    private void guardarLector() {
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase dbGuardar = conect.getWritableDatabase();

        String diaDiscurso = String.valueOf(diaAsignacion);
        String mesDiscurso = String.valueOf(mesAsignacion);
        String anualDiscurso = String.valueOf(anualAsignacion);


                ContentValues registro = new ContentValues();
                registro.put("diaasignacion", diaDiscurso);
                registro.put("mesasignacion", mesDiscurso);
                registro.put("anualasignacion", anualDiscurso);

                dbGuardar.update("publicadores", registro, "idPublicador=" + idLector, null);
                dbGuardar.close();

    }

    private void guardarEncargado1 () {
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase dbGuardar = conect.getWritableDatabase();

        String diaDiscurso = String.valueOf(diaAsignacion);
        String mesDiscurso = String.valueOf(mesAsignacion);
        String anualDiscurso = String.valueOf(anualAsignacion);


        ContentValues registro = new ContentValues();
        registro.put("diaasignacion", diaDiscurso);
        registro.put("mesasignacion", mesDiscurso);
        registro.put("anualasignacion", anualDiscurso);

        dbGuardar.update("publicadores", registro, "idPublicador=" + idEncargado1, null);
        dbGuardar.close();
    }

    private void guardarAyudante1 () {
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase dbGuardar = conect.getWritableDatabase();

        String diaAyuda = String.valueOf(diaAsignacion);
        String mesAyuda = String.valueOf(mesAsignacion);
        String anualAyuda = String.valueOf(anualAsignacion);


        ContentValues registro = new ContentValues();
        registro.put("diaayudante", diaAyuda);
        registro.put("mesayudante", mesAyuda);
        registro.put("anualayudante", anualAyuda);

        dbGuardar.update("publicadores", registro, "idPublicador=" + idAyudante1, null);
        dbGuardar.close();
    }

    private void guardarEncargado2 () {
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase dbGuardar = conect.getWritableDatabase();

        String diaDiscurso = String.valueOf(diaAsignacion);
        String mesDiscurso = String.valueOf(mesAsignacion);
        String anualDiscurso = String.valueOf(anualAsignacion);


        ContentValues registro = new ContentValues();
        registro.put("diaasignacion", diaDiscurso);
        registro.put("mesasignacion", mesDiscurso);
        registro.put("anualasignacion", anualDiscurso);

        dbGuardar.update("publicadores", registro, "idPublicador=" + idEncargado2, null);
        dbGuardar.close();
    }

    private void guardarAyudante2 () {
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase dbGuardar = conect.getWritableDatabase();

        String diaAyuda = String.valueOf(diaAsignacion);
        String mesAyuda = String.valueOf(mesAsignacion);
        String anualAyuda = String.valueOf(anualAsignacion);


        ContentValues registro = new ContentValues();
        registro.put("diaayudante", diaAyuda);
        registro.put("mesayudante", mesAyuda);
        registro.put("anualayudante", anualAyuda);

        dbGuardar.update("publicadores", registro, "idPublicador=" + idAyudante2, null);
        dbGuardar.close();
    }

    private void guardarEncargado3 () {
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase dbGuardar = conect.getWritableDatabase();

        String diaDiscurso = String.valueOf(diaAsignacion);
        String mesDiscurso = String.valueOf(mesAsignacion);
        String anualDiscurso = String.valueOf(anualAsignacion);


        ContentValues registro = new ContentValues();
        registro.put("diaasignacion", diaDiscurso);
        registro.put("mesasignacion", mesDiscurso);
        registro.put("anualasignacion", anualDiscurso);

        dbGuardar.update("publicadores", registro, "idPublicador=" + idEncargado3, null);
        dbGuardar.close();
    }

    private void guardarAyudante3 () {
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase dbGuardar = conect.getWritableDatabase();

        String diaAyuda = String.valueOf(diaAsignacion);
        String mesAyuda = String.valueOf(mesAsignacion);
        String anualAyuda = String.valueOf(anualAsignacion);


        ContentValues registro = new ContentValues();
        registro.put("diaayudante", diaAyuda);
        registro.put("mesayudante", mesAyuda);
        registro.put("anualayudante", anualAyuda);

        dbGuardar.update("publicadores", registro, "idPublicador=" + idAyudante3, null);
        dbGuardar.close();
    }

    public void guradarSala1() {
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase dbGuardar = conect.getWritableDatabase();

        calendar.set(anualAsignacion, (mesAsignacion-1), diaAsignacion);
        numSemana = calendar.get(Calendar.WEEK_OF_YEAR);

        String semana = String.valueOf(numSemana);
        String lectura = lector;
        String enc1 = encargado1;
        String ayu1 = ayudante1;
        String enc2 = encargado2;
        String ayu2 = ayudante2;
        String enc3 = encargado3;
        String ayu3 = ayudante3;
        String evento = tipoEvento;
        String dia = String.valueOf(diaAsignacion);
        String mes = String.valueOf(mesAsignacion);
        String anual = String.valueOf(anualAsignacion);
        String tipo1 = asignacion1;
        String tipo2 = asignacion2;
        String tipo3 = asignacion3;

        ContentValues registro = new ContentValues();
        registro.put("semana", semana);
        registro.put("lector", lectura);
        registro.put("encargado1", enc1);
        registro.put("ayudante1", ayu1);
        registro.put("encargado2", enc2);
        registro.put("ayudante2", ayu2);
        registro.put("encargado3", enc3);
        registro.put("ayudante3", ayu3);
        registro.put("evento", evento);
        registro.put("dia", dia);
        registro.put("mes", mes);
        registro.put("anual", anual);
        registro.put("tipo1",tipo1);
        registro.put("tipo2", tipo2);
        registro.put("tipo3", tipo3);

        dbGuardar.insert("sala1", null, registro);
        dbGuardar.close();
    }
}
