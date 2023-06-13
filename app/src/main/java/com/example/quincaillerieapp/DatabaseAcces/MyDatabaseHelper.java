package com.example.quincaillerieapp.DatabaseAcces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GestionStock.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_COMPTE_NAME = "compte";
    private static final String COLUMN_USERNAME = "nomuser";
    private static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_MAGASIN_NAME = "magasin";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME_MAGASIN = "nommagasin";

    private static final String TABLE_ARTICLE_NAME = "article";
    private static final String COLUMN_NAME_ARTICLE = "nomarticle";
    private static final String COLUMN_QTE_STOCK_ARTICLE = "qtestockarticle";
    private static final String COLUMN_NOMMAGASIN_ARTICLE = "nommagasin";

    private static final String CREATE_COMPTE_TABLE = "CREATE TABLE " + TABLE_COMPTE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERNAME + " TEXT, " +
            COLUMN_PASSWORD + " TEXT)";


    private static final String CREATE_MAGASIN_TABLE = "CREATE TABLE " + TABLE_MAGASIN_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME_MAGASIN + " TEXT)";

    private static final String CREATE_ARTICLE_TABLE = "CREATE TABLE " + TABLE_ARTICLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME_ARTICLE + " TEXT, " +
            COLUMN_QTE_STOCK_ARTICLE + " INTEGER, " +
            COLUMN_NOMMAGASIN_ARTICLE + " TEXT)";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MAGASIN_TABLE);
        db.execSQL(CREATE_ARTICLE_TABLE);
        // Création de la table "compte"
        db.execSQL(CREATE_COMPTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAGASIN_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE_NAME);
        onCreate(db);
    }

    public boolean createNewMagasin(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_MAGASIN, name);
        long result = db.insert(TABLE_MAGASIN_NAME, null, cv);
        return result != -1;
    }

    public Cursor viewMagasin() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + TABLE_MAGASIN_NAME;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;

    }

    public Cursor selectArticlesByMagasin(String nomMagasin) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ARTICLE_NAME + " WHERE " + COLUMN_NAME_MAGASIN + " = ?";
        String[] selectionArgs = {nomMagasin};
        return db.rawQuery(query, selectionArgs);
    }

    public boolean addNewArticle(String nameMagasin, String nomArticle_, int qteStock) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME_ARTICLE, nomArticle_);
        cv.put(COLUMN_QTE_STOCK_ARTICLE, qteStock);
        cv.put(COLUMN_NOMMAGASIN_ARTICLE, nameMagasin);
        long result = db.insert(TABLE_ARTICLE_NAME, null, cv);
        return result != -1;

    }

    public boolean updateArticle(String row_id, int qte_stock){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QTE_STOCK_ARTICLE, qte_stock);
        long result = db.update(TABLE_ARTICLE_NAME, cv, "_id=?", new String[]{row_id});
        return result != -1;
    }

    public boolean deleteArticle(String articleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {articleId};
        int result = db.delete(TABLE_ARTICLE_NAME, whereClause, whereArgs);
        return result > 0;
    }

    public Cursor searchArticle(String searchTerm) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ARTICLE_NAME + " WHERE " + COLUMN_NAME_ARTICLE + " LIKE ?";
        String[] selectionArgs = {"%" + searchTerm + "%"};
        return db.rawQuery(query, selectionArgs);
    }

}
