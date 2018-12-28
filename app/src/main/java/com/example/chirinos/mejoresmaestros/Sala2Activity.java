package com.example.chirinos.mejoresmaestros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Sala2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerFecha, spinnerLectura, spinnerEncargado1, spinnerAyudante1, spinnerEncargado2, spinnerAyudante2, spinnerEncargado3, spinnerAyudante3;
    Button buttonGuardar, buttonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala2);

        spinnerFecha = (Spinner)findViewById(R.id.spinnerFecha);
        String [] spFechas = {"Seleccionar Fecha", "Item 1", "Item 2"};
        ArrayAdapter<String> adapterFechas = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spFechas);
        spinnerFecha.setAdapter(adapterFechas);
        spinnerFecha.setOnItemSelectedListener(this);

        spinnerLectura = (Spinner)findViewById(R.id.spinnerLectura);
        String [] spLectura = {"Seleccionar Publicador", "Item 1", "Item 2"};
        ArrayAdapter <String> adapterLectura = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spLectura);
        spinnerLectura.setAdapter(adapterLectura);
        spinnerLectura.setOnItemSelectedListener(this);

        spinnerEncargado1 = (Spinner)findViewById(R.id.spinnerEncargado1);
        String [] spEncargado1 = {"Seleccionar Publicador", "Item 1", "Item 2"};
        ArrayAdapter <String> adapterEncargado1 = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spEncargado1);
        spinnerEncargado1.setAdapter(adapterEncargado1);
        spinnerEncargado1.setOnItemSelectedListener(this);

        spinnerAyudante1 = (Spinner)findViewById(R.id.spinnerAyudante1);
        String [] spAyudante1 = {"Seleccionar Publicador", "Item 1", "Item 2"};
        ArrayAdapter <String> adapterAyudante1 = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spAyudante1);
        spinnerAyudante1.setAdapter(adapterAyudante1);
        spinnerAyudante1.setOnItemSelectedListener(this);

        spinnerEncargado2 = (Spinner)findViewById(R.id.spinnerEncargado2);
        String [] spEncargado2 = {"Seleccionar Publicador", "Item 1", "Item 2"};
        ArrayAdapter <String> adapterEncargado2 = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spEncargado2);
        spinnerEncargado2.setAdapter(adapterEncargado2);
        spinnerEncargado2.setOnItemSelectedListener(this);

        spinnerAyudante2 = (Spinner)findViewById(R.id.spinnerAyudante2);
        String [] spAyudante2 = {"Seleccionar Publicador", "Item 1", "Item 2"};
        ArrayAdapter <String> adapterAyudante2 = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spAyudante2);
        spinnerAyudante2.setAdapter(adapterAyudante2);
        spinnerAyudante2.setOnItemSelectedListener(this);

        spinnerEncargado3 = (Spinner)findViewById(R.id.spinnerEncargado3);
        String [] spEncargado3 = {"Seleccionar Publicador", "Item 1", "Item 2"};
        ArrayAdapter <String> adapterEncargado3 = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spEncargado3);
        spinnerEncargado3.setAdapter(adapterEncargado3);
        spinnerEncargado3.setOnItemSelectedListener(this);

        spinnerAyudante3 = (Spinner)findViewById(R.id.spinnerAyudante3);
        String [] spAyudante3 = {"Seleccionar Publicador", "Item 1", "Item 2"};
        ArrayAdapter <String> adapterAyudante3 = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spAyudante3);
        spinnerAyudante3.setAdapter(adapterAyudante3);
        spinnerAyudante3.setOnItemSelectedListener(this);

        buttonGuardar = (Button)findViewById(R.id.buttonGuardar);
        buttonCancelar = (Button)findViewById(R.id.buttonCancelar);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
