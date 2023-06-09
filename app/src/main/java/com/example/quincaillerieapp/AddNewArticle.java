package com.example.quincaillerieapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quincaillerieapp.DatabaseAcces.MyDatabaseHelper;

public class AddNewArticle extends AppCompatActivity {
    MyDatabaseHelper mySqlite;
    TextView nomArticleInput;
    TextView qteStockArticleInput;
    Button saveNewArticleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_article);
        mySqlite = new MyDatabaseHelper(this);
        nomArticleInput = findViewById(R.id.articleNameInput);
        qteStockArticleInput = findViewById(R.id.qteArticleInput);
        saveNewArticleBtn = findViewById(R.id.saveArticleBtn);
        String mag = getIntent().getStringExtra("nomMagasin_");

        ActionBar ar = getSupportActionBar();
        if (ar != null) {
            ar.setTitle("Nouveau produit");
        }

        saveNewArticleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String article = nomArticleInput.getText().toString().trim();
                String qteStockString = qteStockArticleInput.getText().toString().trim();

                if (!qteStockString.matches("\\d+")) {
                    Toast.makeText(AddNewArticle.this, "La quantité stock doit être un nombre entier!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int qteStock = Integer.parseInt(qteStockString);

                if (mySqlite.addNewArticle(mag, article, qteStock)) {
                    Toast.makeText(AddNewArticle.this, "Enregistrement réussi", Toast.LENGTH_SHORT).show();
                    nomArticleInput.setText("");
                    qteStockArticleInput.setText("");

                } else {
                    Toast.makeText(AddNewArticle.this, "Une erreur s'est produite. Vérifiez si la quantité stock ne contient pas des caractères!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}