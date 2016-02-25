package com.dubeboard.dubeboard.item.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dubeboard.dubeboard.ManageDB;
import com.dubeboard.dubeboard.R;
import com.dubeboard.dubeboard.clsCategory;
import com.dubeboard.dubeboard.clsImage;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class CategoryItem_1 extends ArrayAdapter<clsCategory> {
    Context context;
    int layoutResourceId;
    ArrayList<clsCategory> data = new ArrayList<clsCategory>();

    public CategoryItem_1(Context context, int layoutResourceId, ArrayList<clsCategory> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryHolder holder = null;

        if (convertView == null) {
            holder = new CategoryHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, null);
            convertView.setTag(holder);
        } else {
            holder = (CategoryHolder) convertView.getTag();
        }

        clsCategory Record = data.get(position);
        // Ejecutar la Tarea de acuerdo a la version de Android
        LoadView Task = new LoadView(convertView, Record);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            Task.execute();
        }
        return convertView;
    }

    /** Clase Asincrona para recuperar los datos de la fila **/
    protected class LoadView extends AsyncTask<String, Void, String> {
        protected clsCategory Record;
        protected View convertView;

        protected TextView txtTitle;
        protected TextView tvCount;
        protected ImageView imgIcon;

        public LoadView(View convertView, clsCategory Record) {
            this.Record = Record;
            this.convertView = convertView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            tvCount = (TextView) convertView.findViewById(R.id.tvCount);
            imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            txtTitle.setText(Record.get_name());
            byte[] outImage = Record.get_image();
            Bitmap icon;
            if (outImage != null){
                ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                icon = BitmapFactory.decodeStream(imageStream);
                imgIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }else{
                // En el caso de que no se pueda cargar la imagen
                icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_def_48x48);
                imgIcon.setScaleType(ImageView.ScaleType.CENTER);
            }
            imgIcon.setImageBitmap(icon);
            //icon = scaleDown(icon, 128, true);

            // Contar cuatos registros están vinculadas a esta Categoria
            clsImage ImageObj = new clsImage(context);
            int count = ImageObj.CountRecords(new Object[]{ManageDB.ColumnsImage.IMAGE_CATEGORY_ID, "=", Record.get_id()});
            tvCount.setText(count + "");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }

    static class CategoryHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}
