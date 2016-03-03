package com.dubeboard.dubeboard.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dubeboard.dubeboard.ManageDB;
import com.dubeboard.dubeboard.R;
import com.dubeboard.dubeboard.clsCategory;
import com.dubeboard.dubeboard.clsImage;
import com.dubeboard.dubeboard.gl;
import com.dubeboard.dubeboard.item.adapter.CategoryItem_1;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddImageActivity extends AppCompatActivity {
    Context Context = (Context) this;
    clsImage SelectedRecord;

    String imgDecodableString;
    TextToSpeech tts;

    private String APP_DIRECTORY = "myPictureApp/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;

    clsImage ImageObj = new clsImage(Context);
    clsCategory CategoryObj = new clsCategory(Context);
    EditText txtName;
    Spinner spCategory;
    ImageView ivImage;
    TextView lblName;
    TextView lblCategory;
    TextView lblImage;
    Button btnGallery;
    Button btnCamera;

    ArrayList<String> CategoryList = new ArrayList<String>();
    HashMap<String,Integer> MapCategory = new HashMap<String,Integer>();
    CategoryItem_1 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Rescatamos el Action Bar y activamos el boton HomeActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        lblName = (TextView) findViewById(R.id.lblName);
        lblCategory = (TextView) findViewById(R.id.lblCategory);
        lblImage = (TextView) findViewById(R.id.lblImage);
        txtName = (EditText) findViewById(R.id.tvName);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        spCategory = (Spinner) findViewById(R.id.spCategory);

        btnGallery = (Button) findViewById(R.id.btnGallery);
        btnCamera = (Button) findViewById(R.id.btnCamera);

        // Establecer las fuentes
        Typeface Roboto_light = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        Typeface Roboto_bold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");

        lblName.setTypeface(Roboto_bold);
        lblImage.setTypeface(Roboto_bold);
        lblCategory.setTypeface(Roboto_bold);

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

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

        // Obtener todas las categorias de la base de datos
        final List<clsCategory> Categories = CategoryObj.getAll();
        for(clsCategory im : Categories){
            CategoryList.add(im.get_name());
            MapCategory.put(im.get_name(), im.get_id());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Context,android.R.layout.simple_spinner_item, CategoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        // Obtener los parametros que se le pasan
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("current_id")){
            getSupportActionBar().setTitle("Editar");
            SelectedRecord = new clsImage();
            LoadData Task = new LoadData(Integer.parseInt(bundle.getString("current_id") + ""));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                Task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else { Task.execute(); }
            btnGallery.requestFocus();
        }
        else{
            SelectedRecord = null;
            getSupportActionBar().setTitle("Agregar Imagen");
        }
    }

    /** Clase Asincrona para recuperar los datos del registro seleccionado **/
    protected class LoadData extends AsyncTask<String, Void, HashMap<String, Object>> {
        int RecordID;
        public LoadData(int RecordID) {
            this.RecordID = RecordID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected HashMap<String, Object> doInBackground(String... params) {
            SelectedRecord = new clsImage(Context, RecordID);
            HashMap<String, Object> res = new HashMap<String, Object>();
            // Obtener la imagen
            res.put("bmp", gl.build_image(Context, SelectedRecord.get_image()));
            res.put("category_index", gl.getIndexSpinner(spCategory, SelectedRecord.get_category().get_name()));
            return res;
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> res) {
            super.onPostExecute(res);

            txtName.setText(SelectedRecord.get_name());
            ivImage.setImageBitmap((Bitmap) res.get("bmp"));
            spCategory.setSelection(Integer.parseInt(res.get("category_index") + ""));
        }
    }



    private void openGallery() {
        startActivityForResult(
                Intent.createChooser(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), "Seleccione la imagen"), SELECT_PICTURE
        );

                            /*
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            intent.putExtra("crop", "true");
                            intent.putExtra("aspectX", 0);
                            intent.putExtra("aspectY", 0);
                            intent.putExtra("outputX", 200);
                            intent.putExtra("outputY", 150);
                            intent.putExtra("return-data", true);
                            startActivityForResult(
                                    intent.createChooser(intent, "Seleccione la imagen"), SELECT_PICTURE
                            );
                            */
    }
    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        file.mkdirs();

        String path = Environment.getExternalStorageDirectory() + File.separator
                + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;

        File newFile = new File(path);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
        startActivityForResult(intent, PHOTO_CODE);
    }

    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    private String selectedImagePath;
    private Bitmap NewBitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case PHOTO_CODE:
                    if(resultCode == RESULT_OK){
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                            selectedImagePath =  Environment.getExternalStorageDirectory() + File.separator
                                    + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
                            Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                            ivImage.setImageBitmap(bitmap);
                        } else {

                        }
                    }
                    break;
                case SELECT_PICTURE:
                    Uri selectedImageUri = data.getData();
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                        selectedImagePath = getPath(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                        /*
                        Bitmap bitmap = data.getExtras().getParcelable("data");
                        // convert bitmap to byte
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        */
                        ivImage.setImageBitmap(bitmap);
                    } else {
                        ParcelFileDescriptor parcelFileDescriptor;
                        try {
                            parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImageUri, "r");
                            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                            parcelFileDescriptor.close();
                            ivImage.setImageBitmap(image);
                        }
                        catch (FileNotFoundException e) { e.printStackTrace(); }
                        catch (IOException e) { e.printStackTrace(); }
                    }
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Intent ImageActivity = new Intent(AddImageActivity.this, ImageActivity.class);
        String CategorySelected;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save:
                // Obtener la categoria sleccionada
                CategorySelected = spCategory.getSelectedItem() + "";

                clsImage NewImage = new clsImage();
                NewImage.set_name(txtName.getText().toString());

                // Verificar si la Imagen no ya no esta creda
                if (NewImage.get_name().equals("")) {
                    Snackbar.make(findViewById(android.R.id.content), "Primero Ingrese un nombre", Snackbar.LENGTH_LONG)
                            .show();
                } else if (CategorySelected.equals("null")) {
                    Snackbar.make(findViewById(android.R.id.content), "Primero seleccione la Categoria", Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    // Obtener la categoria
                    int Category_ID = MapCategory.get(CategorySelected);
                    NewImage.set_category(new clsCategory(Context, Category_ID));

                    // Verificar que no hay un registro con el mismo nombre y la misma categoria
                    List<Object[]> args = new ArrayList<Object[]>();
                    args.add(new Object[] {ManageDB.ColumnsImage.IMAGE_NAME, "=", NewImage.get_name()});
                    args.add(new Object[]{ManageDB.ColumnsImage.IMAGE_CATEGORY_ID, "=", NewImage.get_category().get_id()});
                    if (ImageObj.getRecords(args).size() > 0){
                        Snackbar.make(findViewById(android.R.id.content), "Ya hay una Imagen con este nombre para esta Categoria", Snackbar.LENGTH_LONG)
                                .show();
                    }else{
                        // Redimensionar imagen
                        ivImage.buildDrawingCache();
                        Bitmap bm = ivImage.getDrawingCache();
                        bm = gl.scaleDown(bm, 512, true);
                        NewImage.set_image(bm);

                        // Crear una Imagen
                        ImageObj.AddRecord(NewImage);
                        startActivity(ImageActivity);
                        return true;
                    }
                }
            case R.id.action_save_edit:
                ContentValues vals = new ContentValues();
                vals.put(ManageDB.ColumnsImage.IMAGE_NAME, txtName.getText().toString());

                // Obtener la categoria
                CategorySelected = spCategory.getSelectedItem() + "";
                vals.put(ManageDB.ColumnsImage.IMAGE_CATEGORY_ID, MapCategory.get(CategorySelected));

                // Redimensionar imagen
                ivImage.buildDrawingCache();
                Bitmap bmp = ivImage.getDrawingCache();
                bmp = gl.scaleDown(bmp, 512, true);
                vals.put(ManageDB.ColumnsImage.IMAGE_IMAGE, gl.BitmaptoByteArray(bmp));

                ImageObj.Update(SelectedRecord.get_id(), vals);
                startActivity(ImageActivity);
                finish();
                return true;
            case R.id.action_delete:
                new AlertDialog.Builder(this)
                        .setTitle("Eliminar Categoria")
                        .setMessage("¿Seguro de Eliminar esta categoria?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ImageObj.Delete(SelectedRecord.get_id());
                                startActivity(ImageActivity);
                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (SelectedRecord == null){
            inflater.inflate(R.menu.menu_add, menu);
        } else {
            inflater.inflate(R.menu.menu_edit, menu);
        }
        return true;
    }
}