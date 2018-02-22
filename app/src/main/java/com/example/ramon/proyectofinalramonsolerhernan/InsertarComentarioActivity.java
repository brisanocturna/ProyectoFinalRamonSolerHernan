package com.example.ramon.proyectofinalramonsolerhernan;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.ramon.proyectofinalramonsolerhernan.Config.Config;
import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Comentarios;
import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Noticias;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class InsertarComentarioActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText titulo, contenido;
    LoadData loadData;
    Bundle b;
    Boolean editmode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_comentario);
        toolbar = findViewById(R.id.ToolbarInsertarComentario);
        titulo = findViewById(R.id.edtxInsertarComentarioTitulo);
        contenido = findViewById(R.id.edtxInsertarComentarioContenido);
        loadData = new LoadData(this);
        setSupportActionBar(toolbar);

        if (getIntent().getExtras().getParcelable("COMENTARIO") != null) {
            b = getIntent().getExtras();
            editmode = true;
            Toast.makeText(this, "Ha entrado en modo edicion", Toast.LENGTH_SHORT).show();
            rellenar(b);
        }
    }
    public void rellenar(Bundle b){
        Comentarios c = b.getParcelable("COMENTARIO");
        titulo.setText(c.getTitulo().toString());
        contenido.setText(c.getTitulo().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aceptar__cancelar,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Aceptar:
                if(!titulo.getText().toString().equals("") && !contenido.getText().toString().equals("")){
                    Comentarios c = new Comentarios(contenido.getText().toString(),new Date(),new Date(),
                            Config.autor.getId(),getIntent().getExtras().getLong("ID"),
                            titulo.getText().toString());
                    Log.d("Comentarios NoticiID",""+getIntent().getExtras().getLong("ID"));
                    JSONObject Jcomentario = new JSONObject();
                    try {
                        Jcomentario.put("titulo", c.getTitulo());
                        Jcomentario.put("contenido",c.getContenido());
                        Jcomentario.put("fechaCreacion",c.getFechaCreacion());
                        Jcomentario.put("fechaUpdate",c.getFechaUpdate());
                        Jcomentario.put("idAutor",c.getIdAutor());
                        Jcomentario.put("idNoticia",c.getIdNoticia());
                        if(editmode){
                            Comentarios comentario =getIntent().getExtras().getParcelable("COMENTARIO");
                            Jcomentario.put("id",comentario.getId());
                            Jcomentario.put("idNoticia",comentario.getIdNoticia());
                        }

                        String Jstring = Jcomentario.toString();
                        if(editmode){
                            loadData.updateC(Jstring);
                            Toast.makeText(this, "Llamando a actulizar", Toast.LENGTH_SHORT).show();
                        }else{
                            loadData.loadComentario(Jstring);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(this, "Rellene todos los datos porfavor", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.Cancelar:
                break;
        }
        return true;
    }

}
