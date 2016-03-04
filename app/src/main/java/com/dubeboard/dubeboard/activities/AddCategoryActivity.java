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
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dubeboard.dubeboard.ManageDB;
import com.dubeboard.dubeboard.R;
import com.dubeboard.dubeboard.clsCategory;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.dubeboard.dubeboard.*;
import com.dubeboard.dubeboard.item.adapter.ImageItem_3;


public class AddCategoryActivity extends AppCompatActivity {
    Context Context = (Context) this;
    clsCategory SelectedRecord;

    String imgDecodableString;
    ArrayList<clsImage> ImageList = new ArrayList<clsImage>();
    ImageItem_3 adapterImage;

    Typeface Roboto_light;
    Typeface Roboto_bold;

    private String APP_DIRECTORY = "myPictureApp/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;

    clsCategory CategoryObj = new clsCategory(Context);
    clsImage ImageObj = new clsImage(Context);
    EditText txtName;
    ImageView ivImage;
    TextView lblName;
    TextView lblImage;
    TextView lblChildImages;
    ListView lvImages;
    LinearLayout lyChildImages;
    Button btnGallery;
    Button btnCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        // Rescatamos el Action Bar y activamos el boton HomeActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        lblName = (TextView) findViewById(R.id.lblLanguaje);
        lblImage = (TextView) findViewById(R.id.lblImage);
        lblImage = (TextView) findViewById(R.id.lblImage);
        lblChildImages = (TextView) findViewById(R.id.lblChildImages);
        lvImages = (ListView) findViewById(R.id.lvImages);
        lyChildImages = (LinearLayout) findViewById(R.id.lyChildImages);
        txtName = (EditText) findViewById(R.id.tvName);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        btnGallery = (Button) findViewById(R.id.btnGallery);
        btnCamera = (Button) findViewById(R.id.btnCamera);

        // Establecer las fuentes
        Roboto_light = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        Roboto_bold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");

        lblName.setTypeface(Roboto_bold);
        lblImage.setTypeface(Roboto_bold);

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

        // Obtener los parametros que se le pasan
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("current_id")){
            getSupportActionBar().setTitle("Editar");
            SelectedRecord = new clsCategory();
            LoadData Task = new LoadData(Integer.parseInt(bundle.getString("current_id") + ""));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                Task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else { Task.execute(); }
            btnGallery.requestFocus();
        }
        else{
            SelectedRecord = null;
            getSupportActionBar().setTitle("Agregar Categoría");
            lyChildImages.setVisibility(View.GONE);
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
            SelectedRecord = new clsCategory(Context, RecordID);
            HashMap<String, Object> res = new HashMap<String, Object>();
            // Obtener la imagen
            res.put("bmp", gl.build_image(Context, SelectedRecord.get_image()));
            // Recuperar Imagenes reacionadas
            for(clsImage im : SelectedRecord.getChildImages(Context)){
                ImageList.add(im);
            }
            adapterImage = new ImageItem_3(Context, R.layout.list_item_image_3, ImageList);
            return res;
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> res) {
            super.onPostExecute(res);

            txtName.setText(SelectedRecord.get_name());
            ivImage.setImageBitmap((Bitmap) res.get("bmp"));
            lvImages.setAdapter(adapterImage);
            ListViewDinamicSize.getListViewSize(lvImages);

            lyChildImages.setVisibility(View.VISIBLE);
            lblChildImages.setTypeface(Roboto_bold);
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
        final Intent CategoryActivity = new Intent(AddCategoryActivity.this, com.dubeboard.dubeboard.activities.CategoryActivity.class);
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
                } else if (CategoryObj.getRecords(new Object[] {ManageDB.ColumnsCategory.CATEGORY_NAME, "=", NewCategory.get_name()}).size() > 0){
                    Snackbar.make(findViewById(android.R.id.content), "Ya hay una categoria con este nombre", Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    // Redimensionar imagen
                    ivImage.buildDrawingCache();
                    Bitmap bm = ivImage.getDrawingCache();
                    bm = gl.scaleDown(bm, 512, true);
                    NewCategory.set_image(bm);

                    // Crear una categoria
                    CategoryObj.AddRecord(NewCategory);
                    startActivity(CategoryActivity);
                }
                return true;
            case R.id.action_save_edit:
                ContentValues vals = new ContentValues();
                vals.put(ManageDB.ColumnsCategory.CATEGORY_NAME, txtName.getText().toString());

                // Redimensionar imagen
                ivImage.buildDrawingCache();
                Bitmap bmp = ivImage.getDrawingCache();
                bmp = gl.scaleDown(bmp, 512, true);
                vals.put(ManageDB.ColumnsCategory.CATEGORY_IMAGE, gl.BitmaptoByteArray(bmp));

                CategoryObj.Update(SelectedRecord.get_id(), vals);
                startActivity(CategoryActivity);
                finish();
                return true;
            case R.id.action_delete:
                new AlertDialog.Builder(this)
                        .setTitle("Eliminar Categoria")
                        .setMessage("¿Seguro de Eliminar esta categoria?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                CategoryObj.Delete(SelectedRecord.get_id());
                                startActivity(CategoryActivity);
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