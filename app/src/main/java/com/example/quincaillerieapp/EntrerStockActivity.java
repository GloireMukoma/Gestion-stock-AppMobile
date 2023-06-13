package com.example.quincaillerieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quincaillerieapp.DatabaseAcces.MyDatabaseHelper;

public class EntrerStockActivity extends AppCompatActivity {
    MyDatabaseHelper sqlite;
    Button saveQteEntrerStockInputBtn;
    TextView qteEntrerStockInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrer_stock);
        sqlite = new MyDatabaseHelper(this);

        qteEntrerStockInput = findViewById(R.id.qteEntrerStockInput);
        saveQteEntrerStockInputBtn = findViewById(R.id.saveQteEntrerStockInputBtn);

        // recupere les attributs de l'item qui est cliqué
        String idArticleToUpdate_ = getIntent().getStringExtra("id");
        String nomArticleToUpdate_ = getIntent().getStringExtra("nomArticleToUpdate");
        String qteStockArticleToUpdate_ = getIntent().getStringExtra("qteStockArticleToUpdate");

        saveQteEntrerStockInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qteStkInput = qteEntrerStockInput.getText().toString().trim();
                int qteStk = -1;
                int newStockQte = -1;

                try {
                    qteStk = Integer.parseInt(qteStkInput);
                    int stockActuel = Integer.parseInt(qteStockArticleToUpdate_);
                    // mise a jour de la quantié stock
                    newStockQte = stockActuel+qteStk;

                } catch (NumberFormatException e) {
                    Toast.makeText(EntrerStockActivity.this, "La quantité stock doit être un nombre entier!", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isUpdated = sqlite.updateArticle(idArticleToUpdate_, newStockQte);
                if (isUpdated) {
                    Toast.makeText(EntrerStockActivity.this, "Article modifié avec succès!", Toast.LENGTH_SHORT).show();

                    // Indiquez que la modification a été effectuée avec succès
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("isArticleModified", true);
                    setResult(RESULT_OK, resultIntent);
                } else {
                    Toast.makeText(EntrerStockActivity.this, "Une erreur s'est produite. Vérifiez si la quantité stock ne contient pas des caractères!", Toast.LENGTH_SHORT).show();
                }
                finish();

            }
        });

    }
}