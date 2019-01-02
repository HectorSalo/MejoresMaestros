package com.example.chirinos.mejoresmaestros;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
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

    Chronometer temporizador;
    EditText editTextCrono;
    FloatingActionButton fabStart, fabStop, fabPause;
    boolean isPaused;
    boolean isCenceled;
    long TiempoRestante;
    boolean isResume;

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

        temporizador = (Chronometer) vista.findViewById(R.id.Crono);
        editTextCrono = (EditText) vista.findViewById(R.id.editTextCrono);
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

                long tiempoBase = 15000;
                long intervaloDecrecer = 1000;

                new CountDownTimer(tiempoBase, intervaloDecrecer) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                             if (isPaused || isCenceled) {
                                 cancel();
                                 Toast.makeText(getContext(), "cancel()", Toast.LENGTH_SHORT).show();
                             }   else {
                                 fabStart.setEnabled(false);
                                 fabPause.setEnabled(true);
                                 fabStop.setEnabled(true);
                                 //temporizador.setText(""+millisUntilFinished);
                                 editTextCrono.setText("" + millisUntilFinished / 1000);
                                 TiempoRestante = millisUntilFinished;

                             }
                    }

                    @Override
                    public void onFinish() {
                        editTextCrono.setText("Finalizado");
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
            @Override
            public void onClick(View v) {
                isCenceled = true;
                boolean prueba = true;
                editTextCrono.setText("");
                fabStart.setEnabled(true);
                fabPause.setEnabled(false);
                fabStop.setEnabled(false);



            }
        });

        fabPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isResume) {
                    isPaused = true;
                    isResume = false;
                    fabStart.setEnabled(false);

                } else  {

                    isPaused = false;
                    long tiempoBase = TiempoRestante;
                    long intervaloDecrecer = 1000;

                    new CountDownTimer(tiempoBase, intervaloDecrecer) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            if (isPaused || isCenceled) {
                                cancel();
                            } else {
                                //temporizador.setText(""+millisUntilFinished);
                                editTextCrono.setText("" + millisUntilFinished / 1000);
                                TiempoRestante = millisUntilFinished;
                            }
                        }

                        @Override
                        public void onFinish() {
                            editTextCrono.setText("Finalizado");
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
