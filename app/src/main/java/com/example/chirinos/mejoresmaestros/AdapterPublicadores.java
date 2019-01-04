package com.example.chirinos.mejoresmaestros;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterPublicadores extends RecyclerView.Adapter<AdapterPublicadores.ViewHolderPublicadores> implements View.OnClickListener{

    ArrayList<ConstructorPublicadores> listPublicadores;
    private View.OnClickListener listener;
    Context mctx;

    public AdapterPublicadores (ArrayList<ConstructorPublicadores> listPublicadores, PublicadoresFragment mctx) {
        this.listPublicadores = listPublicadores;

    }
    @NonNull
    @Override
    public ViewHolderPublicadores onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.publicadores_grid, null, false);
        view.setOnClickListener(this);
        return new ViewHolderPublicadores(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPublicadores viewHolderPublicadores, int i) {

        viewHolderPublicadores.nombrePub.setText(listPublicadores.get(i).getNombrePublicador());
        viewHolderPublicadores.apellidoPub.setText(listPublicadores.get(i).getApellidoPublicador());
        viewHolderPublicadores.ultiDiscurso.setText((CharSequence) listPublicadores.get(i).getUltAsignacion());
        viewHolderPublicadores.imagenPublicador.setImageResource(listPublicadores.get(i).getImagen());

    }

    @Override
    public int getItemCount() {
        return listPublicadores.size();
    }

    public void setOnClickListener (View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }

    }

    public class ViewHolderPublicadores extends RecyclerView.ViewHolder {

        ImageView imagenPublicador;
        TextView nombrePub, apellidoPub, ultiDiscurso;

        public ViewHolderPublicadores(@NonNull View itemView) {
            super(itemView);

            imagenPublicador = (ImageView) itemView.findViewById(R.id.imagenPublicadores);
            nombrePub = (TextView) itemView.findViewById(R.id.textViewNombre);
            apellidoPub = (TextView) itemView.findViewById(R.id.textViewApellido);
            ultiDiscurso = (TextView) itemView.findViewById(R.id.textViewFecha);
        }
    }

    public void updateList (ArrayList<ConstructorPublicadores> newList){

        listPublicadores = new ArrayList<>();
        listPublicadores.addAll(newList);
        notifyDataSetChanged();
    }
}
