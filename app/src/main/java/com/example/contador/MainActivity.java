package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
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
    String nombreUsuario;

    private Handler autoIncrementHandler;
    private int autoIncrementValuePerSecond;
    RecyclerView recyclerView;
    JugadorAdapter jugadorAdapter;
    List<Jugador> listaJugadores;




    @SuppressLint({"MissingInflatedId", "Range"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contador = findViewById(R.id.textocontador);
        imagen = findViewById(R.id.imageView);
        Base_Datos dbHelper = new Base_Datos(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        nombreUsuario = obtenerNombreUsuario();
        Cursor cursor = db.rawQuery("SELECT score FROM MiTabla WHERE usuario = ?", new String[]{nombreUsuario});
        if (cursor.moveToFirst()) {
            num = cursor.getDouble(cursor.getColumnIndex("score"));
        }
        cursor.close();
        db.close();
        autoIncrementHandler = new Handler(Looper.getMainLooper());
        recyclerView = findViewById(R.id.recyclerView);
        listaJugadores = obtenerJugadoresDesdeBD();
        jugadorAdapter = new JugadorAdapter(listaJugadores);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(jugadorAdapter);
        valorDelContador = getIntent().getDoubleExtra("contador", num);
        suma = getIntent().getDoubleExtra("sumarvuelta", suma);
        costeRestaM = getIntent().getDoubleExtra("CosteResta", costeRestaM);
        costeAuto = getIntent().getDoubleExtra("CosteAuto", costeAuto);
        autoIncrementValuePerSecond = getIntent().getIntExtra("Automatico", 0);
        num = valorDelContador;
        num();
        automatico();
    }

    private String obtenerNombreUsuario() {
        SharedPreferences prefs = getSharedPreferences("NOMBRE_PREFS", MODE_PRIVATE);
        return prefs.getString("nombreUsuario", ""); // "nombreUsuario" es la clave utilizada al guardar el nombre de usuario
    }



    public void sumar(View v){
        num = num + suma;
        ScaleAnimation fade_in = new ScaleAnimation(0.7f, 1.2f, 0.7f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(100);
        imagen.startAnimation(fade_in);
        num();
    }


    public void atras(View v){
        guardarPuntajeEnBaseDeDatos(num);
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

    private void guardarPuntajeEnBaseDeDatos(double puntaje) {
        // Código para actualizar el puntaje en la base de datos
        Base_Datos dbHelper = new Base_Datos(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("score", puntaje); // "score" es el nombre de la columna donde se guarda el puntaje

        // Suponiendo que "nombreUsuario" es el nombre del usuario actual
        db.update("MiTabla", values, "usuario=?", new String[]{nombreUsuario}); // Actualiza el puntaje para el usuario específico
        db.close();
    }

    private List<Jugador> obtenerJugadoresDesdeBD() {
        List<Jugador> jugadores = new ArrayList<>();

        Base_Datos dbHelper = new Base_Datos(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT usuario, score FROM MiTabla", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("usuario"));
                @SuppressLint("Range") int puntaje = cursor.getInt(cursor.getColumnIndex("score"));
                jugadores.add(new Jugador(nombre, puntaje));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return jugadores;
    }


}