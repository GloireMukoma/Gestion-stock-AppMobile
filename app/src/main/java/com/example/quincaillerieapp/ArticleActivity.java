package com.example.quincaillerieapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quincaillerieapp.DatabaseAcces.MyDatabaseHelper;
import com.example.quincaillerieapp.adapters.ArticleAdapter;
import com.example.quincaillerieapp.beans.Article;

import java.util.ArrayList;

public class ArticleActivity extends AppCompatActivity {
    MyDatabaseHelper mySQLite;
    ListView articleListView;
    ArrayList<String> arraylistArticleItem;
    ArrayAdapter adapter;

    ArrayList<Article> arrayListArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        mySQLite = new MyDatabaseHelper(this);
        articleListView = findViewById(R.id.articleListView);
        arraylistArticleItem = new ArrayList<>();

        //ArrayList<Article> arrayListArticle = new ArrayList<>();
        arrayListArticle = new ArrayList<>();

        // recuperation des données qu'on nous envoie(nom du magasin)
        Intent intent = getIntent();
        String nomMagasin = intent.getStringExtra("nomMagasin_");

        // Methode pour afficher la listview
        viewDataArticle(nomMagasin);

        ActionBar ar = getSupportActionBar();
        if (ar != null) {
            ar.setTitle(nomMagasin);
        }

        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Article selectedArticle = (Article) articleListView.getItemAtPosition(i);
                // Récupérer les informations de l'article sélectionné
                String idArticle = selectedArticle.getIdArticle();
                String nomArticle = selectedArticle.getNomArticle();
                String quantiteStock = selectedArticle.getQteStock();
                String nomMags = selectedArticle.getNomMag();

                // Passer à une nouvelle activité avec les informations de l'article
                Intent intent = new Intent(ArticleActivity.this, UpdateArticleActivity.class);
                intent.putExtra("idArticle", idArticle);
                intent.putExtra("nomArticle", nomArticle);
                intent.putExtra("quantiteStock", quantiteStock);
                intent.putExtra("nom_mag", nomMags);
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Vérifier si l'article a été modifié
            boolean isArticleModified = data.getBooleanExtra("isArticleModified", false);
            if (isArticleModified) {
                // L'article a été modifié avec succès
                // Rafraîchir la liste des articles
                String nomMagasin = getIntent().getStringExtra("nomMagasin_");
                viewDataArticle(nomMagasin);
            }
        }
    }

    private void viewDataArticle(String nomM){
        // Effacer l'ArrayList avant de le mettre à jour
        arrayListArticle.clear();

        Cursor cursor = mySQLite.selectArticlesByMagasin(nomM);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Pas de donnée!", Toast.LENGTH_SHORT).show();
        }else{
            int columnIndexId = cursor.getColumnIndex("_id");
            int columnIndexNomArticle = cursor.getColumnIndex("nomarticle");
            int columnIndexQteStock = cursor.getColumnIndex("qtestockarticle");
            int columnIndexNomMag = cursor.getColumnIndex("nommagasin");

            while (cursor.moveToNext()) {
                String id = cursor.getString(columnIndexId);
                String nomArticle = cursor.getString(columnIndexNomArticle);
                String qteStock = cursor.getString(columnIndexQteStock);
                String nomMag_ = cursor.getString(columnIndexNomMag);

                Article article = new Article(id, nomArticle, qteStock, nomMag_);
                arrayListArticle.add(article);
            }

                // on parcour la ligne dans la table pour recuperer l'item conserné
                //arraylistArticleItem.add(cursor.getString(1)+ "\n"+ "->" + cursor.getString(2) +" stock");

            //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arraylistArticleItem);
            //articleListView.setAdapter(adapter);
            ArticleAdapter articleAdapter = new ArticleAdapter(this, R.layout.article_row, arrayListArticle);
            articleListView.setAdapter(articleAdapter);
        }
    }

    // Fonction pour le menu d'en haut
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.icon_addArticle_menu) {
            // Passer à une nouvelle activité avec la variable nomMagasin
            String nomMagasin = getIntent().getStringExtra("nomMagasin_");

            Intent intent = new Intent(this, AddNewArticle.class);
            intent.putExtra("nomMagasin_", nomMagasin);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.search_icon_btn) {
            // Afficher une boîte de dialogue de recherche
            showSearchDialog();
            return true;
        }else if(item.getItemId() == R.id.more_icon_about){
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rechercher un article");

        // Créer un champ d'entrée de texte dans la boîte de dialogue
        final EditText searchInput = new EditText(this);
        builder.setView(searchInput);

        builder.setPositiveButton("Rechercher", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Récupérer le terme de recherche saisi par l'utilisateur
                String searchTerm = searchInput.getText().toString().trim();
                if (!searchTerm.isEmpty()) {
                    // Effectuer la recherche d'article avec le terme de recherche
                    Cursor cursor = mySQLite.searchArticle(searchTerm);

                    if (cursor != null && cursor.getCount() > 0) {
                        // Effacer l'ArrayList avant de le mettre à jour
                        arrayListArticle.clear();

                        int columnIndexId = cursor.getColumnIndex("_id");
                        int columnIndexNomArticle = cursor.getColumnIndex("nomarticle");
                        int columnIndexQteStock = cursor.getColumnIndex("qtestockarticle");
                        int columnIndexNomMag = cursor.getColumnIndex("nommagasin");

                        while (cursor.moveToNext()) {
                            String id = cursor.getString(columnIndexId);
                            String nomArticle = cursor.getString(columnIndexNomArticle);
                            String qteStock = cursor.getString(columnIndexQteStock);
                            String nomMag_ = cursor.getString(columnIndexNomMag);

                            Article article = new Article(id, nomArticle, qteStock, nomMag_);
                            arrayListArticle.add(article);
                        }

                        ArticleAdapter articleAdapter = new ArticleAdapter(ArticleActivity.this, R.layout.article_row, arrayListArticle);
                        articleListView.setAdapter(articleAdapter);
                    } else {
                        Toast.makeText(ArticleActivity.this, "Aucun article trouvé.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ArticleActivity.this, "Veuillez entrer un terme de recherche.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Annuler la recherche
            }
        });

        builder.create().show();
    }

}