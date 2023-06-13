package com.example.quincaillerieapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quincaillerieapp.DatabaseAcces.MyDatabaseHelper;

public class SortieStockActivity extends AppCompatActivity {

    MyDatabaseHelper sqlite;
    Button saveQteSortieStockInputBtn;
    TextView qteSortieStockInput;
    TextView productName, productQte;
    TextView errorMsgTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sortie_stock);
        sqlite = new MyDatabaseHelper(this);

        qteSortieStockInput = findViewById(R.id.qteSortieStockInput);
        saveQteSortieStockInputBtn = findViewById(R.id.saveQteSortieStockInputBtn);
        errorMsgTextView = findViewById(R.id.errorMsgTextView);

        productName = findViewById(R.id.productName);
        productQte = findViewById(R.id.productQte);

        // recupere les attributs de l'item qui est cliqué
        String idArticleToUpdate_ = getIntent().getStringExtra("id");
        String nomArticleToUpdate_ = getIntent().getStringExtra("nomArticleToUpdate");
        String qteStockArticleToUpdate_ = getIntent().getStringExtra("qteStockArticleToUpdate");
        String nom_mag = getIntent().getStringExtra("nom_mag");

        productName.setText(nomArticleToUpdate_);
        productQte.setText(qteStockArticleToUpdate_);

        ActionBar ar = getSupportActionBar();
        if (ar != null) {
            ar.setTitle("Sortie de stock");
        }

        saveQteSortieStockInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qteStkInput = qteSortieStockInput.getText().toString().trim();
                int qteStk = -1;
                int newStockQte = -1;

                try {
                    qteStk = Integer.parseInt(qteStkInput);
                    int stockActuel = Integer.parseInt(qteStockArticleToUpdate_);
                    if(qteStk > stockActuel){
                        errorMsgTextView.setText("La quantité stock de sortie est supérieure à la quantité actuelle.");
                        return;
                    }
                    // mise a jour de la quantié stock
                    newStockQte = stockActuel-qteStk;

                } catch (NumberFormatException e) {
                    Toast.makeText(SortieStockActivity.this, "La quantité stock doit être un nombre entier!", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isUpdated = sqlite.updateArticle(idArticleToUpdate_, newStockQte);
                if (isUpdated) {
                    Toast.makeText(SortieStockActivity.this, "Succès!", Toast.LENGTH_SHORT).show();

                    // Indiquez que la modification a été effectuée avec succès
                    Intent intent = new Intent(SortieStockActivity.this, ArticleActivity.class);
                    intent.putExtra("nomMagasin_", nom_mag);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(SortieStockActivity.this, "Une erreur s'est produite. Vérifiez si la quantité stock ne contient pas des caractères!", Toast.LENGTH_SHORT).show();
                }
                finish();

            }
        });
    }
}