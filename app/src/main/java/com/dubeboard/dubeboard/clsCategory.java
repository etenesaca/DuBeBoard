package com.dubeboard.dubeboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import com.dubeboard.dubeboard.ManageDB.*;

public class clsCategory {
    protected Context Context;
    protected int _id;
    protected String _name;
    protected byte[] _image = null;

    public clsCategory() { }

    public clsCategory(Context Context, int Category_ID) {
        clsCategory res_category = new clsCategory(Context).getById(Category_ID);
        this._id = res_category.get_id();
        this._name = res_category.get_name();
        this._image = res_category.get_image();
    }

    public clsCategory(Context Context) { this.Context = Context; }

    public clsCategory(int _id, String _name, byte[] _image) {
        this._id = _id;
        this._name = _name;
        this._image = _image;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public byte[] get_image() {
        return _image;
    }

    public void set_image(byte[] _image) {
        this._image = _image;
    }


    public void AddRecord(clsCategory NewRecord) {
        SQLiteDatabase db = new ManageDB(Context).getWritableDatabase();
        // Armar el Insert
        ContentValues values = new ContentValues();
        values.put(ColumnsCategory.CATEGORY_NAME, NewRecord.get_name()); // Nombre
        values.put(ColumnsCategory.CATEGORY_IMAGE, NewRecord.get_image()); // Imagen

        // Insertar registro
        db.insert(ManageDB.TABLE_CATEGORIES, null, values);
        db.close();
    }

    // Obtener una Categoria
    public clsCategory getById(int Category_ID) {
        clsCategory result = null;
        List<clsCategory> Categories = getRecords(Category_ID);
        if (Categories.size() > 1)
            result = Categories.get(0);
        return result;
    }

    // Obtener todas las categorias
    public List<clsCategory> getAll() {
        return getRecords(null, null);
    }

    public List<clsCategory> getRecords(int Category_ID) {
        return getRecords(ColumnsCategory.CATEGORY_ID, Category_ID);
    }

    public List<clsCategory> getRecords(String Field, Object value) {
        List<clsCategory> RecordList = new ArrayList<clsCategory>();
        SQLiteDatabase db = new ManageDB(Context).getWritableDatabase();
        // Select All Query
        String selectQuery = "SELECT "
                + ColumnsCategory.CATEGORY_ID + ","
                + ColumnsCategory.CATEGORY_NAME + ","
                + ColumnsCategory.CATEGORY_IMAGE
                + " FROM " + ManageDB.TABLE_CATEGORIES;

        if (Field != null && value != null){
            value = "'" + value + "'";
            selectQuery = selectQuery + " WHERE " + Field + " = " + value;
        }

        selectQuery = selectQuery +  " ORDER BY " + ColumnsImage.IMAGE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                clsCategory Record = new clsCategory(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getBlob(2)
                );
                // Add Record
                RecordList.add(Record);
            } while (cursor.moveToNext());
        }
        db.close();
        return RecordList;
    }

    // Update
    public int Update(clsCategory NewValues) {
        SQLiteDatabase db = new ManageDB(Context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ColumnsCategory.CATEGORY_NAME, NewValues.get_name());
        values.put(ColumnsCategory.CATEGORY_IMAGE, NewValues.get_image());
        return db.update(ManageDB.TABLE_CATEGORIES, values, ColumnsCategory.CATEGORY_ID + " = " + NewValues.get_id(), null);
    }

    // Delete
    public void Delete(int record_id) {
        SQLiteDatabase db = new ManageDB(Context).getWritableDatabase();
        db.delete(ManageDB.TABLE_CATEGORIES, ColumnsCategory.CATEGORY_ID + " = " + record_id, null);
        db.close();
    }

    // Count Records
    public int CountRecords() {
        SQLiteDatabase db = new ManageDB(Context).getWritableDatabase();

        String countQuery = "SELECT " + ColumnsCategory.CATEGORY_ID + " FROM " + ManageDB.TABLE_CATEGORIES;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
}