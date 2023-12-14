package com.example.contador;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Entrada extends AppCompatActivity {
    EditText usernameEditText;
    EditText passwordEditText;
    EditText scoreEditText;
    Button saveButton;
    Button loginButton;
    Button registerButton; // Agrega un botón para registrar un nuevo usuario

    ScoreDatabaseManager dbManager;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);

        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        registerButton = findViewById(R.id.buttonRegister);

        dbManager = new ScoreDatabaseManager(this);


    }
    public void RegisterUser(View v) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        dbManager.open();
        boolean isUserExists = dbManager.checkIfUserExists(username);

        if (isUserExists) {
            // El usuario ya existe en la base de datos
            Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show();
        } else {
            // El usuario no existe, se procede a registrarlo
            long result = dbManager.insertScoreWithPassword(username, password, 0); // Puntuación inicial

            if (result != -1) {
                Toast.makeText(getApplicationContext(), "Usuario registrado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error al registrar usuario", Toast.LENGTH_SHORT).show();
            }
        }

        dbManager.close();
    }

    public void loginUser(View v){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        dbManager.open();
        boolean isAuthenticated = dbManager.authenticateUser(username, password);
        dbManager.close();

        if (isAuthenticated) {
            // El usuario ha iniciado sesión correctamente
            Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, Pantallainicio.class);
            startActivity(i);
        } else {
            // Las credenciales son incorrectas, muestra un mensaje de error
            Toast.makeText(getApplicationContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }
}

