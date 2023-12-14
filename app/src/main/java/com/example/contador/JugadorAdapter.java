package com.example.contador;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class JugadorAdapter extends RecyclerView.Adapter<JugadorVierHolder> {

    private List<Jugador> listaJugadores;

    public JugadorAdapter(List<Jugador> listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    @NonNull
    @Override
    public JugadorVierHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jugador_layout, parent, false);
        return new JugadorVierHolder(view);
    }

    public void onBindViewHolder(@NonNull JugadorVierHolder holder, int position) {
        Jugador jugador = listaJugadores.get(position);
        holder.bind(jugador);
    }

    @Override
    public int getItemCount() {
        return listaJugadores.size();
    }
}