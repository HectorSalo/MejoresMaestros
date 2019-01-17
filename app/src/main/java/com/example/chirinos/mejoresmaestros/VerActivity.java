package com.example.chirinos.mejoresmaestros;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VerActivity extends AppCompatActivity {

    private ImageView imagenPublicador;
    private TextView tvNombre, tvApellido, tvTelefono, tvCorreo, tvfAsignacion, tvfAyudante, tvfSustitucion, tvHabilitar;
    private Integer idPublicador;
    private String sidPub;
    private FloatingActionButton fabClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        imagenPublicador = (ImageView)findViewById(R.id.imageViewPublicador);
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvApellido = (TextView) findViewById(R.id.tvApellido);
        tvTelefono = (TextView) findViewById(R.id.tvTelefono);
        tvCorreo = (TextView) findViewById(R.id.tvCorro);
        tvfAsignacion = (TextView) findViewById(R.id.textViewfdiscurso);
        tvfAyudante = (TextView) findViewById(R.id.textViewfayudante);
        tvfSustitucion = (TextView) findViewById(R.id.textViewfsustitucion);
        tvHabilitar = (TextView) findViewById(R.id.tvHabilitar);
        fabClose = (FloatingActionButton) findViewById(R.id.fabClose);


        Bundle miBundle = this.getIntent().getExtras();
        idPublicador = miBundle.getInt("id");

        fabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        consultaDetalles();
    }

    private void consultaDetalles() {

        sidPub = String.valueOf(idPublicador);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sidPub, null);

        if (fila.moveToFirst()) {


            tvNombre.setText(fila.getString(1));
            tvApellido.setText(fila.getString(2));
            tvCorreo.setText(fila.getString(4));
            tvTelefono.setText(fila.getString(3));

            if (fila.getInt(7) == 0 || fila.getInt(8) == 0 || fila.getInt(9) == 0) {
                tvfAsignacion.setText("");
            } else {
                tvfAsignacion.setText(fila.getString(7) + "/" + fila.getString(8) + "/" + fila.getString(9));
            }

            if (fila.getInt(10) == 0 || fila.getInt(11) == 0 || fila.getInt(12) == 0) {
                tvfAyudante.setText("");
            } else {
                tvfAyudante.setText(fila.getString(10) + "/" + fila.getString(11) + "/" + fila.getString(12));
            }

            if (fila.getInt(13) == 0 || fila.getInt(14) == 0 || fila.getInt(15) == 0) {
                tvfSustitucion.setText("");
            } else {
                tvfSustitucion.setText(fila.getString(13) + "/" + fila.getString(14) + "/" + fila.getString(15));
            }

            if (fila.getString(5).equals("Hombre")) {
                imagenPublicador.setImageResource(R.mipmap.ic_caballero);
            } else if (fila.getString(5).equals("Mujer")) {
                imagenPublicador.setImageResource(R.mipmap.ic_dama);
            }

            if (fila.getInt(16) == 1) {
                tvHabilitar.setText("Inhabilitado");
            } else if (fila.getInt(16) == 0) {
                tvHabilitar.setText("");
            }

            db.close();

        }


    }




}
