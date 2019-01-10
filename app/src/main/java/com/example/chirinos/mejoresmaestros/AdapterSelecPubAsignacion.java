package com.example.chirinos.mejoresmaestros;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterSelecPubAsignacion extends RecyclerView.Adapter<AdapterSelecPubAsignacion.ViewHolderSeleccionar> implements View.OnClickListener {

    ArrayList<ConstructorPublicadores> listSeleccionarPub;
    Context ctx;
    private View.OnClickListener listenerSeleccionar;

    public AdapterSelecPubAsignacion(ArrayList<ConstructorPublicadores> listSeleccionarPub, Context ctx) {
        this.listSeleccionarPub = listSeleccionarPub;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public AdapterSelecPubAsignacion.ViewHolderSeleccionar onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.publicadores_seleccionar, null, false);
        view.setOnClickListener(this);
        return new ViewHolderSeleccionar(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSelecPubAsignacion.ViewHolderSeleccionar viewHolderSeleccionar, int i) {

        viewHolderSeleccionar.nombrePub.setText(listSeleccionarPub.get(i).getNombrePublicador());
        viewHolderSeleccionar.apellidoPub.setText(listSeleccionarPub.get(i).getApellidoPublicador());
        viewHolderSeleccionar.ultFecha.setText(listSeleccionarPub.get(i).getUltAsignacion());

    }

    @Override
    public int getItemCount() {
        return listSeleccionarPub.size();
    }

    public void setOnClickListener (View.OnClickListener listener) {
        this.listenerSeleccionar = listener;
    }

    @Override
    public void onClick(View view) {
        if (listenerSeleccionar != null) {
            listenerSeleccionar.onClick(view);
        }
    }

    public class ViewHolderSeleccionar extends RecyclerView.ViewHolder {

        TextView nombrePub, apellidoPub, ultFecha;

        public ViewHolderSeleccionar(@NonNull View itemView) {
            super(itemView);

            nombrePub = (TextView) itemView.findViewById(R.id.nombrePub);
            apellidoPub = (TextView) itemView.findViewById(R.id.apellidoPub);
            ultFecha = (TextView) itemView.findViewById(R.id.ultFecha);
        }
    }

    public void updateListSelec (ArrayList<ConstructorPublicadores> newList){

        listSeleccionarPub = new ArrayList<>();
        listSeleccionarPub.addAll(newList);
        notifyDataSetChanged();
    }
}
