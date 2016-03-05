package com.dubeboard.dubeboard.item.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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

public class ImageItem_1 extends ArrayAdapter<clsImage> {
    Context context;
    Config Configuration;

    int layoutResourceId;
    ArrayList<clsImage> data = new ArrayList<clsImage>();

    ImageButton btnSpeech;
    TextToSpeech tts;

    public ImageItem_1(Context context, int layoutResourceId, ArrayList<clsImage> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.Configuration = new Config(context);
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final clsImage Record = data.get(position);
        /*
        if (convertView == null) {
            //LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceId, null);

            holder = new ViewHolder();

            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvCategory = (TextView) convertView.findViewById(R.id.tvCategory);
            holder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        */
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(layoutResourceId, null);

        holder = new ViewHolder();

        holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
        holder.tvCategory = (TextView) convertView.findViewById(R.id.tvCategory);
        holder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);

        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Configuration.getLangLocale());
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

            Context ctx = (Context) context;
            Typeface Roboto_bold = Typeface.createFromAsset(ctx.getAssets(), "fonts/Roboto-Bold.ttf");
            Typeface Roboto_light = Typeface.createFromAsset(ctx.getAssets(), "fonts/Roboto-Light.ttf");

            //v.tvName = (TextView) convertView.findViewById(R.id.tvName);
            //v.tvCategory = (TextView) convertView.findViewById(R.id.tvCategory);
            //v.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);

            v.tvName.setText(Record.get_name());
            v.tvName.setVisibility(View.VISIBLE);
            v.tvName.setTypeface(Roboto_light);
            v.tvCategory.setText(Record.get_category().get_name());
            v.tvCategory.setVisibility(View.VISIBLE);
            v.tvCategory.setTypeface(Roboto_bold);
            v.ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            v.ivImage.setImageBitmap((Bitmap) res.get("bmp"));
        }
    }

    static class ViewHolder
    {
        TextView tvName;
        TextView tvCategory;
        ImageView ivImage;
    }
}
