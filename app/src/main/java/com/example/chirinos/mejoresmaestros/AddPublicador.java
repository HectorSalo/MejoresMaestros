package com.example.chirinos.mejoresmaestros;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddPublicador extends AppCompatActivity {

    EditText editNombrePub, editApellidoPub, editTelefonoPub, editCorreoPub;
    RadioButton radioMasculino, radioFemenino;
    Button buttonAgregar, buttonCancelar;
    String NombrePub, ApellidoPub, Telefono, Correo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_publicador);

    editNombrePub = (EditText)findViewById(R.id.editNombre);
    editApellidoPub = (EditText)findViewById(R.id.editApellido);
    editTelefonoPub = (EditText)findViewById(R.id.editTelefono);
    editCorreoPub = (EditText)findViewById(R.id.editCorreo);
    radioMasculino = (RadioButton)findViewById(R.id.radioButtonMasculino);
    radioFemenino = (RadioButton)findViewById(R.id.radioButtonFemenino);
    buttonAgregar = (Button)findViewById(R.id.bottomAgregar);
    buttonCancelar = (Button)findViewById(R.id.bottomCancelar);




    buttonAgregar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NombrePub = editNombrePub.getText().toString();
            ApellidoPub = editApellidoPub.getText().toString();


            if (NombrePub.length() > 0 && ApellidoPub.length() > 0) {
                if (radioMasculino.isChecked() || radioFemenino.isChecked()) {

                    Toast.makeText(getApplicationContext(), "Guardado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Hay campos obligatorios vacios", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Hay campos obligatorios vacios", Toast.LENGTH_SHORT).show();

            }
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
