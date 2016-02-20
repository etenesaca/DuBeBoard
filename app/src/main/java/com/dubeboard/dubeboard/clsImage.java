package com.dubeboard.dubeboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar on 12/02/16.
 */
public class clsImage {
    protected Context Context;
    protected int _id;
    protected String _name;
    protected clsCategory _category;
    protected byte[] _image;
    protected byte[] _sound;

    public clsImage() { }

    public clsImage(Context Context) { this.Context = Context; }

    public clsImage(int _id, String _name, clsCategory _category, byte[] _image, byte[] _sound) {
        this._id = _id;
        this._name = _name;
        this._category = _category;
        this._image = _image;
        this._sound = _sound;
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

    public clsCategory get_category() {
        return _category;
    }

    public void set_category(clsCategory _category) {
        this._category = _category;
    }

    public byte[] get_image() {
        return _image;
    }

    public void set_image(byte[] _image) {
        this._image = _image;
    }

    public byte[] get_sound() {
        return _sound;
    }

    public void set_sound(byte[] _sound) {
        this._sound = _sound;
    }

    public void AddRecord(clsImage NewRecord) {
        SQLiteDatabase db = new ManageDB(Context).getWritableDatabase();
        // Armar el Insert
        ContentValues values = new ContentValues();
        values.put(ManageDB.ColumnsImage.IMAGE_NAME, NewRecord.get_name()); // Nombre
        values.put(ManageDB.ColumnsImage.IMAGE_IMAGE, NewRecord.get_image()); // Imagen

        // Insertar registro
        db.insert(ManageDB.TABLE_IMAGE, null, values);
        db.close();
    }

    // Obtener una Imagen
    public clsImage getById(int Image_ID) {
        clsImage result = null;
        List<clsImage> Images = getRecords(Image_ID);
        if (Images.size() > 1)
            result = Images.get(0);
        return result;
    }

    // Obtener todas las categorias
    public List<clsImage> getAll() {
        return getRecords(null, null);
    }

    public List<clsImage> getRecords(int Image_ID) {
        return getRecords(ManageDB.ColumnsImage.IMAGE_ID, Image_ID);
    }

    public List<clsImage> getRecords(String Field, Object value) {
        List<clsImage> RecordList = new ArrayList<clsImage>();
        SQLiteDatabase db = new ManageDB(Context).getWritableDatabase();
        // Select All Query
        String selectQuery = "SELECT "
                + ManageDB.ColumnsImage.IMAGE_ID + ","
                + ManageDB.ColumnsImage.IMAGE_NAME + ","
                + ManageDB.ColumnsImage.IMAGE_CATEGORY_ID + ","
                + ManageDB.ColumnsImage.IMAGE_IMAGE + ","
                + ManageDB.ColumnsImage.IMAGE_SOUND
                + " FROM " + ManageDB.TABLE_IMAGE;

        if (Field != null && value != null){
            value = "'" + value + "'";
            selectQuery = selectQuery + " WHERE " + Field + " = " + value;
        }

        selectQuery = selectQuery +  " ORDER BY " + ManageDB.ColumnsImage.IMAGE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                clsImage Record = new clsImage(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        new clsCategory(Context, Integer.parseInt(cursor.getString(2))),
                        cursor.getBlob(3),
                        cursor.getBlob(4)
                );
                // Add Record
                RecordList.add(Record);
            } while (cursor.moveToNext());
        }
        db.close();
        return RecordList;
    }

    // Update
    public int Update(clsImage NewValues) {
        SQLiteDatabase db = new ManageDB(Context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ManageDB.ColumnsImage.IMAGE_NAME, NewValues.get_name());
        values.put(ManageDB.ColumnsImage.IMAGE_IMAGE, NewValues.get_image());
        return db.update(ManageDB.TABLE_IMAGE, values, ManageDB.ColumnsImage.IMAGE_ID + " = " + NewValues.get_id(), null);
    }

    // Delete
    public void Delete(int record_id) {
        SQLiteDatabase db = new ManageDB(Context).getWritableDatabase();
        db.delete(ManageDB.TABLE_IMAGE, ManageDB.ColumnsImage.IMAGE_ID + " = " + record_id, null);
        db.close();
    }

    // Count Records
    public int CountRecords() {
        SQLiteDatabase db = new ManageDB(Context).getWritableDatabase();

        String countQuery = "SELECT " + ManageDB.ColumnsImage.IMAGE_ID + " FROM " + ManageDB.TABLE_IMAGE;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
}
