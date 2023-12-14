package com.example.contador;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class JugadorVierHolder extends RecyclerView.ViewHolder {


    private TextView textNombre;
    private TextView textPuntaje;

    public JugadorVierHolder(@NonNull View itemView) {
        super(itemView);
        textNombre = itemView.findViewById(R.id.textNombre);
        textPuntaje = itemView.findViewById(R.id.textPuntaje);
    }

    public void bind(Jugador jugador) {
        textNombre.setText(jugador.getNombre());
        textPuntaje.setText(String.valueOf(jugador.getPuntaje()));
    }
}