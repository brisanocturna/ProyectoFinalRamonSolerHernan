package com.example.ramon.proyectofinalramonsolerhernan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Noticias;

public class MostrarNoticiaActivity extends AppCompatActivity {
    public Toolbar toolbar;
    TextView titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_noticia);
        titulo = findViewById(R.id.txtMostrarTituloNoticia);
        toolbar = findViewById(R.id.toolbarNoticiasMostrar);
        setSupportActionBar(toolbar);
        Bundle b = getIntent().getExtras();
        Noticias n = b.getParcelable("NOTICIA");
        titulo.setText(n.getTitulo().toString());
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
