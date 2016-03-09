package com.dubeboard.dubeboard.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dubeboard.dubeboard.ManageDB;
import com.dubeboard.dubeboard.R;
import com.dubeboard.dubeboard.clsImage;
import com.dubeboard.dubeboard.item.adapter.ImageItem_1;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    android.content.Context Context = (Context) this;

    clsImage ImageObj = new clsImage(Context);
    ArrayList<clsImage> ImageList = new ArrayList<clsImage>();
    ImageItem_1 adapter;
    GridView dataList;
    String[] menuItems = new String[]{ "Eliminar" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ImageActivity = new Intent(com.dubeboard.dubeboard.activities.
                        ImageActivity.this, AddImageActivity.class);
                startActivity(ImageActivity);
            }
        });

        // Rescatamos el Action Bar y activamos el boton HomeActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        dataList = (GridView) findViewById(R.id.lvImage);
        dataList.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                final ImageView ivImage = (ImageView) view.findViewById(R.id.ivImage);
                final TextView tvName = (TextView) view.findViewById(R.id.tvName);
                final TextView tvCategory = (TextView) view.findViewById(R.id.tvCategory);
                final ImageButton btnSpeech = (ImageButton) view.findViewById(R.id.btnSpeech);

                tvName.setText(null);
                tvName.setVisibility(View.GONE);

                tvCategory.setText(null);
                tvCategory.setVisibility(View.GONE);

                btnSpeech.setVisibility(View.GONE);

                ivImage.setImageBitmap(null);
                ivImage.setImageDrawable(null);
                ivImage.setScaleType(ImageView.ScaleType.CENTER);
                ivImage.setImageDrawable(Context.getResources().getDrawable(R.drawable.loading_24x24));
            }
        });
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OpenImage(adapter.getItem(position));
            }
        });
        LoadListData();
        registerForContextMenu(dataList);
    }

    public void OpenImage(clsImage ImageToOpen){
        Intent ImageActivity = new Intent(Context, AddImageActivity.class);
        ImageActivity.putExtra("current_id", ImageToOpen.get_id() + "");
        startActivity(ImageActivity);
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
                args.add(new Object[]{ManageDB.ColumnsImage.IMAGE_NAME, "like",  "%" + TextToSearch + "%"});
            }
            this.args = args;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(ImageActivity.this, "", "Cargando Datos");
        }

        @Override
        protected String doInBackground(String... params) {
            ImageList.clear();
            final List<clsImage> images = ImageObj.getRecords(args);
            for(clsImage im : images){
                ImageList.add(im);
            }
            adapter = new ImageItem_1(Context, R.layout.list_item_image, ImageList);
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
        if (v.getId() == R.id.lvImage) {
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
                clsImage RowtoDelete = adapter.getItem(info.position);
                ImageObj.Delete(RowtoDelete.get_id());
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
        inflater.inflate(R.menu.image, menu);

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

    // History
    /*
    private List<String> items = new ArrayList<>();
    private void loadHistory(String query) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Cursor
            String[] columns = new String[] { "_id", "text" };
            Object[] temp = new Object[] { 0, "default" };

            MatrixCursor cursor = new MatrixCursor(columns);
            for(int i = 0; i < items.size(); i++) {

                temp[0] = i;
                temp[1] = items.get(i);//replaced s with i as s not used anywhere.
                        cursor.addRow(temp);
            }

            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            final SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
            search.setSuggestionsAdapter(new ExampleAdapter(this, cursor, items));
        }
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.btnSpeech:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
