package com.example.ramon.proyectofinalramonsolerhernan;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.ramon.proyectofinalramonsolerhernan.BD.Bd;
import com.example.ramon.proyectofinalramonsolerhernan.Config.Config;
import com.example.ramon.proyectofinalramonsolerhernan.Config.PhotoManager;
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
import java.util.concurrent.ExecutionException;

import android.content.Context;

/**
 * Created by Brisanocturna on 17/02/2018.
 */

public class LoadData {
    private Context context;
    public LoadData(Context context){
        this.context = context;
    }

    public void load(){
        new LoadAutores().execute(Config.tablaAutores);
        new LoadNoticias().execute(Config.tablaNoticias);
        new LoadComentarios().execute(Config.tablaComentarios);
    }

    public void loadphoto(String s){
        new UploadPhoto(this.context).execute(s);
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
                Bd bd = new Bd(context,Config.nombreDB,null, Config.versionDB);
                SQLiteDatabase instancia = bd.getReadableDatabase();
                ArrayList<Autores> autores = gson.fromJson(s,new TypeToken<ArrayList<Autores>>(){}.getType());
                for (Autores obj :autores) {
                    long affected = bd.updateAutor(obj,instancia);
                    if(affected!=1){
                        bd.insertAutor(obj,instancia);
                    }
                }
            }else{
                Toast.makeText(context, "Fallo al cargar tabla Autores porfavor cierre la aplicacion y vuelva a intentarlo en unos minutos", Toast.LENGTH_SHORT).show();
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
                Bd bd = new Bd(context,Config.nombreDB,null, Config.versionDB);
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
                Toast.makeText(context, "Fallo al cargar tabla Noticias porfavor cierre la aplicacion y vuelva a intentarlo en unos minutos", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {

        }
    }

    public Date parseFecha(String s){
        Date resultado= null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                Bd bd = new Bd(context,Config.nombreDB,null, Config.versionDB);
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
                Toast.makeText(context, "Fallo al cargar tabla Comentarios porfavor cierre la aplicacion y vuelva a intentarlo en unos minutos", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {

        }
    }

    public class UploadPhoto extends AsyncTask<String,Void,Boolean> {

        private ProgressDialog progressDialog;
        AlertDialog.Builder builder;
        Context context;
        /**
         * Constructor de clase
         */
        public UploadPhoto(Context context) {
            this.context=context;
            builder = new AlertDialog.Builder(context);
        }

        /**
         * Antes de comenzar la tarea muestra el progressDialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           //InsertarNoticiaActivity.progressDialog.show();
        }

        /**
         * @param
         */
        @Override
        protected Boolean doInBackground(String... params) {
            Boolean r = false;
            PhotoManager photoManager = new PhotoManager();
            r = photoManager.uploadPhoto(params[0]);
            return r;
        }

        /**
         * Cuando se termina de ejecutar, cierra el progressDialog y avisa
         **/
        @Override
        protected void onPostExecute(Boolean resul) {
            progressDialog.dismiss();
            if (resul) {
                builder.setMessage("Imagen subida al servidor")
                        .setTitle("From Server")
                        .setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();
            } else {
                builder.setMessage("No se pudo subir la imagen")
                        .setTitle("From Server")
                        .setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();
            }
        }
    }
}
