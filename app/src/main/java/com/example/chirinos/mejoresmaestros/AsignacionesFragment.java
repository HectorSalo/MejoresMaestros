package com.example.chirinos.mejoresmaestros;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AsignacionesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AsignacionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AsignacionesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Calendar calendar;
    private String strSemana;
    private Integer dia, mes, anual, semanaActual, diaSelec, mesSelec, anualSelec, semanaSelec;
    private TextView tvFecha, tvLectura, tvAsignacion1, tvAsignacion2, tvAsignacion3, tvEncargado1, tvAyudante1, tvEncargado2, tvAyudante2, tvEncargado3, tvAyudante3, tvAviso;
    private TextView tvLecturaSala2, tvAsignacion1Sala2, tvAsignacion2Sala2, tvAsignacion3Sala2, tvEncargado1Sala2, tvAyudante1Sala2, tvEncargado2Sala2, tvAyudante2Sala2, tvEncargado3Sala2, tvAyudante3Sala2;
    private ImageButton imDate, imbEditSala1, imbEditSala2;
    private FloatingActionButton fabSala1, fabSala2;
    private LinearLayout layoutSala1, layoutSala2;
    private View vista;

    public AsignacionesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AsignacionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AsignacionesFragment newInstance(String param1, String param2) {
        AsignacionesFragment fragment = new AsignacionesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_asignaciones, container, false);
        layoutSala1 = (LinearLayout) vista.findViewById(R.id.layoutSala1);
        layoutSala2 = (LinearLayout) vista.findViewById(R.id.layoutSala2);
        tvFecha = (TextView) vista.findViewById(R.id.tvFechaAsig);

        tvLectura = (TextView) vista.findViewById(R.id.textViewPubsInicioLectura);
        tvAsignacion1 = (TextView) vista.findViewById(R.id.inicioAsignacion1);
        tvAsignacion2 = (TextView) vista.findViewById(R.id.inicioAsignacion2);
        tvAsignacion3 = (TextView) vista.findViewById(R.id.inicioAsignacion3);
        tvEncargado1 = (TextView) vista.findViewById(R.id.textViewPubsInicioAsignacion1);
        tvAyudante1 = (TextView) vista.findViewById(R.id.textViewPubsInicioAsignacionAyu1);
        tvEncargado2 = (TextView) vista.findViewById(R.id.textViewPubsInicioAsignacion2);
        tvAyudante2 = (TextView) vista.findViewById(R.id.textViewPubsInicioAsignacionAyu2);
        tvEncargado3 = (TextView) vista.findViewById(R.id.textViewPubsInicioAsignacion3);
        tvAyudante3 = (TextView) vista.findViewById(R.id.textViewPubsInicioAsignacionAyu3);

        tvLecturaSala2 = (TextView) vista.findViewById(R.id.textViewPubsInicioLecturaSala2);
        tvAsignacion1Sala2 = (TextView) vista.findViewById(R.id.inicioAsignacion1Sala2);
        tvAsignacion2Sala2 = (TextView) vista.findViewById(R.id.inicioAsignacion2Sala2);
        tvAsignacion3Sala2 = (TextView) vista.findViewById(R.id.inicioAsignacion3Sala2);
        tvEncargado1Sala2 = (TextView) vista.findViewById(R.id.textViewPubsInicioAsignacion1Sala2);
        tvAyudante1Sala2 = (TextView) vista.findViewById(R.id.textViewPubsInicioAsignacionAyu1Sala2);
        tvEncargado2Sala2 = (TextView) vista.findViewById(R.id.textViewPubsInicioAsignacion2Sala2);
        tvAyudante2Sala2 = (TextView) vista.findViewById(R.id.textViewPubsInicioAsignacionAyu2Sala2);
        tvEncargado3Sala2 = (TextView) vista.findViewById(R.id.textViewPubsInicioAsignacion3Sala2);
        tvAyudante3Sala2 = (TextView) vista.findViewById(R.id.textViewPubsInicioAsignacionAyu3Sala2);

        tvAviso = (TextView) vista.findViewById(R.id.tvAviso);
        imDate = (ImageButton) vista.findViewById(R.id.iBDateSelec);
        imbEditSala1 = (ImageButton) vista.findViewById(R.id.imageSala1);
        imbEditSala2 = (ImageButton) vista.findViewById(R.id.imageSala2);
        calendar = Calendar.getInstance();
        semanaSelec=0;


        fabSala1 = (FloatingActionButton) vista.findViewById(R.id.fabSala1);
        fabSala1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SeleccionarPubAsig.class);
                Bundle myBundle = new Bundle();
                myBundle.putInt("Sala", 1);
                intent.putExtras(myBundle);
                startActivity(intent);

            }
        });


        fabSala2 = (FloatingActionButton) vista.findViewById(R.id.fabSala2);
        fabSala2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SeleccionarPubAsig.class);
                Bundle myBundle = new Bundle();
                myBundle.putInt("Sala", 2);
                intent.putExtras(myBundle);
                startActivity(intent);
            }
        });

        imDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFechaSelec ();
            }
        });

        imbEditSala1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence [] opciones = {"Sustituir Publicador", "Eliminar semana programada", "Cancelar"};
                android.support.v7.app.AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.Theme_Dialog_Publicador);
                dialog.setTitle("Seleccione una opción");
                dialog.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (opciones[which].equals("Sustituir Publicador")) {
                            editSala1();
                        } else if (opciones[which].equals("Eliminar semana programada")) {
                            eliminarSala1();
                            dialog.dismiss();
                        } else if (opciones[which].equals("Cancelar")) {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });
        imbEditSala2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence [] opciones = {"Sustituir Publicador", "Eliminar semana programada", "Cancelar"};
                android.support.v7.app.AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.Theme_Dialog_Publicador);
                dialog.setTitle("Seleccione una opción");
                dialog.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (opciones[which].equals("Sustituir Publicador")) {
                            editSala2();
                        } else if (opciones[which].equals("Eliminar semana programada")) {
                            eliminarSala2();
                            dialog.dismiss();
                        } else if (opciones[which].equals("Cancelar")) {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });

        cargarFechaActual ();
        return vista;
    }

    private void cargarFechaActual() {
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        anual = calendar.get(Calendar.YEAR);
        semanaActual = calendar.get(Calendar.WEEK_OF_YEAR);
        cargarSala2(semanaActual);
        cargarSala1(semanaActual);

    }

    private void cargarFechaSelec () {
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        anual = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                diaSelec = dayOfMonth;
                mesSelec = month;
                anualSelec = year;
                calendar.set(anualSelec, mesSelec, diaSelec);
                semanaSelec = calendar.get(Calendar.WEEK_OF_YEAR);
                cargarSala2(semanaSelec);
                cargarSala1(semanaSelec);


            }
        }, anual, mes , dia);
        datePickerDialog.show();

    }

    private void cargarSala1(int i) {
        strSemana = String.valueOf(i);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(getContext(), "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM sala1 WHERE semana =" + strSemana, null);

        if (fila.moveToFirst()) {
            if (fila.getString(8).equals("Asamblea")) {
                tvFecha.setText(fila.getString(9) + "/" + fila.getString(10) + "/" + fila.getString(11));
                tvAviso.setVisibility(View.VISIBLE);
                tvAviso.setText("Sin Asignaciones por Asamblea");
                layoutSala1.setVisibility(View.INVISIBLE);
                layoutSala2.setVisibility(View.INVISIBLE);
                db.close();
            } else {
                tvAviso.setVisibility(View.INVISIBLE);
                layoutSala1.setVisibility(View.VISIBLE);
                layoutSala2.setVisibility(View.VISIBLE);
                tvLectura.setText(fila.getString(1));
                tvEncargado1.setText(fila.getString(2));
                tvAyudante1.setText(fila.getString(3));
                tvEncargado2.setText(fila.getString(4));
                tvAyudante2.setText(fila.getString(5));
                tvEncargado3.setText(fila.getString(6));
                tvAyudante3.setText(fila.getString(7));
                tvAsignacion1.setText(fila.getString(12) + ": ");
                tvAsignacion2.setText(fila.getString(13) + ": ");
                tvAsignacion3.setText(fila.getString(14) + ": ");
                tvFecha.setText(fila.getString(9) + "/" + fila.getString(10) + "/" + fila.getString(11));
                db.close();
            }

        } else {
            tvFecha.setText("Seleccione una fecha");
            tvAviso.setText("Sin asignaciones programadas en esta semana");
            tvAviso.setVisibility(View.VISIBLE);
            layoutSala1.setVisibility(View.INVISIBLE);
            layoutSala2.setVisibility(View.INVISIBLE);
            db.close();
        }
    }

    private void cargarSala2 (int i) {
        strSemana = String.valueOf(i);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(getContext(), "VMC", null, AdminSQLiteOpenHelper.VERSION);
        SQLiteDatabase db = conect.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM sala2 WHERE semana =" + strSemana, null);

        if (fila.moveToFirst()) {
                layoutSala2.setVisibility(View.VISIBLE);
                tvLecturaSala2.setText(fila.getString(1));
                tvEncargado1Sala2.setText(fila.getString(2));
                tvAyudante1Sala2.setText(fila.getString(3));
                tvEncargado2Sala2.setText(fila.getString(4));
                tvAyudante2Sala2.setText(fila.getString(5));
                tvEncargado3Sala2.setText(fila.getString(6));
                tvAyudante3Sala2.setText(fila.getString(7));
                tvAsignacion1Sala2.setText(fila.getString(12) + ": ");
                tvAsignacion2Sala2.setText(fila.getString(13) + ": ");
                tvAsignacion3Sala2.setText(fila.getString(14) + ": ");

                db.close();

        } else {
            tvLecturaSala2.setText("Sin programar");
            tvEncargado1Sala2.setText("");
            tvAyudante1Sala2.setText("");
            tvEncargado2Sala2.setText("");
            tvAyudante2Sala2.setText("");
            tvEncargado3Sala2.setText("");
            tvAyudante3Sala2.setText("");
            tvAsignacion1Sala2.setText("");
            tvAsignacion2Sala2.setText("");
            tvAsignacion3Sala2.setText("");
            db.close();
        }
    }

    private void editSala1() {
        Intent myIntent = new Intent(getContext(), EditAsignacionesActivity.class);
        Bundle myBundle = new Bundle();
        myBundle.putInt("sala", 1);
        if(semanaSelec == 0) {
            myBundle.putInt("semana", semanaActual);
            myBundle.putInt("dia", dia);
            myBundle.putInt("mes", mes);
            myBundle.putInt("anual", anual);
        } else {
            myBundle.putInt("semana", semanaSelec);
            myBundle.putInt("dia", diaSelec);
            myBundle.putInt("mes", mesSelec);
            myBundle.putInt("anual", anualSelec);
        }
        myIntent.putExtras(myBundle);
        startActivity(myIntent);
    }

    private void editSala2 () {
        Intent myIntent = new Intent(getContext(), EditAsignacionesActivity.class);
        Bundle myBundle = new Bundle();
        myBundle.putInt("sala", 2);
        if (semanaSelec == 0) {
            myBundle.putInt("semana", semanaActual);
            myBundle.putInt("dia", dia);
            myBundle.putInt("mes", mes);
            myBundle.putInt("anual", anual);
        } else {
            myBundle.putInt("semana", semanaSelec);
            myBundle.putInt("dia", diaSelec);
            myBundle.putInt("mes", mesSelec);
            myBundle.putInt("anual", anualSelec);
        }
        myIntent.putExtras(myBundle);
        startActivity(myIntent);
    }

    private void eliminarSala1 () {
        final String strSemanaActual = String.valueOf(semanaActual);
        final String strSemanaSelec = String.valueOf(semanaSelec);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(getContext(), "VMC", null, AdminSQLiteOpenHelper.VERSION);
        final SQLiteDatabase dbEliminar = conect.getWritableDatabase();

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.Theme_Dialog_Publicador);
        dialog.setTitle("Confirmar");
        dialog.setMessage("¿Eliminar la programación de esta semana?");

        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (semanaSelec == 0) {
                    dbEliminar.delete("sala1", "semana=" + strSemanaActual, null);
                } else {
                    dbEliminar.delete("sala1", "semana=" + strSemanaSelec, null);
                }

                dbEliminar.close();
                Toast.makeText(getContext(), "Eliminado", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Intent myIntent = new Intent(getContext(), PrincipalActivity.class);
                startActivity(myIntent);
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void eliminarSala2 () {
        final String strSemanaActual = String.valueOf(semanaActual);
        final String strSemanaSelec = String.valueOf(semanaSelec);
        AdminSQLiteOpenHelper conect = new AdminSQLiteOpenHelper(getContext(), "VMC", null, AdminSQLiteOpenHelper.VERSION);
        final SQLiteDatabase dbEliminar = conect.getWritableDatabase();

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.Theme_Dialog_Publicador);
        dialog.setTitle("Confirmar");
        dialog.setMessage("¿Eliminar la programación de esta semana?");

        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (semanaSelec == 0) {
                    dbEliminar.delete("sala2", "semana=" + strSemanaActual, null);
                } else {
                    dbEliminar.delete("sala2", "semana=" + strSemanaSelec, null);
                }

                dbEliminar.close();
                Toast.makeText(getContext(), "Eliminado", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Intent myIntent = new Intent(getContext(), PrincipalActivity.class);
                startActivity(myIntent);
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
