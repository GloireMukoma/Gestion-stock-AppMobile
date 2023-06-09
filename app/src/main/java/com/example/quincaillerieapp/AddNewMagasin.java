package com.example.quincaillerieapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quincaillerieapp.DatabaseAcces.MyDatabaseHelper;

public class AddNewMagasin extends AppCompatActivity {
    MyDatabaseHelper sqlite;
    EditText magasinNameInput;
    Button saveBtnMagasin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_magasin);
        sqlite = new MyDatabaseHelper(this);
        magasinNameInput = findViewById(R.id.magasinNameInput_);
        saveBtnMagasin = findViewById(R.id.saveMagasinBtn);

        ActionBar ar = getSupportActionBar();
        if (ar != null) {
            ar.setTitle("Nouveau Magasin");
        }

        saveBtnMagasin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getNomMag = magasinNameInput.getText().toString().trim();
                // Créer un nouveau magasin quand on clique sur le bouton
                if (!getNomMag.equals("") && sqlite.createNewMagasin(getNomMag)) {
                    Toast.makeText(AddNewMagasin.this, "Magasin créé avec succès!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddNewMagasin.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(AddNewMagasin.this, "Veuillez entrez le nom du magasin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}