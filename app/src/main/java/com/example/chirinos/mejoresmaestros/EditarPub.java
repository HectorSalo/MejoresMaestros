package com.example.chirinos.mejoresmaestros;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class EditarPub extends AppCompatActivity {

    private ImageView imagePub;
    private EditText nombrePub, apellidoPub, telefono, correo;
    private TextView fdiscurso, fayudante, fsustitucion;
    private RadioButton radioHombre, radioMujer;
    private String sidPub;
    private Integer idPb, dia, mes, anual, diaAsignacion, mesAsignacion, anualAsignacion, diaAyudante, mesAyudante, anualAyudante, diaSust, mesSust, anualSust;
    private FloatingActionButton fab_EditImage;


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
        idPb = myBundle.getInt("idEditar");
        sidPub = String.valueOf(idPb);

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
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase dbEditar = conect.getWritableDatabase();

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


                ContentValues registro = new ContentValues();
                registro.put("nombre", NombrePub);
                registro.put("apellido", ApellidoPub);
                registro.put("telefono", Telefono);
                registro.put("correo", Correo);
                registro.put("diaasignacion", diaDiscurso);
                registro.put("mesasignacion", mesDiscurso);
                registro.put("anualasignacion", anualDiscurso);
                registro.put("diaayudante", diaAyuda);
                registro.put("mesayudante", mesAyuda);
                registro.put("anualayudante", anualAyuda);
                registro.put("diasust", diaSustitucion);
                registro.put("messust", mesSustitucion);
                registro.put("anualsust", anualSustitucion);


                if (radioHombre.isChecked()) {
                    registro.put("genero", "Hombre");
                } else if (radioMujer.isChecked()) {
                    registro.put("genero", "Mujer");
                }

                dbEditar.update("publicadores", registro, "idPublicador=" + sidPub, null);
                dbEditar.close();

                Toast.makeText(this, "Publicador modificado ", Toast.LENGTH_SHORT).show();
                Intent myintent = new Intent(this, PrincipalActivity.class);
                startActivity(myintent);
                finish();
    } else {
                Toast.makeText(getApplicationContext(), "Hay campos obligatorios vacios", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Hay campos obligatorios vacios", Toast.LENGTH_SHORT).show();

        }

    }

    private void cargarDetalles () {

        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sidPub, null);

        if (fila.moveToFirst()) {
            nombrePub.setText(fila.getString(1));
            apellidoPub.setText(fila.getString(2));
            correo.setText(fila.getString(4));
            telefono.setText(fila.getString(3));

            if (fila.getInt(7) == 0 || fila.getInt(8) == 0 || fila.getInt(9) == 0) {
                fdiscurso.setText("Seleccione Fecha");
            } else {
                fdiscurso.setText(fila.getString(7) + "/" + fila.getString(8) + "/" + fila.getString(9));
            }

            if (fila.getInt(10) == 0 || fila.getInt(11) == 0 || fila.getInt(12) == 0) {
                fayudante.setText("Seleccione Fecha");
            } else {
                fayudante.setText(fila.getString(10) + "/" + fila.getString(11) + "/" + fila.getString(12));
            }

            if (fila.getInt(13) == 0 || fila.getInt(14) == 0 || fila.getInt(15) == 0) {
                fsustitucion.setText("Seleccione Fecha");
            } else {
                fsustitucion.setText(fila.getString(13) + "/" + fila.getString(14) + "/" + fila.getString(15));
            }

            if (fila.getString(5).equals("Hombre")) {
                imagePub.setImageResource(R.mipmap.ic_caballero);
            } else if (fila.getString(5).equals("Mujer")) {
                imagePub.setImageResource(R.mipmap.ic_dama);
            }

            db.close();

        }
    }

    private void cargarImagen () {

        Intent myintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        myintent.setType("image/");
        startActivityForResult(myintent.createChooser(myintent, "Seleccione ubicacion"), 10);

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
