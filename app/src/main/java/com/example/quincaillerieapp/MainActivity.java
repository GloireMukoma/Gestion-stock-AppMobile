package com.example.quincaillerieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quincaillerieapp.DatabaseAcces.MyDatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MyDatabaseHelper mySQLite;

    ArrayList<String> listMagasinItem;
    ArrayAdapter adapter;
    ListView listViewMagasin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ar = getSupportActionBar();
        if (ar != null) {
            ar.setTitle("Liste Magasins");
        }

        mySQLite = new MyDatabaseHelper(this);
        listMagasinItem = new ArrayList<>();

        listViewMagasin = findViewById(R.id.magasinListView);

        // Appel de la méthode onCreate pour créer la base de données
        mySQLite.getWritableDatabase();
        viewDataMagasin();

        //gestion de la barre de navigation


        listViewMagasin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nomMag = listViewMagasin.getItemAtPosition(i).toString();
                Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
                intent.putExtra("nomMagasin_", nomMag);
                startActivity(intent);
            }
        });

    }
    private void viewDataMagasin(){
        Cursor cursor = mySQLite.viewMagasin();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Pas de donnée!", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                listMagasinItem.add(cursor.getString(1));
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMagasinItem);
            listViewMagasin.setAdapter(adapter);
        }
    }
    // Fonction pour le menu d'en haut
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search_menu_addNewMag) {
            // Passer à une nouvelle activité avec la variable nomMagasin
            //redirection vers l'activité de creation d'un nouveau magasin
            Intent intent = new Intent(this, AddNewMagasin.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.search_main_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}