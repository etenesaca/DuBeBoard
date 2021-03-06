package com.dubeboard.dubeboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

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

    public void set_image(Bitmap bmp) {
        this.set_image(gl.BitmaptoByteArray(bmp));
    }

    public void set_image(byte[] _image) {
        this._image = _image;
    }

    public int getNumImages(Context context) {
        // Contar cuantos registros están vinculadas a esta Categoria
        clsImage ImageObj = new clsImage(context);
        int count = ImageObj.CountRecords(new Object[]{ManageDB.ColumnsImage.IMAGE_CATEGORY_ID, "=", this.get_id()});
        return count;
    }

    public List<clsImage> getChildImages(Context context) {
        // Obtener los registros hijos
        clsImage ImageObj = new clsImage(context);
        List<clsImage> ChildImages = ImageObj.getRecords(new Object[]{ManageDB.ColumnsImage.IMAGE_CATEGORY_ID, "=", this.get_id()});
        return ChildImages;
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
    public int AddRecord(String Name, int intImg) {
        clsCategory record = getByName(Name);
        Bitmap bmp = BitmapFactory.decodeResource(Context.getResources(), intImg);
        bmp = gl.scaleDown(bmp, 230, true);
        byte[] Img = gl.BitmaptoByteArray(bmp);

        if (record == null) {
            clsCategory NewRecord = new clsCategory();
            NewRecord.set_name(Name);
            NewRecord.set_image(Img);
            AddRecord(NewRecord);
            record = getByName(Name);
        } else {
            ContentValues vals = new ContentValues();
            vals.put(ColumnsCategory.CATEGORY_IMAGE, Img);
            Update(record.get_id(), vals);
        }
        return record.get_id();
    }

    // Obtener una Categoria
    public clsCategory getById(int Category_ID) {
        clsCategory result = null;
        List<clsCategory> Categories = getRecords(Category_ID);
        if (Categories.size() > 0)
            result = Categories.get(0);
        return result;
    }
    public clsCategory getByName(String Name) {
        clsCategory result = null;
        List<Object[]> args = new ArrayList<Object[]>();
        args.add(new Object[]{ColumnsCategory.CATEGORY_NAME, "=", Name});
        List<clsCategory> res =  getRecords(args);
        if ( res.size() > 0){
            result = res.get(0);
        }
        return result;
    }

    // Obtener todas las categorias
    public List<clsCategory> getAll() {
        return getRecords(new ArrayList<Object[]>());
    }

    public List<clsCategory> getRecords(int Category_ID) {
        List<Object[]> args = new ArrayList<Object[]>();
        args.add(new Object[] {ColumnsCategory.CATEGORY_ID, "=", Category_ID});
        return getRecords(args);
    }
    public List<clsCategory> getRecords(Object[] arg) {
        List<Object[]> args = new ArrayList<Object[]>();
        args.add(new Object[] {arg[0], arg[1], arg[2]});
        return getRecords(args);
    }

    public List<clsCategory> getRecords(List<Object[]> args) {
        List<clsCategory> RecordList = new ArrayList<clsCategory>();
        SQLiteDatabase db = new ManageDB(Context).getWritableDatabase();
        // Select All Query
        String selectQuery = "SELECT "
                + ColumnsCategory.CATEGORY_ID + ","
                + ColumnsCategory.CATEGORY_NAME + ","
                + ColumnsCategory.CATEGORY_IMAGE
                + " FROM " + ManageDB.TABLE_CATEGORIES;

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
        selectQuery = selectQuery + WhereQuery + " ORDER BY " + ColumnsCategory.CATEGORY_NAME;

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
    public int Update(int record_id, ContentValues values) {
        SQLiteDatabase db = new ManageDB(Context).getWritableDatabase();
        return db.update(ManageDB.TABLE_CATEGORIES, values, ColumnsCategory.CATEGORY_ID + " = " + record_id, null);
    }

    // Delete
    public void Delete(int record_id) {
        clsImage ImageObj = new clsImage(Context);
        List<clsImage> ChildImages = ImageObj.getRecords(new Object[]{ManageDB.ColumnsImage.IMAGE_CATEGORY_ID, "=", record_id});
        for (clsImage im : ChildImages){
            ImageObj.Delete(im.get_id());
        }
        ManageDB.DeleteRecord(Context, ManageDB.TABLE_CATEGORIES, record_id);
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

        String selectQuery = "SELECT " + ColumnsCategory.CATEGORY_ID + " FROM " + ManageDB.TABLE_CATEGORIES;
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