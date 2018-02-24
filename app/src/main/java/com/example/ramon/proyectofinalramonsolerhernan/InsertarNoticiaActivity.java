package com.example.ramon.proyectofinalramonsolerhernan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

import com.example.ramon.proyectofinalramonsolerhernan.BD.Bd;
import com.example.ramon.proyectofinalramonsolerhernan.Config.Config;
import com.example.ramon.proyectofinalramonsolerhernan.Config.PhotoManager;
import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Noticias;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

public class InsertarNoticiaActivity extends AppCompatActivity {
    LoadData loadData;
    Bundle b;
    EditText titulo,contenido;
    Button cargarImagen;
    Toolbar toolbar;
    ImageView image;
    Boolean imagenseleccionada= false;
    Boolean editmode = false;
    String nombreimagen="";
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
            editmode=true;
            Toast.makeText(this, "Ha entrado en modo edicion", Toast.LENGTH_SHORT).show();
            rellenar(b);
        }
    }

    public void rellenar(Bundle b){
        Noticias n = b.getParcelable("NOTICIA");
        nombreimagen = n.getImagen();
        titulo.setText(n.getTitulo().toString());
        contenido.setText(n.getTitulo().toString());
        Picasso.with(this).load(Config.imagePath+n.getImagen()).resize(200,200).centerCrop().into(image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aceptar__cancelar,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Aceptar:
                if(imagenseleccionada){
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    getBitmapFromImage(image).compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imageBytes = stream.toByteArray();
                    String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    nombreimagen=System.currentTimeMillis()+".jpg";
                    loadData.loadphoto(encodedImage, nombreimagen);
                }
                if(!nombreimagen.equals("")&& !titulo.getText().toString().equals("") && !contenido.getText().toString().equals("")){
                    Noticias n = new Noticias(titulo.getText().toString(),contenido.getText().toString(),
                            new Date(),new Date(),nombreimagen, Config.autor.getId());
                    JSONObject Jnoticia = new JSONObject();
                    try {

                        if(editmode){
                            Noticias noticia =getIntent().getExtras().getParcelable("NOTICIA");
                            Jnoticia.put("id",noticia.getId());
                        }
                        Jnoticia.put("titulo", n.getTitulo());
                        Jnoticia.put("contenido",n.getContenido());
                        Jnoticia.put("fechaCreacion",n.getFechaCreacion());
                        Jnoticia.put("fechaUpdate",n.getFechaUpdate());
                        Jnoticia.put("imagen",n.getImagen());
                        Jnoticia.put("idAutor",n.getIdAutor());
                        String Jstring = Jnoticia.toString();
                        if(editmode){
                            loadData.updateN(Jstring);
                            Toast.makeText(this, "Llamando a actulizar", Toast.LENGTH_SHORT).show();
                        }else{
                            loadData.loadNoticia(Jstring);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(this, "Rellene todos los datos y asegurese de haber seleccionado una imagen", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.Cancelar:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            image.setImageURI(imageUri);
            imagenseleccionada=true;
        }else{
            imagenseleccionada=false;
        }
    }

    public Bitmap getBitmapFromImage(ImageView image){
       Bitmap photobmp = ((BitmapDrawable) image.getDrawable()).getBitmap();
       return photobmp;
    }


}
