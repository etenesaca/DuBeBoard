package com.dubeboard.dubeboard.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dubeboard.dubeboard.ManageDB;
import com.dubeboard.dubeboard.R;
import com.dubeboard.dubeboard.clsCategory;
import com.dubeboard.dubeboard.clsImage;
import com.dubeboard.dubeboard.gl;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    Context Context = (Context) this;
    clsCategory CategoryObj = new clsCategory(Context);
    clsImage ImageObj = new clsImage(Context);

    Button btnLoadDefData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnLoadDefData = (Button) findViewById(R.id.btnLoadDefData);
        btnLoadDefData.setOnClickListener(LoadDefDataHandler);

        // Rescatamos el Action Bar y activamos el boton HomeActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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

    void LoadDefData(){
        Create_Animales();
        Create_Cocina();
        Create_Comida();
        Create_Cosas();
        Create_Escuela();
        Create_Hogar();
        Create_Lugares();
    }

    View.OnClickListener LoadDefDataHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoadDefData();
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
