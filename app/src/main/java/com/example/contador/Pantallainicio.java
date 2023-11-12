package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Pantallainicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallainicio);
    }

    public void IrAMainActivity(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void IrAInfo(View v){
        Intent i = new Intent(this, PantallaInfo.class);
        startActivity(i);
        finish();
        }

     public void IrAAjustes(View v){
        Intent i = new Intent(this, PantallaAjustes.class);
        startActivity(i);
         finish();
     }

    public void Salir(View v){
        finish();
    }

    }
