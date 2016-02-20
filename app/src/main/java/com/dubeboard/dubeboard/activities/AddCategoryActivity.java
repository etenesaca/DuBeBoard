package com.dubeboard.dubeboard.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.dubeboard.dubeboard.ManageDB;
import com.dubeboard.dubeboard.R;
import com.dubeboard.dubeboard.clsCategory;

public class AddCategoryActivity extends AppCompatActivity {
    Context Context = (Context) this;

    clsCategory CategoryObj = new clsCategory(Context);
    EditText txtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        // Rescatamos el Action Bar y activamos el boton HomeActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Poner Titulo en la barra de direcciones
        getSupportActionBar().setTitle("Agregar Categoría");

        txtName = (EditText) findViewById(R.id.txtName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save:
                clsCategory NewCategory = new clsCategory();
                NewCategory.set_name(txtName.getText().toString());


                // Verificar si la categoria no ya no esta creda
                if (NewCategory.get_name().equals("")) {
                    Snackbar.make(findViewById(android.R.id.content), "Primero Ingrese un nombre", Snackbar.LENGTH_LONG)
                            .show();
                } else if (CategoryObj.getRecords(ManageDB.ColumnsCategory.CATEGORY_NAME, NewCategory.get_name()).size() > 0){
                    Snackbar.make(findViewById(android.R.id.content), "Ya hay una categoria con este nombre", Snackbar.LENGTH_LONG)
                            .show();
                } else{
                    // Crear una categoria
                    CategoryObj.AddRecord(NewCategory);
                    Intent CategoryActivity = new Intent(AddCategoryActivity.this, com.dubeboard.dubeboard.activities.CategoryActivity.class);
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
        inflater.inflate(R.menu.add_category, menu);
        return true;
    }
}
