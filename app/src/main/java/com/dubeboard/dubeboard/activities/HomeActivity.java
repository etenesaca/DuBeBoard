package com.dubeboard.dubeboard.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dubeboard.dubeboard.Config;
import com.dubeboard.dubeboard.R;
import com.dubeboard.dubeboard.clsCategory;
import com.dubeboard.dubeboard.clsImage;
import com.dubeboard.dubeboard.item.adapter.CategoryItem_home;
import com.dubeboard.dubeboard.item.adapter.ImageItem_home;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Context Context = (Context) this;
    Config Configuration = new Config(Context);

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    GridView gvImage;
    ListView lvCategory;
    TextView tvWords;
    ImageButton btnClear;
    ImageButton btnSpeech;
    TextToSpeech tts;

    clsCategory CategoryObj = new clsCategory(Context);
    ArrayList<clsCategory> CategoryList = new ArrayList<clsCategory>();
    CategoryItem_home adapterCategory;

    clsImage ImageObj = new clsImage(Context);
    ArrayList<clsImage> ImageList = new ArrayList<clsImage>();
    ImageItem_home adapterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        lvCategory = (ListView) findViewById(R.id.lvCategory);
        gvImage = (GridView) findViewById(R.id.gvImage);
        tvWords = (TextView) findViewById(R.id.tvWords);
        btnClear = (ImageButton) findViewById(R.id.btnClear);
        btnSpeech = (ImageButton) findViewById(R.id.btnSpeech);

        tvWords.setTextSize(Configuration.getTextSize());

        lvCategory.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                final ImageView imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
                final TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);

                txtTitle.setText(null);
                txtTitle.setVisibility(View.GONE);
                imgIcon.setImageBitmap(null);
                imgIcon.setScaleType(ImageView.ScaleType.CENTER);
                imgIcon.setImageDrawable(Context.getResources().getDrawable(R.drawable.loading_24x24));
            }
        });
        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LoadListDataImage(adapterCategory.getItem(position));
                for (int a = 0; a < parent.getChildCount(); a++) {
                    parent.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
                }
                view.setBackgroundColor(Color.GREEN);
            }
        });

        gvImage.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                final ImageView imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
                final TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);

                txtTitle.setText(null);
                txtTitle.setVisibility(View.GONE);
                imgIcon.setImageBitmap(null);
                imgIcon.setScaleType(ImageView.ScaleType.CENTER);
                imgIcon.setImageDrawable(Context.getResources().getDrawable(R.drawable.loading_24x24));

                //view.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        gvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clsImage ImageSelected = adapterImage.getItem(position);
                tvWords.setText(tvWords.getText() + " " + ImageSelected.get_name());
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvWords.setText("");
            }
        });
        btnSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(tvWords.getText() + "", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        tts = new TextToSpeech(Context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Configuration.getLangLocale());
                }
            }
        });

        LoadListDataCategory();
    }

    void LoadListDataCategory(){
        LoadDataCategory Task = new LoadDataCategory();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else { Task.execute(); }
    }
    protected class LoadDataCategory extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(HomeActivity.this, "", "Cargando Datos");
        }

        @Override
        protected String doInBackground(String... params) {
            CategoryList.clear();
            for(clsCategory x : CategoryObj.getAll()){
                CategoryList.add(x);
            }
            adapterCategory = new CategoryItem_home(Context, R.layout.list_item_simple, CategoryList);
            return "";
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            lvCategory.setAdapter(adapterCategory);
            if (CategoryList.size() > 0) {
                LoadListDataImage(CategoryList.get(0));
            }
            pDialog.dismiss();
        }
    }

    void LoadListDataImage(clsCategory SelectedCategory){
        LoadDataImage Task = new LoadDataImage(SelectedCategory);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else { Task.execute(); }
    }
    protected class LoadDataImage extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;
        clsCategory SelectedCategory;

        public LoadDataImage(clsCategory SelectedCategory) {
            this.SelectedCategory = SelectedCategory;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(HomeActivity.this, "", "Cargando Datos");
        }

        @Override
        protected String doInBackground(String... params) {
            ImageList.clear();
            for(clsImage x : SelectedCategory.getChildImages(Context)){
                ImageList.add(x);
            }
            adapterImage = new ImageItem_home(Context, R.layout.list_item_simple, ImageList);
            return "";
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            gvImage.setAdapter(adapterImage);
            pDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_item) {

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_man_category) {
            Intent CategoryActivity = new Intent(HomeActivity.this, com.dubeboard.dubeboard.activities.CategoryActivity.class);
            startActivity(CategoryActivity);
        } else if (id == R.id.nav_man_image) {
            Intent ImageAcivity = new Intent(HomeActivity.this, ImageActivity.class);
            startActivity(ImageAcivity);
        } else if (id == R.id.nav_settings) {
            Intent SettingsActivity = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(SettingsActivity);
        }else if (id == R.id.nav_Exit) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "HomeActivity Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.dubeboard.dubeboard/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "HomeActivity Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.dubeboard.dubeboard/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
