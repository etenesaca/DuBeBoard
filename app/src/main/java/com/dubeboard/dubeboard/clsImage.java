package com.dubeboard.dubeboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    public clsImage(Context Context, int Category_ID) {
        clsImage res_image = new clsImage(Context).getById(Category_ID);
        this._id = res_image.get_id();
        this._name = res_image.get_name();
        this._image = res_image.get_image();
        this._category = res_image.get_category();
    }

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

    public void set_image(Bitmap bmp) {
        this.set_image(gl.BitmaptoByteArray(bmp));
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

    public int AddRecord(String Name, int intImg, int CategoryID) {
        clsImage record = getByName(Name);
        Bitmap bmp = BitmapFactory.decodeResource(Context.getResources(), intImg);
        bmp = gl.scaleDown(bmp, 220, true);
        byte[] Img = gl.BitmaptoByteArray(bmp);
        clsCategory NewCategory =  new clsCategory(Context, CategoryID);

        if (record == null){
            clsImage NewRecord = new clsImage();
            NewRecord.set_name(Name);
            NewRecord.set_category(NewCategory);
            NewRecord.set_image(Img);
            AddRecord(NewRecord);
            record = getByName(Name);
        } else {
            ContentValues vals = new ContentValues();
            vals.put(ManageDB.ColumnsImage.IMAGE_IMAGE, Img);
            vals.put(ManageDB.ColumnsImage.IMAGE_CATEGORY_ID, NewCategory.get_id());
            Update(record.get_id(), vals);
        }
        return record.get_id();
    }

    public void AddRecord(clsImage NewRecord) {
        SQLiteDatabase db = new ManageDB(Context).getWritableDatabase();
        // Armar el Insert
        ContentValues values = new ContentValues();
        values.put(ManageDB.ColumnsImage.IMAGE_NAME, NewRecord.get_name()); // Nombre
        values.put(ManageDB.ColumnsImage.IMAGE_CATEGORY_ID, NewRecord.get_category().get_id()); // Categoria
        values.put(ManageDB.ColumnsImage.IMAGE_IMAGE, NewRecord.get_image()); // Imagen
        values.put(ManageDB.ColumnsImage.IMAGE_SOUND, NewRecord.get_sound()); // Sonido

        // Insertar registro
        db.insert(ManageDB.TABLE_IMAGE, null, values);
        db.close();
    }

    // Obtener una Imagen
    public clsImage getById(int Image_ID) {
        clsImage result = null;
        List<clsImage> Images = getRecords(Image_ID);
        if (Images.size() > 0)
            result = Images.get(0);
        return result;
    }
    public clsImage getByName(String Name) {
        clsImage result = null;
        List<Object[]> args = new ArrayList<Object[]>();
        args.add(new Object[]{ManageDB.ColumnsImage.IMAGE_NAME, "=", Name});
        List<clsImage> res =  getRecords(args);
        if ( res.size() > 0){
            result = res.get(0);
        }
        return result;
    }

    // Obtener todas las categorias
    public List<clsImage> getAll() {
        return getRecords(new ArrayList<Object[]>());
    }
    public List<clsImage> getRecords(int Image_ID) {
        List<Object[]> args = new ArrayList<Object[]>();
        args.add(new Object[] {ManageDB.ColumnsImage.IMAGE_ID, "=", Image_ID});
        return getRecords(args);
    }
    public List<clsImage> getRecords(Object[] arg) {
        List<Object[]> args = new ArrayList<Object[]>();
        args.add(new Object[]{arg[0], arg[1], arg[2]});
        return getRecords(args);
    }
    public List<clsImage> getRecords(List<Object[]> args) {
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

        String WhereQuery = "";
        String _and = " and ";
        if (args.size() > 0)
            WhereQuery = " WHERE ";
        for (Object[] arg: args) {
            String field = arg[0].toString();
            String operator = arg[1].toString();
            Object value = arg[2];
            if (field != null && value != null){
                if (value instanceof String){
                    value = "'" + value + "'";
                }
                WhereQuery = WhereQuery + field + " " + operator + " " + value + _and;
            }
        }
        if (WhereQuery.length() > _and.length() && WhereQuery.substring(WhereQuery.length() - _and.length(), WhereQuery.length()).equals(_and)){
            WhereQuery = WhereQuery.substring(0, WhereQuery.length() - _and.length());
        }
        selectQuery = selectQuery + WhereQuery +  " ORDER BY " + ManageDB.ColumnsImage.IMAGE_CATEGORY_ID + ","  + ManageDB.ColumnsImage.IMAGE_NAME;

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
    public int Update(int record_id, ContentValues values) {
        SQLiteDatabase db = new ManageDB(Context).getWritableDatabase();
        return db.update(ManageDB.TABLE_IMAGE, values, ManageDB.ColumnsImage.IMAGE_ID + " = " + record_id, null);
    }

    // Delete
    public void Delete(int record_id) {
        ManageDB.DeleteRecord(Context, ManageDB.TABLE_IMAGE, record_id);
    }

    // Count Records
    public int CountRecords() {
        return CountRecords(new ArrayList<Object[]>());
    }
    public int CountRecords(Object[] arg) {
        List<Object[]> args = new ArrayList<Object[]>();
        args.add(new Object[]{arg[0], arg[1], arg[2]});
        return CountRecords(args);
    }
    public int CountRecords(List<Object[]> args) {
        SQLiteDatabase db = new ManageDB(Context).getWritableDatabase();

        String selectQuery = "SELECT " + ManageDB.ColumnsImage.IMAGE_ID + " FROM " + ManageDB.TABLE_IMAGE;
        String WhereQuery = "";
        String _and = " and ";
        if (args.size() > 0)
            WhereQuery = " WHERE ";
        for (Object[] arg: args) {
            String field = arg[0].toString();
            String operator = arg[1].toString();
            Object value = arg[2];
            if (field != null && value != null){
                if (value instanceof String){
                    value = "'" + value + "'";
                }
                WhereQuery = WhereQuery + field + " " + operator + " " + value + _and;
            }
        }
        if (WhereQuery.length() > _and.length() && WhereQuery.substring(WhereQuery.length() - _and.length(), WhereQuery.length()).equals(_and)){
            WhereQuery = WhereQuery.substring(0, WhereQuery.length() - _and.length());
        }
        selectQuery = selectQuery + WhereQuery;

        Cursor cursor = db.rawQuery(selectQuery, null);
        int result = cursor.getCount();
        db.close();
        return result;
    }
}
