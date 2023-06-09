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

    TextView entrerStock, sortieStock;

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

        saveModif = findViewById(R.id.saveArticleModifyBtn);
        deleteArticle = findViewById(R.id.deleteArticleBtn);

        entrerStock = findViewById(R.id.entrerStock);
        sortieStock = findViewById(R.id.sortieStock);

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

        saveModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nomArt = nomArticleModif.getText().toString().trim();
                String qteStkString = qteStockArticleMof.getText().toString().trim();
                qteStk = -1;

                try {
                    qteStk = Integer.parseInt(qteStkString);
                } catch (NumberFormatException e) {
                    Toast.makeText(UpdateArticleActivity.this, "La quantité stock doit être un nombre entier!", Toast.LENGTH_SHORT).show();
                    return;
                }
                _nomMag = nomMagToUpdate.trim();

                boolean isUpdated = sqlite.updateArticle(idArticleToUpdate_, nomArt, qteStk, _nomMag);
                if (isUpdated) {
                    Toast.makeText(UpdateArticleActivity.this, "Article modifié avec succès!", Toast.LENGTH_SHORT).show();

                    // Indiquez que la modification a été effectuée avec succès
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("isArticleModified", true);
                    setResult(RESULT_OK, resultIntent);
                } else {
                    Toast.makeText(UpdateArticleActivity.this, "Une erreur s'est produite. Vérifiez si la quantité stock ne contient pas des caractères!", Toast.LENGTH_SHORT).show();
                }
                finish();

            }
        });

        deleteArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();

            }
        });

    }
    public void confirmDialog() {
        String nomArticleToUpdate_ = getIntent().getStringExtra("nomArticle");
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
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("isArticleDeleted", true);
                    setResult(RESULT_OK, resultIntent);
                    // Appeler onActivityResult de l'activité appelante pour actualiser la liste
                    UpdateArticleActivity.this.onActivityResult(REQUEST_UPDATE_ARTICLE, RESULT_OK, resultIntent);
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