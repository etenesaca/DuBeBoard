package com.dubeboard.dubeboard.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dubeboard.dubeboard.ListViewDinamicSize;
import com.dubeboard.dubeboard.R;
import com.dubeboard.dubeboard.clsCategory;
import com.dubeboard.dubeboard.clsImage;
import com.dubeboard.dubeboard.gl;
import com.dubeboard.dubeboard.item.adapter.CategoryItem_1;
import com.dubeboard.dubeboard.item.adapter.ImageItem_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CategoryActivity extends AppCompatActivity {
    Context Context = (Context) this;

    clsCategory CategoryObj = new clsCategory(Context);
    ArrayList<clsCategory> CategoryList = new ArrayList<clsCategory>();
    CategoryItem_1 adapter;
    GridView dataList;
    String[] menuItems = new String[]{"Editar", "Eliminar"};

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
                Intent CategoryActivity = new Intent(CategoryActivity.this, AddCategoryActivity.class);
                startActivity(CategoryActivity);
            }
        });

        // Rescatamos el Action Bar y activamos el boton HomeActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        dataList = (GridView) findViewById(R.id.gvCategorias);
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent CategoryActivity = new Intent(CategoryActivity.this, AddCategoryActivity.class);
                clsCategory ItemSelected = adapter.getItem(position);
                CategoryActivity.putExtra("current_id", ItemSelected.get_id() + "");
                startActivity(CategoryActivity);
            }
        });
        dataList.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                final ImageView imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
                final TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
                final TextView txtCount = (TextView) view.findViewById(R.id.txtCount);

                txtTitle.setText(null);
                txtCount.setVisibility(View.GONE);
                txtCount.setText(null);
                txtTitle.setVisibility(View.GONE);
                imgIcon.setImageBitmap(null);
                imgIcon.setScaleType(ImageView.ScaleType.CENTER);
                imgIcon.setImageDrawable(Context.getResources().getDrawable(R.drawable.img_def_48x48));
            }
        });

        LoadCategories Task = new LoadCategories();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else { Task.execute(); }
        registerForContextMenu(dataList);
    }

    protected class LoadCategories extends AsyncTask<String, Void, HashMap<String, Object>> {
        ProgressDialog pDialog;

        public LoadCategories() { }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Context ctx = (Context) Context;

            pDialog = new ProgressDialog(CategoryActivity.this);
            pDialog.setMessage("Cargando Datos");
            pDialog.setCancelable(false);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected HashMap<String, Object> doInBackground(String... params) {
            // Obtener todas las categorias de la base de datos
            final List<clsCategory> Categories = CategoryObj.getAll();
            for(clsCategory im : Categories){
                CategoryList.add(im);
            }
            adapter = new CategoryItem_1(Context, R.layout.list_item_category_1, CategoryList);
            return null;
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> res) {
            super.onPostExecute(res);
            dataList.setAdapter(adapter);
            pDialog.dismiss();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.gvCategorias) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle("Opciones");

            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()){
            case 0:
                break;
            case 1:
                clsCategory RowtoDelete = adapter.getItem(info.position);
                CategoryObj.Delete(RowtoDelete.get_id());
                adapter.remove(RowtoDelete);
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
        return true;
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