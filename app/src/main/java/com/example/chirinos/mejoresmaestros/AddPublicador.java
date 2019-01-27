package com.example.chirinos.mejoresmaestros;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddPublicador extends AppCompatActivity {

    private EditText editNombrePub, editApellidoPub, editTelefonoPub, editCorreoPub;
    private RadioButton radioMasculino, radioFemenino;
    private Button buttonAgregar, buttonCancelar;
    private String NombrePub, ApellidoPub, Telefono, Correo;
    private ProgressDialog progress;


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
            //GuardarPublicador();
            progress = new ProgressDialog(AddPublicador.this);
            progress.setMessage("Guardando...");
            progress.show();
            guardarFirebase();
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

    //Metodo prueba firebase
    private void guardarFirebase() {
        // Access a Cloud Firestore instance from your Activity
        NombrePub = editNombrePub.getText().toString();
        ApellidoPub = editApellidoPub.getText().toString();
        Telefono = editTelefonoPub.getText().toString();
        Correo = editCorreoPub.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (!NombrePub.isEmpty() && !ApellidoPub.isEmpty()) {
            if (radioMasculino.isChecked() || radioFemenino.isChecked()) {


                Map<String, Object> publicador = new HashMap<>();
                publicador.put("nombre", NombrePub);
                publicador.put("apellido", ApellidoPub);
                publicador.put("telefono", Telefono);
                publicador.put("correo", Correo);
                publicador.put("habilitado", true);


                if (radioMasculino.isChecked()) {
                    publicador.put("genero", "Hombre");
                } else if (radioFemenino.isChecked()) {
                    publicador.put("genero", "Mujer");
                }

                db.collection("publicadores").add(publicador).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                progress.dismiss();
                                Toast.makeText(getApplicationContext(), "Guardado exitosamente", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progress.dismiss();
                                Toast.makeText(getApplicationContext(), "Error al guadar. Intente nuevamente", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

            } else {
                progress.dismiss();
                Toast.makeText(getApplicationContext(), "Hay campos obligatorios vacíos", Toast.LENGTH_SHORT).show();
            }
        } else {
            progress.dismiss();
            Toast.makeText(getApplicationContext(), "Hay campos obligatorios vacíos", Toast.LENGTH_SHORT).show();
        }
    }


}
