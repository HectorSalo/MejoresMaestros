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

public class VerActivity extends AppCompatActivity {

    private ImageView imagenPublicador;
    private TextView tvNombre, tvApellido, tvTelefono, tvCorreo, tvfAsignacion, tvfAyudante, tvfSustitucion;
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
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(this, "VMC", null, 8);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM publicadores WHERE idPublicador =" + sidPub, null);

        if (fila.moveToFirst()) {
            tvNombre.setText(fila.getString(1));
            tvApellido.setText(fila.getString(2));
            tvCorreo.setText(fila.getString(4));
            tvTelefono.setText(fila.getString(3));
            tvfAsignacion.setText(fila.getString(6));
            tvfAyudante.setText(fila.getString(7));
            tvfSustitucion.setText(fila.getString(8));

            if (fila.getString(5).equals("Hombre")) {
                imagenPublicador.setImageResource(R.mipmap.ic_caballero);
            } else if (fila.getString(5).equals("Mujer")) {
                imagenPublicador.setImageResource(R.mipmap.ic_dama);
            }

            db.close();

        } else {
            Toast.makeText(this, "No existe", Toast.LENGTH_SHORT).show();
        }


    }


}
