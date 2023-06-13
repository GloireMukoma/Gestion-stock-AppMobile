package com.example.quincaillerieapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quincaillerieapp.DatabaseAcces.MyDatabaseHelper;
import com.example.quincaillerieapp.beans.Article;

import java.util.ArrayList;

public class UpdateArticleActivity extends AppCompatActivity {

    MyDatabaseHelper sqlite;
    TextView nomArticleModif;
    TextView qteStockArticleMof;
    Button saveModif;
    Button deleteArticle;
    ArrayList<Article> arrayListArticle;
    private static final int REQUEST_UPDATE_ARTICLE = 1;

    Button entrerStockBtn, sortieStockBtn;

    String nomArt;
    int qteStk;
    String _nomMag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_article);
        sqlite = new MyDatabaseHelper(this);
        arrayListArticle = new ArrayList<>();

        nomArticleModif = findViewById(R.id.productNameModify);
        qteStockArticleMof = findViewById(R.id.productQteModify);

        entrerStockBtn = findViewById(R.id.entrerStockBtn);
        sortieStockBtn = findViewById(R.id.sortieStockBtn);

        // recupere les attributs de l'item qui est cliqué
        String idArticleToUpdate_ = getIntent().getStringExtra("idArticle");
        String nomArticleToUpdate_ = getIntent().getStringExtra("nomArticle");
        String qteStockArticleToUpdate_ = getIntent().getStringExtra("quantiteStock");
        String nomMagToUpdate = getIntent().getStringExtra("nom_mag");

        nomArticleModif.setText(nomArticleToUpdate_);
        qteStockArticleMof.setText(qteStockArticleToUpdate_);

        ActionBar ar = getSupportActionBar();
        if (ar != null) {
            ar.setTitle("Fiche du produit");
        }
        // quand on clique sur le boutton de pour faire une entrée en stock
        entrerStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EntrerStockActivity.class);
                intent.putExtra("id", idArticleToUpdate_);
                intent.putExtra("nomArticleToUpdate", nomArticleToUpdate_);
                intent.putExtra("qteStockArticleToUpdate", qteStockArticleToUpdate_);
                startActivity(intent);

            }
        });
        // quand on clique sur le boutton de sortie stock, redirige vers une activité pour faire un sortie
        sortieStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SortieStockActivity.class);
                intent.putExtra("id", idArticleToUpdate_);
                intent.putExtra("nomArticleToUpdate", nomArticleToUpdate_);
                intent.putExtra("qteStockArticleToUpdate", qteStockArticleToUpdate_);
                startActivity(intent);

            }
        });


    }

}