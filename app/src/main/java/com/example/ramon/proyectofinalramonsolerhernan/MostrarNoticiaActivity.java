package com.example.ramon.proyectofinalramonsolerhernan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MostrarNoticiaActivity extends AppCompatActivity {

    TextView titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_noticia);
        titulo = findViewById(R.id.txtMostrarTituloNoticia);
        Bundle b = getIntent().getExtras();
    }
}
