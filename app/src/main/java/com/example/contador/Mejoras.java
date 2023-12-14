package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class Mejoras extends AppCompatActivity {

    private TextView contador;
    private Button boton;
    private Button boton2;
    private Handler autoIncrementHandler;
    private int autoIncrementValuePerSecond = 0;


    double numRecibido;
    double sumaRecibido = 1;
    double costeResta;
    double costeAutomatico = 250;
    int mejorasCompradas = 0;
    double millones;
    double miles;
    double billones;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mejoras);
        contador = findViewById(R.id.textView2);
        boton = findViewById(R.id.button7);
        boton2 = findViewById(R.id.button2);
        autoIncrementHandler = new Handler(Looper.getMainLooper());
        numRecibido = getIntent().getDoubleExtra("Num", 0.0);
        sumaRecibido = getIntent().getDoubleExtra("Suma", 0.0);
        costeResta = getIntent().getDoubleExtra("CosteResta", 0.0);
        costeAutomatico = getIntent().getDoubleExtra("CosteAutoV", costeAutomatico);
        autoIncrementValuePerSecond = getIntent().getIntExtra("auto",0);
        boton.setText(costeResta + " clicks");
        boton2.setText(costeAutomatico + " cliks");
        num();
        automatico();
    }

    public void juego(View v){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("contador", numRecibido);
        i.putExtra("sumarvuelta", sumaRecibido);
        i.putExtra("CosteResta", costeResta);
        i.putExtra("CosteAuto", costeAutomatico);
        i.putExtra("MejorasCompradas", mejorasCompradas);
        i.putExtra("Automatico", autoIncrementValuePerSecond);
        startActivity(i);
        finish();
    }

    @SuppressLint("SetTextI18n")
    public void restar(View v){
        if (numRecibido>=costeResta) {
            numRecibido -= costeResta;
            sumaRecibido++;
            costeResta = costeResta +50;
            boton.setText(costeResta + " clicks");
            num();
        }
    }

    @SuppressLint("SetTextI18n")
    public void comprarAutomatico(View v) {
        if(numRecibido >=  costeAutomatico) {
            numRecibido -= costeAutomatico;
            costeAutomatico += 250;
            boton2.setText(costeAutomatico + " cliks");
            autoIncrementValuePerSecond++;
            }
    }

    public void automatico(){
        Runnable autoIncrementRunnable = new Runnable() {
            @Override
            public void run() {
                numRecibido += autoIncrementValuePerSecond;
                num();

                Intent updateIntent = new Intent("ACTUALIZAR_CONTADOR");
                updateIntent.putExtra("contador", numRecibido);
                sendBroadcast(updateIntent);

                autoIncrementHandler.postDelayed(this, 1000);
            }
        };
        autoIncrementHandler.postDelayed(autoIncrementRunnable, 1000);

    }

    public void num(){
        if(numRecibido>1000000000){
            billones = numRecibido/1000000000;
            String texto = String.format(Locale.getDefault(),"%.2f Billones", billones);
            contador.setText(texto);
        }else if (numRecibido >= 1000000){
            millones = numRecibido/1000000;
            String texto = String.format(Locale.getDefault(), "%.2f Millones", millones);
            contador.setText(texto);
        }else if (numRecibido>= 1000) {
            miles = numRecibido /1000;
            String texto = String.format(Locale.getDefault(), "%.2f Mil", miles);
            contador.setText(texto);
        }     else
            contador.setText(String.valueOf(numRecibido));
    }


}