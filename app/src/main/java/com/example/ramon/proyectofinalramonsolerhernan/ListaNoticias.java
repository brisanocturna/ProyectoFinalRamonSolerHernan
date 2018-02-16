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
        LoadData();
        ArrayList<Noticias> noticias = new ArrayList<>();
        noticias=bd.getAllNoticias(database);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        AdapterNoticias adapter = new AdapterNoticias(this, noticias);
        recyclerView.setAdapter(adapter);
    }

    public void LoadData(){
        new LoadAutores().execute(Config.tablaAutores);
        new LoadNoticias().execute(Config.tablaNoticias);
        new LoadComentarios().execute(Config.tablaComentarios);
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

    public class LoadAutores extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setConnectTimeout(2000);
                connection.setDoInput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("GET");

                int responsecode = connection.getResponseCode();

                if(responsecode==200){
                    connection.getContent();
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    BufferedReader streamReader = new BufferedReader(
                            new InputStreamReader(in, "UTF-8"));
                    StringBuilder responseString = new StringBuilder();
                    String temp;
                    while((temp=streamReader.readLine())!=null){
                        responseString.append(temp);
                    }
                    String json = responseString.toString();
                    return json;
                }else{
                    return null;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // TODO: register the new account here.
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null){
                Gson gson = new Gson();
                Bd bd = new Bd(ListaNoticias.this,Config.nombreDB,null, Config.versionDB);
                SQLiteDatabase instancia = bd.getReadableDatabase();
                ArrayList<Autores> autores = gson.fromJson(s,new TypeToken<ArrayList<Autores>>(){}.getType());
                for (Autores obj :autores) {
                    long affected = bd.updateAutor(obj,instancia);
                    if(affected!=1){
                        bd.insertAutor(obj,instancia);
                    }
                }
            }else{
                Toast.makeText(ListaNoticias.this, "Fallo al cargar tabla Autores porfavor cierre la aplicacion y vuelva a intentarlo en unos minutos", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {

        }
    }

    public class LoadNoticias extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setConnectTimeout(2000);
                connection.setDoInput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("GET");

                int responsecode = connection.getResponseCode();

                if(responsecode==200){
                    connection.getContent();
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    BufferedReader streamReader = new BufferedReader(
                            new InputStreamReader(in, "UTF-8"));
                    StringBuilder responseString = new StringBuilder();
                    String temp;
                    while((temp=streamReader.readLine())!=null){
                        responseString.append(temp);
                    }
                    String json = responseString.toString();
                    return json;
                }else{
                    return null;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // TODO: register the new account here.
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null){
                Gson gson = new Gson();
                Bd bd = new Bd(ListaNoticias.this,Config.nombreDB,null, Config.versionDB);
                SQLiteDatabase instancia = bd.getReadableDatabase();
                //ArrayList<Noticias> noticias = gson.fromJson(s,new TypeToken<ArrayList<Noticias>>(){}.getType());
                ArrayList<Noticias> noticias= new ArrayList<>();
                try {
                    JSONArray array = new JSONArray(s);
                    for (int i = 0; i <array.length() ; i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Noticias n = new Noticias(obj.getLong("id"),obj.getString("titulo"),
                                obj.getString("contenido"),parseFecha(obj.getString("fechaCreacion")),
                                parseFecha(obj.getString("fechaUpdate")),obj.getString("imagen"),obj.getLong("idAutor"));
                        noticias.add(n);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (Noticias obj : noticias) {
                    long affected = bd.updateNoticias(obj,instancia);
                    if(affected!=1){
                        bd.insertNoticias(obj,instancia);
                    }
                }
            }else{
                Toast.makeText(ListaNoticias.this, "Fallo al cargar tabla Noticias porfavor cierre la aplicacion y vuelva a intentarlo en unos minutos", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {

        }
    }

    public Date parseFecha(String s){
        Date resultado= null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        try {
            resultado=sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public class LoadComentarios extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setConnectTimeout(2000);
                connection.setDoInput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("GET");

                int responsecode = connection.getResponseCode();

                if(responsecode==200){
                    connection.getContent();
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    BufferedReader streamReader = new BufferedReader(
                            new InputStreamReader(in, "UTF-8"));
                    StringBuilder responseString = new StringBuilder();
                    String temp;
                    while((temp=streamReader.readLine())!=null){
                        responseString.append(temp);
                    }
                    String json = responseString.toString();
                    return json;
                }else{
                    return null;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // TODO: register the new account here.
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null){
                Gson gson = new Gson();
                Bd bd = new Bd(ListaNoticias.this,Config.nombreDB,null, Config.versionDB);
                SQLiteDatabase instancia = bd.getReadableDatabase();
                //ArrayList<Comentarios> comentarios = gson.fromJson(s,new TypeToken<ArrayList<Comentarios>>(){}.getType());ç
                ArrayList<Comentarios> comentarios= new ArrayList<>();
                try {
                    JSONArray array = new JSONArray(s);
                    for (int i = 0; i <array.length() ; i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Comentarios c = new Comentarios(obj.getLong("id"),obj.getString("contenido"),
                                parseFecha(obj.getString("fechaCreacion")), parseFecha(obj.getString("fechaUpdate")),
                                obj.getLong("idAutor"),obj.getLong("idNoticia"),obj.getString("titulo"));
                        comentarios.add(c);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (Comentarios obj : comentarios) {
                    long affected = bd.updateComentario(obj,instancia);
                    if(affected!=1){
                        bd.insertComentario(obj,instancia);
                    }
                }
            }else{
                Toast.makeText(ListaNoticias.this, "Fallo al cargar tabla Comentarios porfavor cierre la aplicacion y vuelva a intentarlo en unos minutos", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}