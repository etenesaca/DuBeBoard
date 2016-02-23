package com.dubeboard.dubeboard.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dubeboard.dubeboard.ManageDB;
import com.dubeboard.dubeboard.R;
import com.dubeboard.dubeboard.clsImage;

import java.util.HashMap;
import java.util.Locale;

public class AddImageActivity extends AppCompatActivity {
    Context Context = (Context) this;
    TextToSpeech tts;

    clsImage ImageObj = new clsImage(Context);
    EditText txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtName = (EditText) findViewById(R.id.txtName);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    //tts.setLanguage(Locale.UK);
                    tts.setLanguage(new Locale("spa", "ES"));
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnSpeech);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toSpeak = txtName.getText().toString();
                //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        // Rescatamos el Action Bar y activamos el boton HomeActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Poner Titulo en la barra de direcciones
        getSupportActionBar().setTitle("Nueva Imagen");

        //txtName = (EditText) findViewById(R.id.txtName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save:
                clsImage NewImage = new clsImage();
                NewImage.set_name(txtName.getText().toString());

                // Verificar si la categoria no ya no esta creda
                if (ImageObj.getRecords(ManageDB.ColumnsCategory.CATEGORY_NAME, NewImage.get_name()).size() > 0){
                    Toast.makeText(Context, "Ya hay una Imagen con este nombre", Toast.LENGTH_LONG);
                    //Snackbar.make(AddCategoryActivity.this, "Replace with your own action", Snackbar.LENGTH_LONG)
                    //        .setAction("Action", null).show();
                }else{
                    // Crear una categoria
                    ImageObj.AddRecord(NewImage);
                    Intent CategoryActivity = new Intent(AddImageActivity.this, com.dubeboard.dubeboard.activities.HomeActivity.class);
                    startActivity(CategoryActivity);
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_imagen, menu);
        return true;
    }
}