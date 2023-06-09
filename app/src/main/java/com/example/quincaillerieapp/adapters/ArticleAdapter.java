package com.example.quincaillerieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quincaillerieapp.R;
import com.example.quincaillerieapp.beans.Article;

import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<Article> {
    private Context mContext;
    private int mResource;

    public ArticleAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Article> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(mResource, parent, false);
        }

        Article article = getItem(position);

        TextView id_article = convertView.findViewById(R.id.idArticleRow);
        TextView nom_article = convertView.findViewById(R.id.nomArticleRow);
        TextView qte_stock = convertView.findViewById(R.id.qteStockArticleRow);

        // Set les valeurs dans les TextViews
        //id_article.setText(getItem(position).getIdArticle());
        nom_article.setText(getItem(position).getNomArticle());
        qte_stock.setText(getItem(position).getQteStock() +" stocks");

        return convertView;
    }
}
