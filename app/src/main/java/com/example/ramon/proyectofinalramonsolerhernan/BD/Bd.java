package com.example.ramon.proyectofinalramonsolerhernan.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Autores;
import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Comentarios;
import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Noticias;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ramon on 12/02/2018.
 */

public class Bd extends SQLiteOpenHelper{

    final String createTableUsuarios = "CREATE TABLE autores (" +
            "'id'INTEGER NOT NULL PRIMARY KEY," +
            "'nombre'TEXT NOT NULL," +
            "'apellidos'TEXT NOT NULL,"+
            "'nick'TEXT NOT NULL,"+
            "'password'TEXT NOT NULL,"+
            "'email'TEXT NOT NULL"+
            ")";
    final String createTableNoticias = "CREATE TABLE noticias (" +
            "'id'INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "'titulo'TEXT NOT NULL," +
            "'contenido'TEXT NOT NULL,"+
            "'fechaCreacion'INTEGER NOT NULL,"+
            "'fechaUpdate'INTEGER NOT NULL,"+
            "'imagen'TEXT NOT NULL,"+
            "'idAutor'INTEGER NOT NULL"+
            ")";
    final String createTableComentarios = "CREATE TABLE comentarios (" +
            "'id'INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "'contenido'TEXT NOT NULL," +
            "'fechaCreacion'INTEGER NOT NULL,"+
            "'fechaUpdate'INTEGER NOT NULL,"+
            "'idAutor'INTEGER NOT NULL,"+
            "'idNoticia'INTEGER NOT NULL,"+
            "'titulo'TEXT NOT NULL"+
            ")";

    public Bd(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableUsuarios);
        db.execSQL(createTableNoticias);
        db.execSQL(createTableComentarios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    ////////////////////////////////////////////////77 AUTORES ///////////////////////////////////77

    public Autores getAutor(SQLiteDatabase db, long id){
        Autores autor=null;

        Cursor cursorAutores = db.rawQuery("select id, nombre, apellidos, nick, password, email" +
                " from autores where id="+id,null);
        if(cursorAutores.moveToFirst()){
            do{
                autor = new Autores(cursorAutores.getInt(0),
                        cursorAutores.getString(1),cursorAutores.getString(2),
                        cursorAutores.getString(3),cursorAutores.getString(4),
                        cursorAutores.getString(5));
            }while(cursorAutores.moveToNext());
        }

        return autor;
    }

    public long insertAutor(Autores autores, SQLiteDatabase sqLiteDatabase){
        ContentValues values = new ContentValues();

        values.put("nombre",autores.getNombre());
        values.put("apellidos",autores.getApellidos());
        values.put("nick",autores.getNick());
        values.put("password",autores.getPassword());
        values.put("email",autores.getEmail());

        return sqLiteDatabase.insert("autores","id",values);
    }

    public long updateAutor(Autores autores, SQLiteDatabase sqLiteDatabase){
        ContentValues values = new ContentValues();
        Log.d("LOG","nombre: "+autores.getNombre());
        values.put("nombre",autores.getNombre());
        values.put("apellidos",autores.getApellidos());
        values.put("nick",autores.getNick());
        values.put("password",autores.getPassword());
        values.put("email",autores.getEmail());

        return sqLiteDatabase.update("autores",values,"id="+autores.getId(), null);
    }

    ////////////////////////////////NOTICIAS////////////////////////////////////////////////////7

    public ArrayList<Noticias> getAllNoticias(SQLiteDatabase db){
        ArrayList<Noticias> lista = new ArrayList<>();

        Cursor cursorNoticias = db.rawQuery("select *" +
                " from noticias",null);
        if(cursorNoticias.moveToFirst()){
            do{
                long idAutor = cursorNoticias.getLong(6);
                long idNoticia = cursorNoticias.getLong(0);
                Noticias noticia = new Noticias(idNoticia,
                        cursorNoticias.getString(1),
                        cursorNoticias.getString(2),
                        new Date(cursorNoticias.getLong(3)),
                        new Date(cursorNoticias.getLong(4)),
                        cursorNoticias.getString(5),
                        idAutor,
                        getAutor(db, idAutor ),
                        getAllComentarios(db, idNoticia)
                        );
                lista.add(noticia);
            }while(cursorNoticias.moveToNext());
        }
        return lista;
    }

    public ArrayList<Noticias> getNoticiasByUser(SQLiteDatabase db, Autores autores){
        ArrayList<Noticias> lista = new ArrayList<>();

        Cursor cursorNoticias = db.rawQuery("select *" +
                " from noticias where idAutor="+autores.getId(),null);
        if(cursorNoticias.moveToFirst()){
            do{
                long idAutor = cursorNoticias.getLong(6);
                long idNoticia = cursorNoticias.getLong(0);
                Noticias noticia = new Noticias(idNoticia,
                        cursorNoticias.getString(1),
                        cursorNoticias.getString(2),
                        new Date(cursorNoticias.getLong(3)),
                        new Date(cursorNoticias.getLong(4)),
                        cursorNoticias.getString(5),
                        idAutor,
                        getAutor(db, idAutor ),
                        getAllComentarios(db, idNoticia)
                );
                lista.add(noticia);
            }while(cursorNoticias.moveToNext());
        }
        return lista;
    }

    public long insertNoticias(Noticias noticias, SQLiteDatabase sqLiteDatabase){
        ContentValues values = new ContentValues();

        values.put("titulo",noticias.getTitulo());
        values.put("contenido",noticias.getContenido());
        values.put("fechaCreacion",noticias.getFechaCreacion().getTime());
        values.put("fechaUpdate",noticias.getFechaUpdate().getTime());
        values.put("imagen",noticias.getImagen());
        values.put("idAutor",noticias.getIdAutor());

        return sqLiteDatabase.insert("noticias","id",values);
    }

    public long updateNoticias(Noticias noticias, SQLiteDatabase sqLiteDatabase){
        ContentValues values = new ContentValues();

        values.put("titulo",noticias.getTitulo());
        values.put("contenido",noticias.getContenido());
        values.put("fechaUpdate",noticias.getFechaUpdate().getTime());
        values.put("imagen",noticias.getImagen());

        return sqLiteDatabase.update("noticias",values,"id="+noticias.getId(),null);
    }

    public long deleteNoticia(Noticias noticias, SQLiteDatabase sqLiteDatabase){
        return sqLiteDatabase.delete("noticias", "id="+noticias.getId(),null);
    }

    ///////////////////////////////// COMENTARIOS ////////////////////////////////////////

    public ArrayList<Comentarios> getAllComentarios(SQLiteDatabase db, long id){
        //TODO necesito un objeto autor en cada comentario
        ArrayList<Comentarios> lista = new ArrayList<>();

        Cursor cursorComentarios = db.rawQuery("select *" +
                " from comentarios where id="+id,null);
        if(cursorComentarios.moveToFirst()){
            do{
                Comentarios comentarios = new Comentarios(cursorComentarios.getInt(0),
                        cursorComentarios.getString(1),
                        new Date(cursorComentarios.getLong(2)),
                        new Date(cursorComentarios.getLong(3)),
                        cursorComentarios.getLong(4),
                        cursorComentarios.getLong(5),
                        cursorComentarios.getString(6));
                lista.add(comentarios);
            }while(cursorComentarios.moveToNext());
        }
        return lista;
    }

    public long insertComentario(Comentarios comentarios, SQLiteDatabase sqLiteDatabase){
        ContentValues values = new ContentValues();

        values.put("titulo",comentarios.getTitulo());
        values.put("contenido",comentarios.getContenido());
        values.put("fechaCreacion",comentarios.getFechaCreacion().getTime());
        values.put("fechaUpdate",comentarios.getFechaUpdate().getTime());
        values.put("idNoticia",comentarios.getIdNoticia());
        values.put("idAutor",comentarios.getIdAutor());

        return sqLiteDatabase.insert("comentarios","id",values);
    }

    public long updateComentario(Comentarios comentarios, SQLiteDatabase sqLiteDatabase){
        ContentValues values = new ContentValues();

        values.put("titulo",comentarios.getTitulo());
        values.put("contenido",comentarios.getContenido());
        values.put("fechaUpdate",comentarios.getFechaUpdate().getTime());

        return sqLiteDatabase.update("comentarios",values,"id="+comentarios.getId(), null);
    }

    public long deleteComentario(Comentarios comentarios, SQLiteDatabase sqLiteDatabase){
        return sqLiteDatabase.delete("comentarios", "id="+comentarios.getId(),null);
    }
}
