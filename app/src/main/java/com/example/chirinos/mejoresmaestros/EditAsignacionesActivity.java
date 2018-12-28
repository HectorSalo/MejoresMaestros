package com.example.chirinos.mejoresmaestros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class EditAsignacionesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerAnterior, spinnerNuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_asignaciones);

        spinnerAnterior = (Spinner)findViewById(R.id.spinner2);
        String [] spAnterior = {"Seleccionar Publicador", "Item 1", "Item 2"};
        ArrayAdapter<String> adapterAnterior = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spAnterior);
        spinnerAnterior.setAdapter(adapterAnterior);
        spinnerAnterior.setOnItemSelectedListener(this);

        spinnerNuevo = (Spinner)findViewById(R.id.spinner3);
        String [] spNuevo = {"Seleccionar Publicador", "Item 1", "Item 2"};
        ArrayAdapter <String> adapterNuevo = new ArrayAdapter<String>(this, R.layout.spinner_personalizado, spNuevo);
        spinnerNuevo.setAdapter(adapterNuevo);
        spinnerNuevo.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
