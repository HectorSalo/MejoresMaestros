package com.example.chirinos.mejoresmaestros;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
        tvAviso = (TextView) vista.findViewById(R.id.tvAviso);
        calendar = Calendar.getInstance();


        fabSala1 = (FloatingActionButton) vista.findViewById(R.id.fabSala1);
        fabSala1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SeleccionarPubAsig.class);
                startActivity(intent);

            }
        });

        fabSala2 = (FloatingActionButton) vista.findViewById(R.id.fabSala2);
        fabSala2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Sala2Activity.class);
                Bundle myBundle = new Bundle();
                myBundle.putInt("llenarSala1", 0);
                intent.putExtras(myBundle);
                startActivity(intent);
            }
        });

        tvFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFechaSelec ();
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

        } else {
            tvFecha.setText("Seleccione una fecha");
            tvAviso.setVisibility(View.VISIBLE);
            layoutSala1.setVisibility(View.INVISIBLE);
            layoutSala2.setVisibility(View.INVISIBLE);
            db.close();
        }
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
