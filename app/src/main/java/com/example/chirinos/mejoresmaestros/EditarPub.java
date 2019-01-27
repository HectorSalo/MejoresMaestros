package com.example.chirinos.mejoresmaestros;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class EditarPub extends AppCompatActivity {

    private ImageView imagePub;
    private EditText nombrePub, apellidoPub, telefono, correo;
    private TextView fdiscurso, fayudante, fsustitucion;
    private RadioButton radioHombre, radioMujer;
    private CheckBox cbHabilitar;
    private String idPb;
    private Integer dia, mes, anual, diaAsignacion, mesAsignacion, anualAsignacion, diaAyudante, mesAyudante, anualAyudante, diaSust, mesSust, anualSust;
    private FloatingActionButton fab_EditImage;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pub);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEditPub);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        imagePub = (ImageView) findViewById(R.id.imageBtPublicador);
        nombrePub = (EditText) findViewById(R.id.etNombre);
        apellidoPub = (EditText) findViewById(R.id.etApellido);
        telefono = (EditText) findViewById(R.id.etTelefono);
        correo = (EditText) findViewById(R.id.etCorreo);
        radioHombre = (RadioButton) findViewById(R.id.radioHombre);
        radioMujer = (RadioButton) findViewById(R.id.radioMujer);
        cbHabilitar = (CheckBox) findViewById(R.id.cbHabilitar);
        fdiscurso = (TextView) findViewById(R.id.etfdiscurso);
        fayudante = (TextView) findViewById(R.id.etfayudante);
        fsustitucion = (TextView) findViewById(R.id.etfsustitucion);
        fab_EditImage = (FloatingActionButton) findViewById(R.id.fabEditImage);



        //Ignora restricciones para poder usar la camara. Mejorar: https://stackoverflow.com/questions/48117511/exposed-beyond-app-through-clipdata-item-geturi
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());



        fdiscurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirFechaDiscurso();
            }
        });

        fayudante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirFechaAyudante();
            }
        });

        fsustitucion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirFechaSust();
            }
        });

        fab_EditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen ();
            }
        });

        Bundle myBundle = this.getIntent().getExtras();
        idPb = myBundle.getString("idEditar");

        progress = new ProgressDialog(EditarPub.this);
        progress.setMessage("Cargando...");
        progress.show();
        cargarDetalles ();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_editar_pub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_save_pub) {
            editarpublicador();
            return true;
        } else if (id == R.id.menu_cancel_pub) {
            Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void elegirFechaDiscurso () {

        final Calendar calendario = Calendar.getInstance();

        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH);
        anual = calendario.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fdiscurso.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                diaAsignacion = dayOfMonth;
                mesAsignacion = month + 1;
                anualAsignacion = year;
            }
        }, anual, mes , dia);
        datePickerDialog.show();

    }

    private void elegirFechaAyudante() {
        final Calendar calendario = Calendar.getInstance();

        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH);
        anual = calendario.get(Calendar.YEAR);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fayudante.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                diaAyudante = dayOfMonth;
                mesAyudante = month + 1;
                anualAyudante = year;
            }
        }, anual, mes , dia);
        datePickerDialog.show();
    }

    private void elegirFechaSust() {
        final Calendar calendario = Calendar.getInstance();

        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH);
        anual = calendario.get(Calendar.YEAR);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fsustitucion.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                diaSust = dayOfMonth;
                mesSust = month + 1;
                anualSust = year;
            }
        }, anual, mes , dia);
        datePickerDialog.show();
    }

    @SuppressLint("ResourceType")
    private void editarpublicador() {
        progress = new ProgressDialog(EditarPub.this);
        progress.setMessage("Guardando...");
        progress.show();
        FirebaseFirestore dbEditar = FirebaseFirestore.getInstance();

        String NombrePub = nombrePub.getText().toString();
        String ApellidoPub = apellidoPub.getText().toString();
        String Telefono = telefono.getText().toString();
        String Correo = correo.getText().toString();
        String diaDiscurso = String.valueOf(diaAsignacion);
        String mesDiscurso = String.valueOf(mesAsignacion);
        String anualDiscurso = String.valueOf(anualAsignacion);
        String diaAyuda = String.valueOf(diaAyudante);
        String mesAyuda = String.valueOf(mesAyudante);
        String anualAyuda = String.valueOf(anualAyudante);
        String diaSustitucion = String.valueOf(diaSust);
        String mesSustitucion = String.valueOf(mesSust);
        String anualSustitucion = String.valueOf(anualSust);




        if (!NombrePub.isEmpty() && !ApellidoPub.isEmpty()) {
            if (radioHombre.isChecked() || radioMujer.isChecked()) {

                Map<String, Object> publicador = new HashMap<>();
                publicador.put("nombre", NombrePub);
                publicador.put("apellido", ApellidoPub);
                publicador.put("telefono", Telefono);
                publicador.put("correo", Correo);


                if (radioHombre.isChecked()) {
                    publicador.put("genero", "Hombre");
                } else if (radioMujer.isChecked()) {
                    publicador.put("genero", "Mujer");
                }

                if (cbHabilitar.isChecked()) {
                    publicador.put("habilitado", false);
                } else {
                    publicador.put("habilitado", true);
                }

                dbEditar.collection("publicadores").document(idPb).set(publicador).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), "Publicador modificado ", Toast.LENGTH_SHORT).show();
                        Intent myintent = new Intent(getApplicationContext(), PrincipalActivity.class);
                        startActivity(myintent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), "Error al guardar. Intente nuevamente", Toast.LENGTH_SHORT).show();
                    }
                });


    } else {
                Toast.makeText(getApplicationContext(), "Hay campos obligatorios vacíos", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Hay campos obligatorios vacíos", Toast.LENGTH_SHORT).show();

        }

    }

    private void cargarDetalles () {

        FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
        CollectionReference reference = dbFirestore.collection("publicadores");

        reference.document(idPb).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    nombrePub.setText(doc.getString("nombre"));
                    apellidoPub.setText(doc.getString("apellido"));
                    correo.setText(doc.getString("correo"));
                    telefono.setText(doc.getString("telefono"));

                    if (doc.getString("genero").equals("Hombre")) {
                        radioHombre.setChecked(true);
                        imagePub.setImageResource(R.mipmap.ic_caballero);
                    } else if (doc.getString("genero").equals("Mujer")) {
                        imagePub.setImageResource(R.mipmap.ic_dama);
                        radioMujer.setChecked(true);
                    }

                    if (doc.getBoolean("habilitado") == false) {
                        cbHabilitar.setChecked(true);
                    }

                    progress.dismiss();

                } else {
                    progress.dismiss();
                    Toast.makeText(getApplicationContext(), "Error al cargar. Intente nuevamente", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }

    private void cargarImagen () {

        Intent myintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        myintent.setType("image/");
        startActivityForResult(myintent.createChooser(myintent, "Seleccione ubicación"), 10);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri mipath = data.getData();
            imagePub.setImageURI(mipath);

        }

    }
}
