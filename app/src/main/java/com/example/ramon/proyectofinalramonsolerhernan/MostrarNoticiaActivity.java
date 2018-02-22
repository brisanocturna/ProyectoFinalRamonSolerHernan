package com.example.ramon.proyectofinalramonsolerhernan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.ramon.proyectofinalramonsolerhernan.Config.Config;
import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Noticias;
import com.squareup.picasso.Picasso;

public class MostrarNoticiaActivity extends AppCompatActivity {
    public Toolbar toolbar;
    TextView titulo, contenido;
    Button comentarios;
    ImageView imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_noticia);
        titulo = findViewById(R.id.txtMostrarTituloNoticia);
        contenido = findViewById(R.id.txtMostrarContenidoNoticias);
        toolbar = findViewById(R.id.toolbarNoticiasMostrar);
        imagen = findViewById(R.id.imgMostrarNoticia);
        setSupportActionBar(toolbar);
        Bundle b = getIntent().getExtras();
        final Noticias n = b.getParcelable("NOTICIA");
        titulo.setText(n.getTitulo().toString());
        contenido.setText(n.getContenido().toString());
        comentarios = findViewById(R.id.btnLeerComentarios);
        Picasso.with(this).load(Config.imagePath+n.getImagen()).resize(200,200).centerCrop().into(imagen);
        comentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MostrarNoticiaActivity.this,ListaComentariosActivity.class);
                Bundle b = new Bundle();
                b.putLong("ID",n.getId());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mostrar_noticia,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
