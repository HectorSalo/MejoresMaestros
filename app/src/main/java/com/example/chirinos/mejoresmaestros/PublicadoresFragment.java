package com.example.chirinos.mejoresmaestros;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PublicadoresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PublicadoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublicadoresFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner ordenarPublicadores;

    private OnFragmentInteractionListener mListener;

    private FloatingActionButton fabPublicadores;
    private View vista;
    private EditText textBuscar;
    private RecyclerView recyclerPublicadores;
    private ArrayList<ConstructorPublicadores> listPublicadores, listNombres, listApellidos, listFecha;
    private AdapterPublicadores adapterPublicadores;
    private AdminSQLiteOpenHelper conect;
    private String seleccionSpinner;
    private ProgressDialog progress;

    public PublicadoresFragment() {
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
    public static PublicadoresFragment newInstance(String param1, String param2) {
        PublicadoresFragment fragment = new PublicadoresFragment();
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
        vista = inflater.inflate(R.layout.fragment_publicadores, container, false);

        conect = new AdminSQLiteOpenHelper(getContext(), "VMC", null, AdminSQLiteOpenHelper.VERSION);

        textBuscar = (EditText) vista.findViewById(R.id.editTextBuscar);
        textBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listafiltrada (s.toString());
            }
        });

        GridLayoutManager gM = new GridLayoutManager(getContext(),3);
        recyclerPublicadores = (RecyclerView) vista.findViewById(R.id.recyclerPublicadores);
        recyclerPublicadores.setLayoutManager(gM);
        recyclerPublicadores.setHasFixedSize(true);
        listPublicadores = new ArrayList<>();
        listNombres = new ArrayList<>();
        listApellidos = new ArrayList<>();
        listFecha = new ArrayList<>();

        ordenarPublicadores = (Spinner) vista.findViewById(R.id.spinnerOrdenarPublicadores);
        String [] spOrdenar = {"Ordenar", "A-Z (Nombres)", "A-Z (Apellidos)", "Última Fecha"};
        ArrayAdapter<String> adapterOrdenar = new ArrayAdapter<String>(getContext(), R.layout.spinner_personalizado, spOrdenar);
        ordenarPublicadores.setAdapter(adapterOrdenar);
        ordenarPublicadores.setOnItemSelectedListener(this);

        fabPublicadores = (FloatingActionButton) vista.findViewById(R.id.fabPublicadores);
        fabPublicadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(getContext(), AddPublicador.class);
               startActivity(intent);

            }
        });


        progress = new ProgressDialog(getContext());
        progress.setMessage("Cargando...");
        progress.show();
        adapterPublicadores = new AdapterPublicadores(listPublicadores, getContext());
        recyclerPublicadores.setAdapter(adapterPublicadores);
        llenarlistPub ();


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), gM.getOrientation());
        recyclerPublicadores.addItemDecoration(dividerItemDecoration);


        return vista;
    }

    public void llenarlistPub() {

        listPublicadores = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("publicadores");

        Query query = reference.orderBy("apellido", Query.Direction.ASCENDING);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        ConstructorPublicadores publi = new ConstructorPublicadores();
                        publi.setIdPublicador(doc.getId());
                        publi.setNombrePublicador(doc.getString("nombre"));
                        publi.setApellidoPublicador(doc.getString("apellido"));
                        publi.setCorreo(doc.getString("correo"));
                        publi.setTelefono(doc.getString("telefono"));
                        publi.setGenero(doc.getString("genero"));

                        listPublicadores.add(publi);

                    }
                    adapterPublicadores.updateList(listPublicadores);
                    progress.dismiss();
                } else {
                    progress.dismiss();
                    Toast.makeText(getContext(), "Error al cargar lista. Intente nuevamente", Toast.LENGTH_SHORT).show();
                }
            }
        });



        adapterPublicadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VerActivity.class);
                Bundle miBundle = new Bundle();
                miBundle.putString("id", listPublicadores.get(recyclerPublicadores.getChildAdapterPosition(v)).getIdPublicador());
                intent.putExtras(miBundle);
                startActivity(intent);
            }
        });


    }

    private void llenarListNombres() {
        listPublicadores = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("publicadores");

        Query query = reference.orderBy("nombre", Query.Direction.ASCENDING);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        ConstructorPublicadores publi = new ConstructorPublicadores();
                        publi.setIdPublicador(doc.getId());
                        publi.setNombrePublicador(doc.getString("nombre"));
                        publi.setApellidoPublicador(doc.getString("apellido"));
                        publi.setCorreo(doc.getString("correo"));
                        publi.setTelefono(doc.getString("telefono"));
                        publi.setGenero(doc.getString("genero"));

                        listPublicadores.add(publi);

                    }
                    adapterPublicadores.updateList(listPublicadores);
                    progress.dismiss();
                } else {
                    progress.dismiss();
                    Toast.makeText(getContext(), "Error al cargar lista. Intente nuevamente", Toast.LENGTH_SHORT).show();
                }
            }
        });



        adapterPublicadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VerActivity.class);
                Bundle miBundle = new Bundle();
                miBundle.putString("id", listPublicadores.get(recyclerPublicadores.getChildAdapterPosition(v)).getIdPublicador());
                intent.putExtras(miBundle);
                startActivity(intent);
            }
        });
    }

    private void llenarListApellidos () {
        listPublicadores = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("publicadores");

        Query query = reference.orderBy("apellido", Query.Direction.ASCENDING);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        ConstructorPublicadores publi = new ConstructorPublicadores();
                        publi.setIdPublicador(doc.getId());
                        publi.setNombrePublicador(doc.getString("nombre"));
                        publi.setApellidoPublicador(doc.getString("apellido"));
                        publi.setCorreo(doc.getString("correo"));
                        publi.setTelefono(doc.getString("telefono"));
                        publi.setGenero(doc.getString("genero"));

                        listPublicadores.add(publi);

                    }
                    adapterPublicadores.updateList(listPublicadores);
                    progress.dismiss();
                } else {
                    progress.dismiss();
                    Toast.makeText(getContext(), "Error al cargar lista. Intente nuevamente", Toast.LENGTH_SHORT).show();
                }
            }
        });



        adapterPublicadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VerActivity.class);
                Bundle miBundle = new Bundle();
                miBundle.putString("id", listPublicadores.get(recyclerPublicadores.getChildAdapterPosition(v)).getIdPublicador());
                intent.putExtras(miBundle);
                startActivity(intent);
            }
        });
    }

    private void llenarListFecha () {
        SQLiteDatabase db = conect.getReadableDatabase();

        Cursor cursor =db.rawQuery("SELECT * FROM publicadores ORDER BY anualasignacion ASC, mesasignacion ASC, diaasignacion ASC", null);

        while (cursor.moveToNext()) {
            ConstructorPublicadores publi = new ConstructorPublicadores();
            publi.setIdPublicador(cursor.getString(0));
            publi.setNombrePublicador(cursor.getString(1));
            publi.setApellidoPublicador(cursor.getString(2));
            publi.setGenero(cursor.getString(5));
            publi.setUltAsignacion(String.valueOf(cursor.getInt(7)) + "/" + String.valueOf(cursor.getInt(8)) + "/" + String.valueOf(cursor.getInt(9)));

            listFecha.add(publi);
        }
        adapterPublicadores.updateList(listFecha);


        adapterPublicadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VerActivity.class);
                Bundle miBundle = new Bundle();
                miBundle.putString("id", listFecha.get(recyclerPublicadores.getChildAdapterPosition(v)).getIdPublicador());
                intent.putExtras(miBundle);
                startActivity(intent);
            }
        });
        db.close();
    }



    private void listafiltrada (String text) {

        if (listPublicadores.isEmpty()) {
            Toast.makeText(getContext(), "No hay lista cargada", Toast.LENGTH_SHORT).show();
        } else {
            String userInput = text.toLowerCase();
            ArrayList<ConstructorPublicadores> newList = new ArrayList<>();

            for (ConstructorPublicadores name : listPublicadores) {

                if (name.getNombrePublicador().toLowerCase().contains(userInput) || name.getApellidoPublicador().toLowerCase().contains(userInput)) {

                    newList.add(name);
                }
            }

            adapterPublicadores.updateList(newList);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        seleccionSpinner = ordenarPublicadores.getSelectedItem().toString();

        if (seleccionSpinner.equals("A-Z (Nombres)")) {
            llenarListNombres();
        } else if (seleccionSpinner.equals("A-Z (Apellidos)")) {
            llenarListApellidos();
        } else if (seleccionSpinner.equals("Última Fecha")) {
            //llenarListFecha();
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
