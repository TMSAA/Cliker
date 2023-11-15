package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView contador;
    ImageView imagen;

    double num = 0;
    double suma = 100;
    double valorDelContador;
    double costeRestaM = 100;
    double costeAuto =250;
    double millones;
    double miles;
    double billones;

    private Handler autoIncrementHandler;
    private int autoIncrementValuePerSecond;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contador = findViewById(R.id.textocontador);
        imagen = findViewById(R.id.imageView);
        autoIncrementHandler = new Handler(Looper.getMainLooper());
        valorDelContador = getIntent().getDoubleExtra("contador", 0.0);
        suma = getIntent().getDoubleExtra("sumarvuelta", suma);
        costeRestaM = getIntent().getDoubleExtra("CosteResta", costeRestaM);
        costeAuto = getIntent().getDoubleExtra("CosteAuto", costeAuto);
        autoIncrementValuePerSecond = getIntent().getIntExtra("Automatico", 0);
        num = valorDelContador;
        num();
        automatico();
    }



    public void sumar(View v){
        num = num + suma;
        ScaleAnimation fade_in = new ScaleAnimation(0.7f, 1.2f, 0.7f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(100);
        imagen.startAnimation(fade_in);
        num();
    }


    public void atras(View v){
        new Intent(this, Pantallainicio.class);
        finish();
    }

   public void mejoras(View v){
        Intent i = new Intent(MainActivity.this, Mejoras.class);
        i.putExtra("Num", num);
        i.putExtra("Suma", suma);
        i.putExtra("CosteResta", costeRestaM);
        i.putExtra("auto", autoIncrementValuePerSecond);
        i.putExtra("CosteAutoV", costeAuto);
       startActivity(i);
       finish();
   }

    public void automatico(){
        Runnable autoIncrementRunnable = new Runnable() {
            @Override
            public void run() {
                num += autoIncrementValuePerSecond;
                num();

                Intent updateIntent = new Intent("ACTUALIZAR_CONTADOR");
                updateIntent.putExtra("contador", num);
                sendBroadcast(updateIntent);

                autoIncrementHandler.postDelayed(this, 1000);
            }
        };
        autoIncrementHandler.postDelayed(autoIncrementRunnable, 1000);

    }

    public void num(){
        if(num>1000000000){
            billones = num/1000000000;
            String texto = String.format(Locale.getDefault(),"%.2f Billones", billones);
            contador.setText(texto);
        }else if (num >= 1000000){
            millones = num/1000000;
            String texto = String.format(Locale.getDefault(), "%.2f Millones", millones);
            contador.setText(texto);
        }else if (num>= 1000) {
            miles = num /1000;
            String texto = String.format(Locale.getDefault(), "%.2f Mil", miles);
            contador.setText(texto);
        }     else
              contador.setText(String.valueOf(num));
    }



}