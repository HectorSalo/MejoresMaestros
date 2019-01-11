package com.example.chirinos.mejoresmaestros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Sala2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerPrimeraAsignacion, spinnerSegundaAsignacion, spinnerTerceraAsignacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSalas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        spinnerPrimeraAsignacion = (Spinner)findViewById(R.id.spinnerPrimeraAsignacion);
        String [] spPrimeraAsignacion = {"Seleccionar Asignacion", "Lectores y Maestros", "Primera Conversacion", "Primera Revisita", "Segunda Revisita", "Curso Biblico", "Discurso"};
        ArrayAdapter <String> adapterPrimeraAsignacion = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spPrimeraAsignacion);
        spinnerPrimeraAsignacion.setAdapter(adapterPrimeraAsignacion);
        spinnerPrimeraAsignacion.setOnItemSelectedListener(this);

        spinnerSegundaAsignacion = (Spinner)findViewById(R.id.spinnerSegundaAsignacion);
        String [] spSegundaAsignacion = {"Seleccionar Asignacion", "Lectores y Maestros", "Primera Conversacion", "Primera Revisita", "Segunda Revisita", "Curso Biblico", "Discurso"};
        ArrayAdapter <String> adapterSegundaAsignacion = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spSegundaAsignacion);
        spinnerSegundaAsignacion.setAdapter(adapterSegundaAsignacion);
        spinnerSegundaAsignacion.setOnItemSelectedListener(this);

        spinnerTerceraAsignacion = (Spinner)findViewById(R.id.spinnerTerceraAsignacion);
        String [] spTerceraAsignacion = {"Seleccionar Asignacion", "Lectores y Maestros", "Primera Conversacion", "Primera Revisita", "Segunda Revisita", "Curso Biblico", "Discurso"};
        ArrayAdapter <String> adapterTerceraAsignacion = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spTerceraAsignacion);
        spinnerTerceraAsignacion.setAdapter(adapterTerceraAsignacion);
        spinnerTerceraAsignacion.setOnItemSelectedListener(this);
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
