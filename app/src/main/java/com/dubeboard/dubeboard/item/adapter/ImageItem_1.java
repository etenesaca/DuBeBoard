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
import android.widget.Toast;

import com.dubeboard.dubeboard.R;
import com.dubeboard.dubeboard.clsImage;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Locale;

public class ImageItem_1 extends ArrayAdapter<clsImage> {
    Context context;
    int layoutResourceId;
    ArrayList<clsImage> data = new ArrayList<clsImage>();

    ImageButton btnSpeech;
    TextToSpeech tts;

    public ImageItem_1(Context context, int layoutResourceId, ArrayList<clsImage> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryHolder holder = null;
        final clsImage Record = data.get(position);

        if (convertView == null) {
            holder = new CategoryHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, null);
            convertView.setTag(holder);
        } else {
            holder = (CategoryHolder) convertView.getTag();
        }

        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    //tts.setLanguage(Locale.UK);
                    tts.setLanguage(new Locale("spa", "ES"));
                }
            }
        });

        btnSpeech = (ImageButton) convertView.findViewById(R.id.btnSpeech);
        btnSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(Record.get_name() + "", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

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
        protected clsImage Record;
        protected View convertView;

        protected TextView tvName;
        protected TextView tvCategory;
        protected ImageView ivImage;

        public LoadView(View convertView, clsImage Record) {
            this.Record = Record;
            this.convertView = convertView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            tvName = (TextView) convertView.findViewById(R.id.tvName);
            tvCategory = (TextView) convertView.findViewById(R.id.tvCategory);
            ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            tvName.setText(Record.get_name());
            tvCategory.setText(Record.get_category().get_name());
            byte[] outImage = Record.get_image();
            Bitmap icon;
            if (outImage != null){
                ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                icon = BitmapFactory.decodeStream(imageStream);

            }else{
                // En el caso de que no se pueda cargar la imagen
                icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.image_def_128);
            }
            //icon = scaleDown(icon, 128, true);
            ivImage.setImageBitmap(icon);


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
