package com.example.ramon.proyectofinalramonsolerhernan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramon.proyectofinalramonsolerhernan.ADAPTERS.AdapterComentarios;
import com.example.ramon.proyectofinalramonsolerhernan.ADAPTERS.AdapterNoticias;
import com.example.ramon.proyectofinalramonsolerhernan.BD.Bd;
import com.example.ramon.proyectofinalramonsolerhernan.Config.Config;
import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Comentarios;
import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Noticias;

import java.util.ArrayList;

public class ListaComentariosActivity extends AppCompatActivity {

    private TextView mTextMessage;
    static AdapterComentarios adapter;
    boolean first = true;
    Toolbar toolbar;
    RecyclerView recyclerView;
    SQLiteDatabase database;
    Bd bd;
    BottomNavigationItemView todas, usuario;
    MenuItem itemTodas, itemUsuario;
    BottomNavigationView navigation;
    Bundle b;
    LoadData loadData;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("RestrictedApi")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    itemTodas = item;
                    selectAllComentarios(item);
                    if(!first){itemUsuario.setEnabled(true);}
                    return true;
                case R.id.navigation_dashboard:
                    first=false;
                    itemUsuario = item;
                    selectUserComentarios(item);
                    itemTodas.setEnabled(true);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_comentarios);
        todas = findViewById(R.id.navigation_home);
        usuario = findViewById(R.id.navigation_dashboard);
        mTextMessage = (TextView) findViewById(R.id.message);
        toolbar = findViewById(R.id.toolbar3);
        recyclerView = findViewById(R.id.recyclerListaComentarios);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        setSupportActionBar(toolbar);
        bd = new Bd(this, Config.nombreDB,null,Config.versionDB);
        database = bd.getReadableDatabase();
        b = getIntent().getExtras();
        loadData = new LoadData(this);

    }

    public void selectUserComentarios(MenuItem item){
        ArrayList<Comentarios> comentarios = bd.getAllComentarios(database,getIntent().getExtras().getLong("ID"));
        ArrayList<Comentarios> comentariosUser = new ArrayList<>();
        for (Comentarios c:comentarios) {
            if(c.getIdAutor()==Config.autor.getId()){
                comentariosUser.add(c);
            }
        }
            GridLayoutManager gridLayoutManager= new GridLayoutManager(getApplicationContext(), 1);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new AdapterComentarios(getApplicationContext(), comentariosUser);
            recyclerView.setAdapter(adapter);
        Toast.makeText(this, "Cargando los comentarios del usuario", Toast.LENGTH_SHORT).show();
        item.setEnabled(false);
    }

    public void selectAllComentarios(MenuItem item){
        ArrayList<Comentarios> comentarios = bd.getAllComentarios(database,getIntent().getExtras().getLong("ID"));
        GridLayoutManager gridLayoutManager= new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new AdapterComentarios(getApplicationContext(), comentarios);
        recyclerView.setAdapter(adapter);
        Toast.makeText(this, "Cargando todos los comentarios", Toast.LENGTH_SHORT).show();
        item.setEnabled(false);
    }

    @Override
    protected void onResume() {
        loadData.load();
        super.onResume();
        if(itemUsuario!=null){
            itemUsuario.setEnabled(true);
        }
        if(itemTodas!=null){
            itemTodas.setEnabled(true);
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menunoticias,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuAddNoticia:
                Intent intent = new Intent(ListaComentariosActivity.this,InsertarComentarioActivity.class);
                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
                break;
        }
        return true;
    }
}
