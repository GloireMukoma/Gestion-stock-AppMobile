package com.example.quincaillerieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {
    // Durée d'affichage de l'écran de chargement en millisecondes
    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_creen);

        // Crée un gestionnaire pour retarder le lancement de l'activité principale
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Lance l'activité principale
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);

                // Ferme l'activité d'écran de chargement
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}