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
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dubeboard.dubeboard.ManageDB;
import com.dubeboard.dubeboard.R;
import com.dubeboard.dubeboard.clsCategory;
import com.dubeboard.dubeboard.item.adapter.CategoryItem;

import java.util.ArrayList;
import java.util.List;


public class CategoryActivity extends AppCompatActivity {
    Context Context = (Context) this;

    clsCategory CategoryObj = new clsCategory(Context);
    ArrayList<clsCategory> CategoryList = new ArrayList<clsCategory>();
    CategoryItem imageAdapter;
    GridView dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CategoryActivity = new Intent(com.dubeboard.dubeboard.activities.CategoryActivity.this, AddCategoryActivity.class);
                startActivity(CategoryActivity);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        // Rescatamos el Action Bar y activamos el boton HomeActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        dataList = (GridView) findViewById(R.id.gvCategorias);

        // Obtener todas las categorias de la base de datos
        final List<clsCategory> Categories = CategoryObj.getAll();
        for(clsCategory im : Categories){
            CategoryList.add(im);
        }

        dataList.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                final ImageView imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
                final TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);

                txtTitle.setText(null);
                imgIcon.setImageBitmap(null);
                imgIcon.setImageDrawable(Context.getResources().getDrawable(R.drawable.image_def_128));
            }
        });

        imageAdapter = new CategoryItem(this, R.layout.list_item, CategoryList);
        dataList.setAdapter(imageAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.btnAdd:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}