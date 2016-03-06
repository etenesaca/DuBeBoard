package com.dubeboard.dubeboard.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dubeboard.dubeboard.ListViewDinamicSize;
import com.dubeboard.dubeboard.ManageDB;
import com.dubeboard.dubeboard.R;
import com.dubeboard.dubeboard.clsCategory;
import com.dubeboard.dubeboard.gl;
import com.dubeboard.dubeboard.item.adapter.CategoryItem_1;
import com.dubeboard.dubeboard.item.adapter.ImageItem_1;
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
                imgIcon.setImageDrawable(Context.getResources().getDrawable(R.drawable.loading_24x24));
            }
        });

        LoadListData();
        registerForContextMenu(dataList);
    }

    void LoadListData(){
        LoadListData("");
    }
    void LoadListData(String TextToSearch){
        LoadData Task = new LoadData(TextToSearch);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else { Task.execute(); }
    }
    protected class LoadData extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;
        List<Object[]> args;

        public LoadData(String TextToSearch) {
            List<Object[]> args = new ArrayList<Object[]>();
            if (!TextToSearch.equals("")){
                args.add(new Object[]{ManageDB.ColumnsCategory.CATEGORY_NAME, "like",  "%" + TextToSearch + "%"});
            }
            this.args = args;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(CategoryActivity.this, "", "Cargando Datos");
        }

        @Override
        protected String doInBackground(String... params) {
            CategoryList.clear();
            final List<clsCategory> images = CategoryObj.getRecords(args);
            for(clsCategory im : images){
                CategoryList.add(im);
            }
            adapter = new CategoryItem_1(Context, R.layout.list_item_category_1, CategoryList);
            return "";
        }

        @Override
        protected void onPostExecute(String res) {
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.category, menu);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    LoadListData(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
            search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus){
                        LoadListData();
                    }
                }
            });
        }
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