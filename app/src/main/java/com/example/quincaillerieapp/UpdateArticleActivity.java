package com.example.quincaillerieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        deleteArticle = findViewById(R.id.deleteArticle);

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
                intent.putExtra("nom_mag", nomMagToUpdate);
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
                intent.putExtra("nom_mag", nomMagToUpdate);
                startActivity(intent);

            }
        });
        deleteArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    // méthode pour supprimer un article
    public void confirmDialog() {
        String nomArticleToUpdate_ = getIntent().getStringExtra("nomArticle");
        String nomMagToUpdate = getIntent().getStringExtra("nom_mag");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Suppression de " + nomArticleToUpdate_ + " ?");
        builder.setMessage("Êtes-vous sûr de vouloir supprimer " + nomArticleToUpdate_ + " ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String idArticleToUpdate_ = getIntent().getStringExtra("idArticle");
                boolean isDeleted = sqlite.deleteArticle(idArticleToUpdate_);
                if (isDeleted) {
                    Toast.makeText(UpdateArticleActivity.this, "Article supprimé avec succès", Toast.LENGTH_SHORT).show();

                    // Indiquez que la suppression a été effectuée avec succès
                    Intent intent = new Intent(UpdateArticleActivity.this, ArticleActivity.class);
                    intent.putExtra("nomMagasin_", nomMagToUpdate);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UpdateArticleActivity.this, "Échec de la suppression de l'article", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Ne rien faire si l'utilisateur clique sur "Non"
            }
        });
        builder.create().show();
    }


}