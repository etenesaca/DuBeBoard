package com.dubeboard.dubeboard.item.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dubeboard.dubeboard.Config;
import com.dubeboard.dubeboard.R;
import com.dubeboard.dubeboard.clsImage;
import com.dubeboard.dubeboard.clsImage;
import com.dubeboard.dubeboard.gl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ImageItem_Home extends ArrayAdapter<clsImage> {
    Context context;
    Config Configuration = new Config(context);

    int layoutResourceId;
    ArrayList<clsImage> data = new ArrayList<clsImage>();

    public ImageItem_Home(Context context, int layoutResourceId, ArrayList<clsImage> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        clsImage Record = data.get(position);
        /*
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, null);

            holder = new ViewHolder();
            //holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            //holder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        */
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(layoutResourceId, null);

        holder = new ViewHolder();
        holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        holder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
        convertView.setTag(holder);

        // Ejecutar la Tarea de acuerdo a la version de Android
        LoadView Task = new LoadView(convertView, Record);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, holder);
        } else {
            Task.execute(holder);
        }
        return convertView;
    }

    /** Clase Asincrona para recuperar los datos de la fila **/
    protected class LoadView extends AsyncTask<ViewHolder, Void, HashMap<String, Object>> {
        protected ViewHolder v;

        protected clsImage Record;
        protected View convertView;

        public LoadView(View convertView, clsImage Record) {
            this.Record = Record;
            this.convertView = convertView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected HashMap<String, Object> doInBackground(ViewHolder... params) {
            v = params[0];
            HashMap<String, Object> res = new HashMap<String, Object>();
            // Obtener la imagen
            res.put("bmp", gl.build_image(context, Record.get_image()));
            return res;
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> res) {
            super.onPostExecute(res);

            //v.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            //v.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);

            v.txtTitle.setText(Record.get_name());
            v.txtTitle.setVisibility(View.VISIBLE);
            v.imgIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            v.imgIcon.setImageBitmap((Bitmap) res.get("bmp"));
        }
    }

    static class ViewHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}
