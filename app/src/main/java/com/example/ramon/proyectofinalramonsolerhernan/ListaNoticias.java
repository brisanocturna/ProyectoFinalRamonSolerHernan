package com.example.ramon.proyectofinalramonsolerhernan;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.ramon.proyectofinalramonsolerhernan.ADAPTERS.AdapterNoticias;
import com.example.ramon.proyectofinalramonsolerhernan.BD.Bd;
import com.example.ramon.proyectofinalramonsolerhernan.Config.Config;
import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Autores;
import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Comentarios;
import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Noticias;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListaNoticias extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    SQLiteDatabase database;
    Bd bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_noticias);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerNoticiasFragment);
        bd = new Bd(this,Config.nombreDB,null,Config.versionDB);
        database = bd.getReadableDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Noticias> noticias = new ArrayList<>();
        noticias=bd.getAllNoticias(database);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        AdapterNoticias adapter = new AdapterNoticias(this, noticias);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menunoticias,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}
