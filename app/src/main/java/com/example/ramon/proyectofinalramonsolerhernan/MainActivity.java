package com.example.ramon.proyectofinalramonsolerhernan;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramon.proyectofinalramonsolerhernan.ADAPTERS.AdapterNoticias;
import com.example.ramon.proyectofinalramonsolerhernan.BD.Bd;
import com.example.ramon.proyectofinalramonsolerhernan.Config.Config;
import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Noticias;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    boolean first = true;
    Toolbar toolbar;
    RecyclerView recyclerView;
    SQLiteDatabase database;
    Bd bd;
    BottomNavigationItemView todas, usuario;
    MenuItem itemTodas, itemUsuario;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("RestrictedApi")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    itemTodas = item;
                    selectAllNoticias(item);
                    if(!first){itemUsuario.setEnabled(true);}
                    return true;
                case R.id.navigation_dashboard:
                    first=false;
                    itemUsuario = item;
                    selectUserNoticias(item);
                    itemTodas.setEnabled(true);
                    return true;
            }
            return false;
        }
    };

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todas = findViewById(R.id.navigation_home);
        usuario = findViewById(R.id.navigation_dashboard);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerNoticiasFragment);
        bd = new Bd(this, Config.nombreDB,null,Config.versionDB);
        database = bd.getReadableDatabase();

        navigation.setSelectedItemId(R.id.navigation_home);
    }

    @SuppressLint("RestrictedApi")
    public void selectAllNoticias(MenuItem item){
        ArrayList<Noticias> noticias = new ArrayList<>();
        noticias=bd.getAllNoticias(database);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        AdapterNoticias adapter = new AdapterNoticias(getApplicationContext(), noticias);
        recyclerView.setAdapter(adapter);
        Toast.makeText(this, "Cargando todas las noticias", Toast.LENGTH_SHORT).show();
        item.setEnabled(false);
    }

    @SuppressLint("RestrictedApi")
    public void selectUserNoticias(MenuItem item){
        ArrayList<Noticias> noticias = new ArrayList<>();
        noticias=bd.getNoticiasByUser(database,Config.autor);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        AdapterNoticias adapter = new AdapterNoticias(getApplicationContext(), noticias);
        recyclerView.setAdapter(adapter);
        Toast.makeText(this, "Cargando las noticias del usuario", Toast.LENGTH_SHORT).show();
        item.setEnabled(false);
    }



    @Override
    protected void onResume() {
        super.onResume();
    }
}
