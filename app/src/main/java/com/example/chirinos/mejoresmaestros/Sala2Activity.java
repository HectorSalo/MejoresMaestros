package com.example.chirinos.mejoresmaestros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Sala2Activity extends AppCompatActivity {

    Button buttonGuardar, buttonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala2);

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
}
