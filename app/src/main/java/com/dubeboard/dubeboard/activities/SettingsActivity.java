package com.dubeboard.dubeboard.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dubeboard.dubeboard.Config;
import com.dubeboard.dubeboard.R;
import com.dubeboard.dubeboard.clsCategory;
import com.dubeboard.dubeboard.clsImage;
import com.dubeboard.dubeboard.gl;

import java.util.ArrayList;
import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {
    Context Context = (Context) this;
    Config Configuration = new Config(Context);

    clsCategory CategoryObj = new clsCategory(Context);
    clsImage ImageObj = new clsImage(Context);

    TextView lblLanguaje;
    TextView lblSizeText;
    TextView lblSizeTextSample;
    TextView lblLoadDefData;
    Button btnLoadDefData;
    Spinner spLanguaje;
    Spinner spSizeText;

    String[] lstLanguaje = new String[]{
            "Español - spa_ES",
            "Español - spa_MEX",
            "Español - es_US",
            "Inglés - en_US",
            "Inglés - en_CA",
            "Alemán - de_DE",
            "Italiano - it_IT",
            "Italiano - it_CH",
            "Francés - fr_FR",
            "Francés - fr_CA",
            "Portugues - pt_BR",
            "Portugues - pt_PT",
            "Chino - zh_CN",
            "Japones - ja_JP",
            "Ruso - ru_RU",
            "Árabe - ar_EG",
            "Árabe - ar_IL"
    };
    String[] lstSizeText = new String[]{"12","14","16","18","20","24","28","32","40","48"};

    HashMap<String,Integer> MapLanguaje = new HashMap<String,Integer>();
    HashMap<String,Integer> MapTextSize = new HashMap<String,Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Crear una instancia de la Clase de Configuraciones

        btnLoadDefData = (Button) findViewById(R.id.btnLoadDefData);
        btnLoadDefData.setOnClickListener(LoadDefDataHandler);

        getSupportActionBar().setTitle("Configuraciones");

        // Rescatamos el Action Bar y activamos el boton HomeActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        lblLanguaje = (TextView) findViewById(R.id.lblLanguaje);
        lblSizeText = (TextView) findViewById(R.id.lblSizeText);
        lblSizeTextSample = (TextView) findViewById(R.id.lblSizeTextSample);
        lblLoadDefData = (TextView) findViewById(R.id.lblLoadDefData);
        spLanguaje = (Spinner) findViewById(R.id.spLanguaje);
        spSizeText = (Spinner) findViewById(R.id.spSizeText);

        // Establecer las fuentes
        Typeface Roboto_light = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        Typeface Roboto_bold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");

        lblLanguaje.setTypeface(Roboto_bold);
        lblSizeText.setTypeface(Roboto_bold);
        lblLoadDefData.setTypeface(Roboto_bold);

        //ArrayAdapter<String> adapterLanguaje = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, lstLanguaje);
        //ArrayAdapter<String> adapterSizeText = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, lstSizeText);

        ArrayAdapter<String> adapterLanguaje = new ArrayAdapter(Context, android.R.layout.simple_spinner_item, lstLanguaje);
        ArrayAdapter<String> adapterSizeText = new ArrayAdapter(Context, android.R.layout.simple_spinner_item, lstSizeText);
        spLanguaje.setAdapter(adapterLanguaje);
        spSizeText.setAdapter(adapterSizeText);
        spSizeText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lblSizeTextSample.setTextSize(Integer.parseInt(spSizeText.getSelectedItem() + ""));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Recuperar los datos de la configuración
        spLanguaje.setSelection(gl.getIndexSpinner(spLanguaje, Configuration.getLang()));
        spSizeText.setSelection(gl.getIndexSpinner(spSizeText, Configuration.getTextSize() + ""));
        lblSizeTextSample.setTextSize(Configuration.getTextSize());
    }

    // Categoria Animales
    void Create_Animales(){
        int CatID = CategoryObj.AddRecord("Animales", R.drawable.zc_animales);
        ImageObj.AddRecord("Perro", R.drawable.zi_perro, CatID);
        ImageObj.AddRecord("Gato", R.drawable.zi_gato, CatID);
        ImageObj.AddRecord("León", R.drawable.zi_leon, CatID);
        ImageObj.AddRecord("Tortuga", R.drawable.zi_tortuga, CatID);
        ImageObj.AddRecord("Caballo", R.drawable.zi_caballo, CatID);
        ImageObj.AddRecord("Loro", R.drawable.zi_loro, CatID);
        ImageObj.AddRecord("Camello", R.drawable.zi_camello, CatID);
        ImageObj.AddRecord("Vaca", R.drawable.zi_vaca, CatID);
        ImageObj.AddRecord("Ratón", R.drawable.zi_raton, CatID);
        ImageObj.AddRecord("Conejo", R.drawable.zi_conejo, CatID);
        ImageObj.AddRecord("Elefante", R.drawable.zi_elefante, CatID);
        ImageObj.AddRecord("Pato", R.drawable.zi_pato, CatID);
        ImageObj.AddRecord("Gallo", R.drawable.zi_gallo, CatID);
        ImageObj.AddRecord("Canguro", R.drawable.zi_canguro, CatID);
        ImageObj.AddRecord("Oveja", R.drawable.zi_oveja, CatID);
        ImageObj.AddRecord("Llama", R.drawable.zi_llama, CatID);
        ImageObj.AddRecord("Tigre", R.drawable.zi_tigre, CatID);
        ImageObj.AddRecord("Oso", R.drawable.zi_oso, CatID);
        ImageObj.AddRecord("Oso polar", R.drawable.zi_oso_polar, CatID);
        ImageObj.AddRecord("Lobo", R.drawable.zi_lobo, CatID);
        ImageObj.AddRecord("Jaguar", R.drawable.zi_jaguar, CatID);
        ImageObj.AddRecord("Pantera", R.drawable.zi_pantera, CatID);
    }

    // Categoria COCINA
    void Create_Cocina(){
        int CatID = CategoryObj.AddRecord("Cocina", R.drawable.zc_cocina);
        ImageObj.AddRecord("Vaso", R.drawable.zi_vaso, CatID);
        ImageObj.AddRecord("Plato", R.drawable.zi_plato, CatID);
        ImageObj.AddRecord("Servilleta", R.drawable.zi_servilleta, CatID);
        ImageObj.AddRecord("Tenedor", R.drawable.zi_tenedor, CatID);
        ImageObj.AddRecord("Taza", R.drawable.zi_taza, CatID);
        ImageObj.AddRecord("Salero", R.drawable.zi_salero, CatID);
        ImageObj.AddRecord("Cuchillo", R.drawable.zi_cuchillo, CatID);
        ImageObj.AddRecord("Cuchara", R.drawable.zi_cuchara, CatID);
    }

    // Categoria COMIDA
    void Create_Comida(){
        int CatID = CategoryObj.AddRecord("Comida", R.drawable.zc_comida);
        ImageObj.AddRecord("Sanduche", R.drawable.zi_sanduche, CatID);
        ImageObj.AddRecord("Café", R.drawable.zi_cafe, CatID);
        ImageObj.AddRecord("Pan", R.drawable.zi_pan, CatID);
        ImageObj.AddRecord("Verduras", R.drawable.zi_verduras, CatID);
        ImageObj.AddRecord("Fruta", R.drawable.zi_fruta, CatID);
        ImageObj.AddRecord("Leche", R.drawable.zi_leche, CatID);
        ImageObj.AddRecord("Pescado", R.drawable.zi_pescado, CatID);
        ImageObj.AddRecord("Huevo", R.drawable.zi_huevo, CatID);
        ImageObj.AddRecord("Carne", R.drawable.zi_carne, CatID);
        ImageObj.AddRecord("Pollo", R.drawable.zi_pollo, CatID);
        ImageObj.AddRecord("Sopa", R.drawable.zi_sopa, CatID);
        ImageObj.AddRecord("Jugo", R.drawable.zi_jugo, CatID);
        ImageObj.AddRecord("Tortilla", R.drawable.zi_tortilla, CatID);
        ImageObj.AddRecord("Fréjol", R.drawable.zi_frejol, CatID);
    }

    // Categoria COSAS
    void Create_Cosas(){
        int CatID = CategoryObj.AddRecord("Cosas", R.drawable.zc_cosas);
        ImageObj.AddRecord("Juguete", R.drawable.zi_juguete, CatID);
        ImageObj.AddRecord("Reloj", R.drawable.zi_reloj, CatID);
        ImageObj.AddRecord("Celular", R.drawable.zi_celular, CatID);
        ImageObj.AddRecord("Peine", R.drawable.zi_peine, CatID);
        ImageObj.AddRecord("Jabón", R.drawable.zi_jabon, CatID);
        ImageObj.AddRecord("Almohada", R.drawable.zi_almohadas, CatID);
        ImageObj.AddRecord("Radio", R.drawable.zi_radio, CatID);
        ImageObj.AddRecord("Televisor", R.drawable.zi_televisor, CatID);
        ImageObj.AddRecord("Botella", R.drawable.zi_botella, CatID);
        ImageObj.AddRecord("Silla de ruedas", R.drawable.zi_silla_de_ruedas, CatID);
        ImageObj.AddRecord("Computador", R.drawable.zi_computador, CatID);
        ImageObj.AddRecord("Tableta", R.drawable.zi_tablet, CatID);
        ImageObj.AddRecord("Desodorante", R.drawable.zi_desodorante, CatID);
    }

    // Categoria ESCUELA
    void Create_Escuela(){
        int CatID = CategoryObj.AddRecord("Escuela", R.drawable.zc_escuelaprincipal);
        ImageObj.AddRecord("Escuela", R.drawable.zi_escuela, CatID);
        ImageObj.AddRecord("Regla", R.drawable.zi_regla, CatID);
        ImageObj.AddRecord("Tarea", R.drawable.zi_tarea, CatID);
        ImageObj.AddRecord("Pinturas", R.drawable.zi_pinturas, CatID);
        ImageObj.AddRecord("Papel", R.drawable.zi_papel, CatID);
        ImageObj.AddRecord("Borrador", R.drawable.zi_borrador, CatID);
        ImageObj.AddRecord("Libros", R.drawable.zi_libros, CatID);
        ImageObj.AddRecord("Lápiz", R.drawable.zi_lapiz, CatID);
        ImageObj.AddRecord("Cuaderno", R.drawable.zi_cuaderno, CatID);
        ImageObj.AddRecord("Pizarrón", R.drawable.zi_pizarron, CatID);
        ImageObj.AddRecord("Tijeras", R.drawable.zi_tijeras, CatID);
        ImageObj.AddRecord("Mochila", R.drawable.zi_mochila, CatID);
        ImageObj.AddRecord("Goma", R.drawable.zi_goma, CatID);
    }

    // Categoria HOGAR
    void Create_Hogar(){
        int CatID = CategoryObj.AddRecord("Hogar", R.drawable.zc_casahogar);
        ImageObj.AddRecord("Hogar", R.drawable.zc_casahogar, CatID);
        ImageObj.AddRecord("Baño", R.drawable.zi_banio, CatID);
        ImageObj.AddRecord("Dormitorio", R.drawable.zi_dormitorio, CatID);
        ImageObj.AddRecord("Cama", R.drawable.zi_cama, CatID);
        ImageObj.AddRecord("Puerta", R.drawable.zi_puerta, CatID);
        ImageObj.AddRecord("Silla", R.drawable.zi_silla, CatID);
        ImageObj.AddRecord("Jardín", R.drawable.zi_jardin, CatID);
        ImageObj.AddRecord("Gradas", R.drawable.zi_gradas, CatID);
        ImageObj.AddRecord("Comedor", R.drawable.zi_comedor, CatID);
        ImageObj.AddRecord("Piso", R.drawable.zi_piso, CatID);
        ImageObj.AddRecord("Techo", R.drawable.zi_techo, CatID);
        ImageObj.AddRecord("Mesa", R.drawable.zi_mesa, CatID);
        ImageObj.AddRecord("Sillón", R.drawable.zi_sillon, CatID);
        ImageObj.AddRecord("Casa", R.drawable.zi_casa, CatID);
        ImageObj.AddRecord("Sala", R.drawable.zi_sala, CatID);
        ImageObj.AddRecord("Cocina", R.drawable.zi_cocina, CatID);
        ImageObj.AddRecord("Ventana", R.drawable.zi_ventana, CatID);
    }

    // Categoria LUGARES
    void Create_Lugares(){
        int CatID = CategoryObj.AddRecord("Lugares", R.drawable.zc_lugares);
        ImageObj.AddRecord("Bosque", R.drawable.zi_bosque, CatID);
        ImageObj.AddRecord("Playa", R.drawable.zi_banio, CatID);
        ImageObj.AddRecord("Cine", R.drawable.zi_cine, CatID);
        ImageObj.AddRecord("Tienda", R.drawable.zi_tienda, CatID);
        ImageObj.AddRecord("Parque", R.drawable.zi_parque, CatID);
        ImageObj.AddRecord("Zoológico", R.drawable.zi_zoo, CatID);
        ImageObj.AddRecord("Parque de diversiones", R.drawable.zi_pardediversiones, CatID);
        ImageObj.AddRecord("Supermercado", R.drawable.zi_supermercado, CatID);
        ImageObj.AddRecord("Panaderia", R.drawable.zi_panaderia, CatID);
        ImageObj.AddRecord("Restaurante", R.drawable.zi_restaurant, CatID);
        ImageObj.AddRecord("Farmacia", R.drawable.zi_farmacia, CatID);
        ImageObj.AddRecord("Hospital", R.drawable.zi_hospital, CatID);
    }

    // Categoria CUERPO
    void Create_Cuerpo(){
        int CatID = CategoryObj.AddRecord("Partes del Cuerpo", R.drawable.zc_cuerpo);
        ImageObj.AddRecord("Ojo", R.drawable.zi_ojo, CatID);
        ImageObj.AddRecord("Pies", R.drawable.zi_pies, CatID);
        ImageObj.AddRecord("Piernas", R.drawable.zi_piernas, CatID);
        ImageObj.AddRecord("Espalda", R.drawable.zi_espalda, CatID);
        ImageObj.AddRecord("Labios", R.drawable.zi_labios, CatID);
        ImageObj.AddRecord("Manos", R.drawable.zi_manos, CatID);
        ImageObj.AddRecord("Pecho", R.drawable.zi_pecho, CatID);
        ImageObj.AddRecord("Nariz", R.drawable.zi_nariz, CatID);
        ImageObj.AddRecord("Cuello", R.drawable.zi_cuello, CatID);
        ImageObj.AddRecord("Dientes", R.drawable.zi_dientes, CatID);
        ImageObj.AddRecord("Lengua", R.drawable.zi_lengua, CatID);
        ImageObj.AddRecord("Rodilla", R.drawable.zi_rodilla, CatID);
        ImageObj.AddRecord("Brazo", R.drawable.zi_brazo, CatID);
        ImageObj.AddRecord("Oreja", R.drawable.zi_oreja, CatID);
        ImageObj.AddRecord("Cabello", R.drawable.zi_cabello, CatID);
        ImageObj.AddRecord("Estomago", R.drawable.zi_estomago, CatID);
    }

    // Categoria POSTRES
    void Create_Postres(){
        int CatID = CategoryObj.AddRecord("Postres", R.drawable.zc_postre);
        ImageObj.AddRecord("Postre", R.drawable.zc_postre, CatID);
        ImageObj.AddRecord("Chocolate", R.drawable.zi_chocolate, CatID);
        ImageObj.AddRecord("Galeltas", R.drawable.zi_galleta, CatID);
        ImageObj.AddRecord("Pastel", R.drawable.zi_pastel, CatID);
        ImageObj.AddRecord("Brawonie", R.drawable.zi_brownie, CatID);
        ImageObj.AddRecord("Tarta", R.drawable.zi_tarta, CatID);
        ImageObj.AddRecord("Gelatina", R.drawable.zi_gelatina, CatID);
        ImageObj.AddRecord("Chicle", R.drawable.zi_chicle, CatID);
        ImageObj.AddRecord("Dulces", R.drawable.zi_dulces, CatID);
    }

    // Categoria VERBOS
    void Create_Verbos(){
        int Img = R.drawable.image_def_128;
        int CatID = CategoryObj.AddRecord("Verbos", Img);
        ImageObj.AddRecord("Estar", Img, CatID);
        ImageObj.AddRecord("Ser", Img, CatID);
        ImageObj.AddRecord("Ver", Img, CatID);
        ImageObj.AddRecord("Oir", Img, CatID);
        ImageObj.AddRecord("Despertar", Img, CatID);
        ImageObj.AddRecord("Jugar", Img, CatID);
        ImageObj.AddRecord("Meter", Img, CatID);
        ImageObj.AddRecord("Romper", Img, CatID);
        ImageObj.AddRecord("Pintar", Img, CatID);
        ImageObj.AddRecord("Tener", Img, CatID);
        ImageObj.AddRecord("Sentir", Img, CatID);
        ImageObj.AddRecord("Tocar", Img, CatID);
        ImageObj.AddRecord("Buscar", Img, CatID);
        ImageObj.AddRecord("Olvidar", Img, CatID);
        ImageObj.AddRecord("Cortar", Img, CatID);
        ImageObj.AddRecord("Amar", Img, CatID);
        ImageObj.AddRecord("Coger", Img, CatID);
        ImageObj.AddRecord("Ir", Img, CatID);
        ImageObj.AddRecord("Venir", Img, CatID);
        ImageObj.AddRecord("Cerrar", Img, CatID);
        ImageObj.AddRecord("Caer", Img, CatID);
        ImageObj.AddRecord("Traer", Img, CatID);
        ImageObj.AddRecord("Llevar", Img, CatID);
        ImageObj.AddRecord("Ayudar", Img, CatID);
        ImageObj.AddRecord("Dar", Img, CatID);
        ImageObj.AddRecord("Recibir", Img, CatID);
        ImageObj.AddRecord("Querer", Img, CatID);
        ImageObj.AddRecord("Dormir", Img, CatID);
        ImageObj.AddRecord("Comer", Img, CatID);
        ImageObj.AddRecord("Beber", Img, CatID);
        ImageObj.AddRecord("Hablar", Img, CatID);
        ImageObj.AddRecord("Estudiar", Img, CatID);
        ImageObj.AddRecord("Pensar", Img, CatID);
        ImageObj.AddRecord("Nadar", Img, CatID);
        ImageObj.AddRecord("Sentar", Img, CatID);
        ImageObj.AddRecord("Parar", Img, CatID);
        ImageObj.AddRecord("Correr", Img, CatID);
        ImageObj.AddRecord("Caminar", Img, CatID);
        ImageObj.AddRecord("Puner", Img, CatID);
        ImageObj.AddRecord("Necesitar", Img, CatID);

    }

    protected class LoadData extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(SettingsActivity.this, "Cargando Datos", "Esperando..");
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            pDialog.setMessage(String.valueOf(values[0]));
        }

        @Override
        protected String doInBackground(String... params) {
            int num_cat = 10;
            int cnum = 1;
            publishProgress(cnum + " - " + num_cat + " Animales"); cnum ++;
            Create_Animales();
            publishProgress(cnum + " - " + num_cat + " Cocina"); cnum ++;
            Create_Cocina();
            publishProgress(cnum + " - " + num_cat + " Comida"); cnum ++;
            Create_Comida();
            publishProgress(cnum + " - " + num_cat + " Cosas"); cnum ++;
            Create_Cosas();
            publishProgress(cnum + " - " + num_cat + " Escuela"); cnum ++;
            Create_Escuela();
            publishProgress(cnum + " - " + num_cat + " Hogar"); cnum ++;
            Create_Hogar();
            publishProgress(cnum + " - " + num_cat + " Lugares"); cnum ++;
            Create_Lugares();
            publishProgress(cnum + " - " + num_cat + " Cuerpo"); cnum ++;
            Create_Cuerpo();
            publishProgress(cnum + " - " + num_cat + " Postres"); cnum ++;
            Create_Postres();
            publishProgress(cnum + " - " + num_cat + " Verbos"); cnum ++;
            Create_Verbos();
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            pDialog.dismiss();
        }
    }

    View.OnClickListener LoadDefDataHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoadData Task = new LoadData();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                Task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else { Task.execute(); }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Intent ImageActivity = new Intent(SettingsActivity.this, HomeActivity.class);
        String CategorySelected;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save:
                // Guardar las preferencias
                Configuration.setLang(spLanguaje.getSelectedItem() + "");
                Configuration.setTextSize(spSizeText.getSelectedItem() + "");

                final Intent HomeActivity = new Intent(SettingsActivity.this, HomeActivity.class);
                finish();
                startActivity(ImageActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }

}
