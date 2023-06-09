package com.example.quincaillerieapp.beans;

public class Article {
    String idArticle;
    String nomArticle;
    String qteStock;
    String nomMag;

    public Article(String idArticle, String nomArticle, String qteStock, String nomMag) {
        this.idArticle = idArticle;
        this.nomArticle = nomArticle;
        this.qteStock = qteStock;
        this.nomMag = nomMag;
    }

    public String getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(String idArticle) {
        this.idArticle = idArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getQteStock() {
        return qteStock;
    }

    public void setQteStock(String qteStock) {
        this.qteStock = qteStock;
    }

    public String getNomMag() {
        return nomMag;
    }

    public void setNomMag(String nomMag) {
        this.nomMag = nomMag;
    }
}
