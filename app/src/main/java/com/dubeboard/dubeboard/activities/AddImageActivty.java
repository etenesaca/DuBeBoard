package com.dubeboard.dubeboard.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.dubeboard.dubeboard.clsCategory;
import com.dubeboard.dubeboard.clsImage;

public class AddImageActivty extends AppCompatActivity {
    Context Context = (Context) this;

    clsImage ImageObj = new clsImage(Context);
    EditText txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_activty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
                    Intent CategoryActivity = new Intent(AddImageActivty.this, com.dubeboard.dubeboard.activities.HomeActivity.class);
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