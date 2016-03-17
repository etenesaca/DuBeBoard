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
        ImageObj.AddRecord("Galletas", R.drawable.zi_galleta, CatID);
        ImageObj.AddRecord("Pastel", R.drawable.zi_pastel, CatID);
        ImageObj.AddRecord("Brownie", R.drawable.zi_brownie, CatID);
        ImageObj.AddRecord("Tarta", R.drawable.zi_tarta, CatID);
        ImageObj.AddRecord("Gelatina", R.drawable.zi_gelatina, CatID);
        ImageObj.AddRecord("Chicle", R.drawable.zi_chicle, CatID);
        ImageObj.AddRecord("Dulces", R.drawable.zi_dulces, CatID);
    }

    // Categoria VERBOS
    void Create_Verbos(){
        int Img = R.drawable.zi_verde;
        int CatID = CategoryObj.AddRecord("Verbos", R.drawable.zc_verbos);
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
        ImageObj.AddRecord("Poner", Img, CatID);
        ImageObj.AddRecord("Necesitar", Img, CatID);
    }

    // Categoria VERBOSPRESENTE
    void Create_VerbosPresente(){
        int Img = R.drawable.zi_verde;
        int CatID = CategoryObj.AddRecord("Verbos presente", R.drawable.zc_verbospresente);
        ImageObj.AddRecord("Estoy", Img, CatID);
        ImageObj.AddRecord("Soy", Img, CatID);
        ImageObj.AddRecord("Veo", Img, CatID);
        ImageObj.AddRecord("Escucho", Img, CatID);
        ImageObj.AddRecord("Despierto", Img, CatID);
        ImageObj.AddRecord("Juego", Img, CatID);
        ImageObj.AddRecord("Meto", Img, CatID);
        ImageObj.AddRecord("Rompo", Img, CatID);
        ImageObj.AddRecord("Pinto", Img, CatID);
        ImageObj.AddRecord("Tengo", Img, CatID);
        ImageObj.AddRecord("Siento", Img, CatID);
        ImageObj.AddRecord("Toco", Img, CatID);
        ImageObj.AddRecord("Busco", Img, CatID);
        ImageObj.AddRecord("Olvido", Img, CatID);
        ImageObj.AddRecord("Corto", Img, CatID);
        ImageObj.AddRecord("Amo", Img, CatID);
        ImageObj.AddRecord("Cojo", Img, CatID);
        ImageObj.AddRecord("Voy", Img, CatID);
        ImageObj.AddRecord("Vengo", Img, CatID);
        ImageObj.AddRecord("Cierro", Img, CatID);
        ImageObj.AddRecord("Caigo", Img, CatID);
        ImageObj.AddRecord("Traigo", Img, CatID);
        ImageObj.AddRecord("Llevo", Img, CatID);
        ImageObj.AddRecord("Ayudo", Img, CatID);
        ImageObj.AddRecord("Doy", Img, CatID);
        ImageObj.AddRecord("Recibo", Img, CatID);
        ImageObj.AddRecord("Quiero", Img, CatID);
        ImageObj.AddRecord("Duermo", Img, CatID);
        ImageObj.AddRecord("Como", Img, CatID);
        ImageObj.AddRecord("Bebo", Img, CatID);
        ImageObj.AddRecord("Hablo", Img, CatID);
        ImageObj.AddRecord("Estudio", Img, CatID);
        ImageObj.AddRecord("Pienso", Img, CatID);
        ImageObj.AddRecord("Nado", Img, CatID);
        ImageObj.AddRecord("Siento", Img, CatID);
        ImageObj.AddRecord("Paro", Img, CatID);
        ImageObj.AddRecord("Corro", Img, CatID);
        ImageObj.AddRecord("Camino", Img, CatID);
        ImageObj.AddRecord("Pongo", Img, CatID);
        ImageObj.AddRecord("Necesito", Img, CatID);
    }

    // Categoria VERBOSFUTURO
    void Create_VerbosFuturo(){
        int Img = R.drawable.zi_verde;
        int CatID = CategoryObj.AddRecord("Verbos futuro", R.drawable.zc_verbosfuturo);
        ImageObj.AddRecord("Estaré", Img, CatID);
        ImageObj.AddRecord("Seré", Img, CatID);
        ImageObj.AddRecord("Veré", Img, CatID);
        ImageObj.AddRecord("Escucharé", Img, CatID);
        ImageObj.AddRecord("Despertaré", Img, CatID);
        ImageObj.AddRecord("Jugaré", Img, CatID);
        ImageObj.AddRecord("Meteré", Img, CatID);
        ImageObj.AddRecord("Romperé", Img, CatID);
        ImageObj.AddRecord("Pintaré", Img, CatID);
        ImageObj.AddRecord("Tendré", Img, CatID);
        ImageObj.AddRecord("Sentiré", Img, CatID);
        ImageObj.AddRecord("Tocaré", Img, CatID);
        ImageObj.AddRecord("Buscaré", Img, CatID);
        ImageObj.AddRecord("Olvidaré", Img, CatID);
        ImageObj.AddRecord("Cortaré", Img, CatID);
        ImageObj.AddRecord("Amaré", Img, CatID);
        ImageObj.AddRecord("Cogeré", Img, CatID);
        ImageObj.AddRecord("Iré", Img, CatID);
        ImageObj.AddRecord("Vendré", Img, CatID);
        ImageObj.AddRecord("Cerrare", Img, CatID);
        ImageObj.AddRecord("Caeré", Img, CatID);
        ImageObj.AddRecord("Traeré", Img, CatID);
        ImageObj.AddRecord("Llevaré", Img, CatID);
        ImageObj.AddRecord("Ayudaré", Img, CatID);
        ImageObj.AddRecord("Daré", Img, CatID);
        ImageObj.AddRecord("Recibiré", Img, CatID);
        ImageObj.AddRecord("Querré", Img, CatID);
        ImageObj.AddRecord("Dormiré", Img, CatID);
        ImageObj.AddRecord("Comeré", Img, CatID);
        ImageObj.AddRecord("Beberé", Img, CatID);
        ImageObj.AddRecord("Hablaré", Img, CatID);
        ImageObj.AddRecord("Estudiaré", Img, CatID);
        ImageObj.AddRecord("Pensaré", Img, CatID);
        ImageObj.AddRecord("Nadaré", Img, CatID);
        ImageObj.AddRecord("Sentiré", Img, CatID);
        ImageObj.AddRecord("Pararé", Img, CatID);
        ImageObj.AddRecord("Correré", Img, CatID);
        ImageObj.AddRecord("Caminaré", Img, CatID);
        ImageObj.AddRecord("Pondré", Img, CatID);
        ImageObj.AddRecord("Necesitaré", Img, CatID);
    }

    // Categoria VERBOS PASADO
    void Create_VerbosPasado(){
        int Img = R.drawable.zi_verde;
        int CatID = CategoryObj.AddRecord("Verbos pasado", R.drawable.zc_verbospasado);
        ImageObj.AddRecord("Estaba", Img, CatID);
        ImageObj.AddRecord("Fui", Img, CatID);
        ImageObj.AddRecord("Ví", Img, CatID);
        ImageObj.AddRecord("Escuché", Img, CatID);
        ImageObj.AddRecord("Desperte", Img, CatID);
        ImageObj.AddRecord("Jugué", Img, CatID);
        ImageObj.AddRecord("Metí", Img, CatID);
        ImageObj.AddRecord("Rompí", Img, CatID);
        ImageObj.AddRecord("Pinté", Img, CatID);
        ImageObj.AddRecord("Tuve", Img, CatID);
        ImageObj.AddRecord("Sentí", Img, CatID);
        ImageObj.AddRecord("Toqué", Img, CatID);
        ImageObj.AddRecord("Busqué", Img, CatID);
        ImageObj.AddRecord("Olvidé", Img, CatID);
        ImageObj.AddRecord("Corté", Img, CatID);
        ImageObj.AddRecord("Amé", Img, CatID);
        ImageObj.AddRecord("Cogí", Img, CatID);
        ImageObj.AddRecord("Iba", Img, CatID);
        ImageObj.AddRecord("Vine", Img, CatID);
        ImageObj.AddRecord("Cerré", Img, CatID);
        ImageObj.AddRecord("Caí", Img, CatID);
        ImageObj.AddRecord("Traje", Img, CatID);
        ImageObj.AddRecord("Llevé", Img, CatID);
        ImageObj.AddRecord("Ayudé", Img, CatID);
        ImageObj.AddRecord("Di", Img, CatID);
        ImageObj.AddRecord("Recibí", Img, CatID);
        ImageObj.AddRecord("Quise", Img, CatID);
        ImageObj.AddRecord("Dormí", Img, CatID);
        ImageObj.AddRecord("Comí", Img, CatID);
        ImageObj.AddRecord("Bebí", Img, CatID);
        ImageObj.AddRecord("Hablé", Img, CatID);
        ImageObj.AddRecord("Estudie", Img, CatID);
        ImageObj.AddRecord("Pensé", Img, CatID);
        ImageObj.AddRecord("Nadé", Img, CatID);
        ImageObj.AddRecord("Senté", Img, CatID);
        ImageObj.AddRecord("Paré", Img, CatID);
        ImageObj.AddRecord("Corrí", Img, CatID);
        ImageObj.AddRecord("Caminé", Img, CatID);
        ImageObj.AddRecord("Puse", Img, CatID);
        ImageObj.AddRecord("Necesité", Img, CatID);
    }

    // Categoria PRENDAS
    void Create_Prendas(){
        int CatID = CategoryObj.AddRecord("Prendas", R.drawable.zc_vestir);
        ImageObj.AddRecord("Vestido", R.drawable.zi_vestido, CatID);
        ImageObj.AddRecord("Camisa", R.drawable.zi_camisa, CatID);
        ImageObj.AddRecord("Camiseta", R.drawable.zi_camiseta, CatID);
        ImageObj.AddRecord("Sueter", R.drawable.zi_sueter, CatID);
        ImageObj.AddRecord("Casaca", R.drawable.zi_casaca, CatID);
        ImageObj.AddRecord("Bufanda", R.drawable.zi_bufanda, CatID);
        ImageObj.AddRecord("Pantalones", R.drawable.zi_pantalones, CatID);
        ImageObj.AddRecord("Zapatos", R.drawable.zi_zapatos, CatID);
        ImageObj.AddRecord("Zapatos Deportivos", R.drawable.zi_zapato_depor, CatID);
        ImageObj.AddRecord("Medias", R.drawable.zi_medias, CatID);
        ImageObj.AddRecord("Pijama", R.drawable.zi_pijama, CatID);
        ImageObj.AddRecord("Pañuelo", R.drawable.zi_panuelo, CatID);
        ImageObj.AddRecord("Toalla", R.drawable.zi_toalla, CatID);
    }

    // Categoria TRANSPORTE
    void Create_Transporte(){
        int CatID = CategoryObj.AddRecord("Transporte", R.drawable.zc_transporte);
        ImageObj.AddRecord("Carro", R.drawable.zi_carro, CatID);
        ImageObj.AddRecord("Bus", R.drawable.zi_bus, CatID);
        ImageObj.AddRecord("Avión", R.drawable.zi_avion, CatID);
        ImageObj.AddRecord("Tren", R.drawable.zi_tren, CatID);
        ImageObj.AddRecord("Camión", R.drawable.zi_camion, CatID);
        ImageObj.AddRecord("Helicóptero", R.drawable.zi_helicoptero, CatID);
        ImageObj.AddRecord("Bicicleta", R.drawable.zi_bicicleta, CatID);
        ImageObj.AddRecord("Bote", R.drawable.zi_bote, CatID);
        ImageObj.AddRecord("Barco", R.drawable.zi_barco, CatID);
        ImageObj.AddRecord("Motocicleta", R.drawable.zi_motocicleta, CatID);
        ImageObj.AddRecord("Ambulancia", R.drawable.zi_ambulancia, CatID);
    }

    // Categoria COLORES

    void Create_Colores(){
        int CatID = CategoryObj.AddRecord("Colores", R.drawable.zc_colores);
        ImageObj.AddRecord("Blanco", R.drawable.zi_blanco, CatID);
        ImageObj.AddRecord("Negro", R.drawable.zi_negro, CatID);
        ImageObj.AddRecord("Azul", R.drawable.zi_azul, CatID);
        ImageObj.AddRecord("Amarillo", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Rojo", R.drawable.zi_rojo, CatID);
        ImageObj.AddRecord("Morado", R.drawable.zi_morado, CatID);
        ImageObj.AddRecord("Rosado", R.drawable.zi_rosado, CatID);
        ImageObj.AddRecord("Gris", R.drawable.zi_gris, CatID);
        ImageObj.AddRecord("Naranja", R.drawable.zi_naranja, CatID);
        ImageObj.AddRecord("Verde", R.drawable.zi_verde, CatID);
        ImageObj.AddRecord("Café", R.drawable.zi_cafecolor, CatID);

    }
    // Categoria Numeros
    void Create_Numeros(){
        int CatID = CategoryObj.AddRecord("Numeros", R.drawable.zc_numeros);
        ImageObj.AddRecord("Cero", R.drawable.zi_cero, CatID);
        ImageObj.AddRecord("Uno", R.drawable.zi_uno, CatID);
        ImageObj.AddRecord("Dos", R.drawable.zi_dos, CatID);
        ImageObj.AddRecord("Tres", R.drawable.zi_tres, CatID);
        ImageObj.AddRecord("Cuatro", R.drawable.zi_cuatro, CatID);
        ImageObj.AddRecord("Cinco", R.drawable.zi_cinco, CatID);
        ImageObj.AddRecord("Seis", R.drawable.zi_seis, CatID);
        ImageObj.AddRecord("Siete", R.drawable.zi_siete, CatID);
        ImageObj.AddRecord("Ocho", R.drawable.zi_ocho, CatID);
        ImageObj.AddRecord("Nueve", R.drawable.zi_nueve, CatID);
        ImageObj.AddRecord("Diez", R.drawable.zi_diez, CatID);
        ImageObj.AddRecord("Once", R.drawable.zi_once, CatID);
        ImageObj.AddRecord("Doce", R.drawable.zi_doce, CatID);
        ImageObj.AddRecord("Trece", R.drawable.zi_trece, CatID);
        ImageObj.AddRecord("Catorce", R.drawable.zi_catorce, CatID);
        ImageObj.AddRecord("Quince", R.drawable.zi_quince, CatID);
        ImageObj.AddRecord("Dieciseis", R.drawable.zi_dieciseis, CatID);
        ImageObj.AddRecord("Diecisiete", R.drawable.zi_diecisiete, CatID);
        ImageObj.AddRecord("Dieciocho", R.drawable.zi_dieciocho, CatID);
        ImageObj.AddRecord("Diecinueve", R.drawable.zi_diecinueve, CatID);
        ImageObj.AddRecord("Veinte", R.drawable.zi_veinte, CatID);

    }

    // Categoria Numeros
    void Create_Pronombres(){
        int Img = R.drawable.zi_blanco;
        int CatID = CategoryObj.AddRecord("Pronombres", R.drawable.zc_pronombres);
        ImageObj.AddRecord("Yo", Img, CatID);
        ImageObj.AddRecord("Tú", Img, CatID);
        ImageObj.AddRecord("El", Img, CatID);
        ImageObj.AddRecord("Ella", Img, CatID);
        ImageObj.AddRecord("Nosotros", Img, CatID);
        ImageObj.AddRecord("Ellos", Img, CatID);
        ImageObj.AddRecord("Ellas", Img, CatID);
        ImageObj.AddRecord("Ustedes", Img, CatID);
        ImageObj.AddRecord("Lo", Img, CatID);
        ImageObj.AddRecord("La", Img, CatID);
        ImageObj.AddRecord("Los", Img, CatID);
        ImageObj.AddRecord("Las", Img, CatID);
        ImageObj.AddRecord("Un", Img, CatID);
        ImageObj.AddRecord("Unas", Img, CatID);
        ImageObj.AddRecord("Mi", Img, CatID);
        ImageObj.AddRecord("Mío", Img, CatID);
        ImageObj.AddRecord("Me", Img, CatID);
        ImageObj.AddRecord("Le", Img, CatID);
        ImageObj.AddRecord("Les", Img, CatID);
        ImageObj.AddRecord("Qué", Img, CatID);
        ImageObj.AddRecord("Quién", Img, CatID);
        ImageObj.AddRecord("Cómo", Img, CatID);
        ImageObj.AddRecord("Cuando", Img, CatID);
        ImageObj.AddRecord("Por qué", Img, CatID);
        ImageObj.AddRecord("Porque", Img, CatID);
        ImageObj.AddRecord("Para qué", Img, CatID);

    }
    void Create_Adjetivos() {
        int CatID = CategoryObj.AddRecord("Adjetivos", R.drawable.zc_adjetivos);
        ImageObj.AddRecord("Hermoso", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Nuevo", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Viejo", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Feo", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Aburrido", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Alegre", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Alto", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Ancho", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Amargo", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Bajo", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Bonito", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Bueno", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Caliente", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Común", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Conocido", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Contento", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Corto", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Débil", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Delgado", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Diferente", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Dificil", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Divertido", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Dulce", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Duro", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Enfermo", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Fácil", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Feo", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Frío", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Fuerte", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Gordo", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Grande", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Húmedo", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Igual", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Interesante", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Joven", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Largo", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Lento", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Malo", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Mayor", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Menor", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Mucho", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Nuevo", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Peor", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Pequeño", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Pobre", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Poco", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Rápido", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Rico", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Salado", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Sano", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Seco", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Tímido", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Triste", R.drawable.zi_amarillo, CatID);
        ImageObj.AddRecord("Vivo", R.drawable.zi_amarillo, CatID);

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
            int num_cat = 19;
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
            publishProgress(cnum + " - " + num_cat + " Prendas"); cnum ++;
            Create_Prendas();
            publishProgress(cnum + " - " + num_cat + " Transporte"); cnum ++;
            Create_Transporte();
            publishProgress(cnum + " - " + num_cat + " Numeros"); cnum ++;
            Create_Numeros();
            publishProgress(cnum + " - " + num_cat + "Colores"); cnum ++;
            Create_Colores();
            publishProgress(cnum + " - " + num_cat + "Pronombres"); cnum ++;
            Create_Pronombres();
            publishProgress(cnum + " - " + num_cat + "Adjetivos"); cnum ++;
            Create_Adjetivos();
            publishProgress(cnum + " - " + num_cat + "Verbos presente"); cnum ++;
            Create_VerbosPresente();
            publishProgress(cnum + " - " + num_cat + "Verbos pasado"); cnum ++;
            Create_VerbosPasado();
            publishProgress(cnum + " - " + num_cat + "Verbos futuro"); cnum ++;
            Create_VerbosFuturo();
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
