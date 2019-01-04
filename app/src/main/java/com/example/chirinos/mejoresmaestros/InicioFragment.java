package com.example.chirinos.mejoresmaestros;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import java.util.concurrent.TimeUnit;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InicioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View vista;

    EditText editTextMinutos, editTextSegundos;
    FloatingActionButton fabStart, fabStop, fabPause;
    TextView textViewTemp;
    boolean isPaused;
    boolean isCenceled;
    long TiempoRestante;
    boolean isResume;
    String FORMAT = "%02d:%02d";
    long resultadoMinuto, resultadoSegundo, tiempoBase, intervaloDecrecer;
    Long minuto_ingresado, segundo_ingresado;

    private InicioFragment.OnFragmentInteractionListener mListener;

    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PublicadoresFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
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
        vista = inflater.inflate(R.layout.fragment_inicio, container, false);

        textViewTemp = (TextView) vista.findViewById(R.id.visorTiempo);
        editTextMinutos = (EditText) vista.findViewById(R.id.editTextMinutos);
        editTextSegundos = (EditText) vista.findViewById(R.id.editTextSegundos);
        fabStart = (FloatingActionButton) vista.findViewById(R.id.fabPlay);
        fabStop = (FloatingActionButton) vista.findViewById(R.id.fabStop);
        fabPause = (FloatingActionButton) vista.findViewById(R.id.fabPause);

        fabPause.setEnabled(false);
        fabStop.setEnabled(false);

        isPaused = false;
        isResume = true;
        isCenceled = false;
        TiempoRestante = 0;




        fabStart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                isPaused = false;
                isCenceled = false;
                isResume = true;


                if (editTextSegundos.getText().length() > 0 && editTextMinutos.getText().length() > 0) {
                    minuto_ingresado = Long.valueOf(String.valueOf(editTextMinutos.getText()));
                    segundo_ingresado = Long.valueOf(String.valueOf(editTextSegundos.getText()));
                    resultadoMinuto = minuto_ingresado * 60000;
                    resultadoSegundo = segundo_ingresado * 1000;
                    tiempoBase = resultadoMinuto + resultadoSegundo;
                } else if (editTextMinutos.getText().length() > 0 && editTextSegundos.getText().length() == 0){
                    minuto_ingresado = Long.valueOf(String.valueOf(editTextMinutos.getText()));
                    resultadoMinuto = minuto_ingresado * 60000;
                    resultadoSegundo = 0;
                    tiempoBase = resultadoMinuto + resultadoSegundo;
                } else if (editTextMinutos.getText().length() == 0 && editTextSegundos.getText().length() > 0) {
                    segundo_ingresado = Long.valueOf(String.valueOf(editTextSegundos.getText()));
                    resultadoMinuto = 0;
                    resultadoSegundo = segundo_ingresado * 1000;
                    tiempoBase = resultadoMinuto + resultadoSegundo;
                } else if (editTextMinutos.getText().length() == 0 && editTextSegundos.getText().length() == 0) {
                    tiempoBase = 0;
                    Toast.makeText(getContext(), "Debe ingresar un valor para iniciar", Toast.LENGTH_SHORT).show();
                }

                intervaloDecrecer = 1000;


                new CountDownTimer(tiempoBase, intervaloDecrecer) {

                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onTick(long millisUntilFinished) {

                             if (isPaused || isCenceled) {
                                 cancel();

                             }   else {
                                 fabStart.setVisibility(View.INVISIBLE);
                                 fabPause.setEnabled(true);
                                 fabStop.setEnabled(true);
                                 editTextMinutos.setEnabled(false);
                                 editTextSegundos.setEnabled(false);

                                 TiempoRestante = millisUntilFinished;

                                 textViewTemp.setText(""+ String.format(FORMAT,
                                         TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                         TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                             }
                    }

                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onFinish() {
                        textViewTemp.setText("Finalizado");
                        fabStart.setVisibility(View.VISIBLE);
                        fabStart.setEnabled(true);
                        fabPause.setEnabled(false);
                        fabStop.setEnabled(false);
                        isPaused = false;
                        isCenceled = false;
                    }
                }.start();

            }
        });

        fabStop.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                isCenceled = true;
                textViewTemp.setText("00:00");
                fabStart.setVisibility(View.VISIBLE);
                fabStart.setEnabled(true);
                fabPause.setEnabled(false);
                fabStop.setEnabled(false);
                editTextMinutos.setEnabled(true);
                editTextSegundos.setEnabled(true);
                fabPause.setImageResource(R.drawable.ic_action_pause);



            }
        });

        fabPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isResume) {
                    isPaused = true;
                    isResume = false;
                    fabStart.setEnabled(false);
                    fabPause.setImageResource(R.drawable.ic_action_play);

                } else  {
                    fabPause.setImageResource(R.drawable.ic_action_pause);
                    isPaused = false;
                    long tiempoBase = TiempoRestante;
                    long intervaloDecrecer = 1000;

                    new CountDownTimer(tiempoBase, intervaloDecrecer) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            if (isPaused || isCenceled) {
                                cancel();
                            } else {
                                textViewTemp.setText(""+ String.format(FORMAT,
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                                TiempoRestante = millisUntilFinished;
                            }
                        }

                        @SuppressLint("RestrictedApi")
                        @Override
                        public void onFinish() {
                            textViewTemp.setText("Finalizado");
                            fabStart.setVisibility(View.VISIBLE);
                            fabStart.setEnabled(true);
                            fabPause.setEnabled(false);
                            fabStop.setEnabled(false);
                            isPaused = false;
                            isCenceled = false;
                        }
                    }.start();
                    isResume = true;
                    fabStart.setEnabled(false);
                }
            }
        });

        return vista;


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
        if (context instanceof InicioFragment.OnFragmentInteractionListener) {
            mListener = (InicioFragment.OnFragmentInteractionListener) context;
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
