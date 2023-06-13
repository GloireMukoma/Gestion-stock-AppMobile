package com.example.quincaillerieapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quincaillerieapp.DatabaseAcces.MyDatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    MyDatabaseHelper SQLite;
    Button loginUserbton;
    TextView nomUser, passwordUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Demande à l'activité de ne pas afficher la ActionBar
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // Met en plein écran
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                //WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar ar = getSupportActionBar();
        if (ar != null) {
            ar.setTitle("Connexion");
        }

        setContentView(R.layout.activity_login);

        loginUserbton = findViewById(R.id.loginUserBtn);
        nomUser = findViewById(R.id.nomUserLogin);
        passwordUser = findViewById(R.id.passwordUserLogin);

        loginUserbton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Récupère les informations saisies
                String username = nomUser.getText().toString().trim();
                String password = passwordUser.getText().toString().trim();

                // Vérifie l'authentification
                if (isValidLogin(username, password)) {
                    // Authentification réussie, redirige vers l'activité principale
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Termine l'activité de connexion pour qu'elle ne soit pas accessible via le bouton "Retour"
                } else {
                    nomUser.setText("");
                    passwordUser.setText("");
                    // Authentification échouée, affiche un message d'erreur
                    Toast.makeText(LoginActivity.this, "Nom d'utilisateur ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private boolean isValidLogin(String username, String password) {
        if(username.equals("teclaire") && password.equals("0012")){
            return true;
        }else{
            return false;
        }
    }
}