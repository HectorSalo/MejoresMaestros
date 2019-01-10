package com.example.chirinos.mejoresmaestros;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class AddPublicador extends AppCompatActivity {

    EditText editNombrePub, editApellidoPub, editTelefonoPub, editCorreoPub;
    RadioButton radioMasculino, radioFemenino;
    Button buttonAgregar, buttonCancelar;
    String NombrePub, ApellidoPub, Telefono, Correo;

    ArrayList<ConstructorPublicadores> listPublicadores;
    AdapterPublicadores adapterPublicadores;

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
            GuardarPublicador();

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


    public void GuardarPublicador () {

        AdminSQLiteOpenHelper conectDB = new AdminSQLiteOpenHelper(getApplicationContext(), "VMC", null, AdminSQLiteOpenHelper.VERSION);

        SQLiteDatabase db = conectDB.getWritableDatabase();

        NombrePub = editNombrePub.getText().toString();
        ApellidoPub = editApellidoPub.getText().toString();
        Telefono = editTelefonoPub.getText().toString();
        Correo = editCorreoPub.getText().toString();


        if (!NombrePub.isEmpty() && !ApellidoPub.isEmpty()) {
            if (radioMasculino.isChecked() || radioFemenino.isChecked()) {


                ContentValues registro = new ContentValues();
                registro.put("nombre", NombrePub);
                registro.put("apellido", ApellidoPub);
                registro.put("telefono", Telefono);
                registro.put("correo", Correo);


                if (radioMasculino.isChecked()) {
                    registro.put("genero", "Hombre");
                } else if (radioFemenino.isChecked()) {
                    registro.put("genero", "Mujer");
                }

                db.insert("publicadores", null, registro);
                db.close();

                Toast.makeText(getApplicationContext(), "Guardado correctamente", Toast.LENGTH_SHORT).show();

                finish();
                Intent intent = new Intent(this, PrincipalActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), "Hay campos obligatorios vacios", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Hay campos obligatorios vacios", Toast.LENGTH_SHORT).show();

        }

    }


}
