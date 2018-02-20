package com.example.ramon.proyectofinalramonsolerhernan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.ramon.proyectofinalramonsolerhernan.Config.PhotoManager;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InsertarNoticiaActivity extends AppCompatActivity {
    LoadData loadData;
    Bundle b;
    EditText titulo,contenido;
    Button cargarImagen;
    Toolbar toolbar;
    ImageView image;
    static String nombreimagen="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_noticia);
        titulo=findViewById(R.id.edtxInsertarNoticiaTitulo);
        contenido=findViewById(R.id.edtxInsertarNoticiaContenido);
        cargarImagen=findViewById(R.id.btnCargarImagen);
        toolbar = findViewById(R.id.toolbar4);
        image = findViewById(R.id.imgInsertarNoticia);
        setSupportActionBar(toolbar);
        loadData = new LoadData(InsertarNoticiaActivity.this);
        cargarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(
                        intent, "complete la accion usando..."
                ),1);
            }
        });
        if(getIntent().getExtras()!=null){
            b=getIntent().getExtras();
            //rellenar(b);
        }else{

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aceptar__cancelar,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Aceptar:
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                getBitmapFromImage(image).compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageBytes = stream.toByteArray();
                String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                //loadData.loadphoto(encodedImage);
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);
                PhotoManager photo = new PhotoManager();
                photo.uploadPhoto(encodedImage);
                break;
            case R.id.Cancelar:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }

    public Bitmap getBitmapFromImage(ImageView image){
       Bitmap photobmp = ((BitmapDrawable) image.getDrawable()).getBitmap();
       return photobmp;
    }


}
