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
        //Bitmap bmp = BitmapFactory.decodeResource(Context.getResources(), R.drawable.zc_animales);
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
    void LoadDefData(){
        Create_Animales();
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
