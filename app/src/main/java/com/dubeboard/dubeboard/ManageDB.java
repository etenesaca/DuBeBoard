package com.dubeboard.dubeboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ManageDB extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "dubeboard_db";

    // Tabla Categoria
    public static final String TABLE_CATEGORIES = "Category";
    // Columnas
    public static class ColumnsCategory{
        public static final String CATEGORY_ID = "id";
        public static final String CATEGORY_NAME = "name";
        public static final String CATEGORY_IMAGE = "image";
    }

    // Tablas de la base de datos
    public static final String TABLE_IMAGE = "Image";
    // Columnas
    public static class ColumnsImage{
        public static final String IMAGE_ID = "id";
        public static final String IMAGE_NAME = "name";
        public static final String IMAGE_CATEGORY_ID = "category_id";
        public static final String IMAGE_IMAGE = "image";
        public static final String IMAGE_SOUND = "sound";
    }

    public ManageDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla Categorias
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "("
                + ColumnsCategory.CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ColumnsCategory.CATEGORY_NAME + " VARCHAR(256) UNIQUE,"
                + ColumnsCategory.CATEGORY_IMAGE + " BLOB"
                + ")";
        db.execSQL(CREATE_CATEGORY_TABLE);

        // Crear la tabla Imagenes
        String CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_IMAGE + "("
                + ColumnsImage.IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ColumnsImage.IMAGE_NAME + " VARCHAR(256),"
                + ColumnsImage.IMAGE_CATEGORY_ID + " INTEGER,"
                + ColumnsImage.IMAGE_IMAGE + " BLOB,"
                + ColumnsImage.IMAGE_SOUND + " BLOB,"
                + "UNIQUE(" + ColumnsImage.IMAGE_NAME + ", " + ColumnsImage.IMAGE_CATEGORY_ID + " ) ON CONFLICT REPLACE, "
                + "FOREIGN KEY(" + ColumnsImage.IMAGE_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORIES + "(" + ColumnsCategory.CATEGORY_ID + ")"
                + ")";
        db.execSQL(CREATE_IMAGE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar las tablas Cuando se cambie la version de la base de datos
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);

        // Create tables again
        onCreate(db);
    }
}