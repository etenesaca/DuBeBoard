package com.dubeboard.dubeboard.activities;

import android.content.Context;
import android.content.Intent;
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
    String[] menuItems = new String[]{"Editar", "Eliminar"};

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

        // Obtener todas las imagenes de la base de datos
        final List<clsImage> images = ImageObj.getAll();
        for(clsImage im : images){
            ImageList.add(im);
        }

        dataList.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                final ImageView ivImage = (ImageView) view.findViewById(R.id.ivImage);
                final TextView tvName = (TextView) view.findViewById(R.id.tvName);
                final TextView tvCategory = (TextView) view.findViewById(R.id.tvCategory);

                tvName.setText(null);
                tvCategory.setText(null);
                ivImage.setImageBitmap(null);
                ivImage.setImageDrawable(Context.getResources().getDrawable(R.drawable.img_def_48x48));
            }
        });

        adapter = new ImageItem_1(this, R.layout.list_item_image_1, ImageList);
        dataList.setAdapter(adapter);
        registerForContextMenu(dataList);
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
