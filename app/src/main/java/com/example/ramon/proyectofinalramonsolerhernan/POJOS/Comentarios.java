package com.example.ramon.proyectofinalramonsolerhernan.POJOS;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Comentarios implements Parcelable {

  private long id;
  private String contenido;
  private Date fechaCreacion;
  private Date fechaUpdate;
  private long idAutor;
  private long idNoticia;
  private String titulo;
  private Autores autor;

    public Comentarios(long id, String contenido, Date fechaCreacion, Date fechaUpdate, long idAutor, long idNoticia, String titulo, Autores autor) {
        this.id = id;
        this.contenido = contenido;
        this.fechaCreacion = fechaCreacion;
        this.fechaUpdate = fechaUpdate;
        this.idAutor = idAutor;
        this.idNoticia = idNoticia;
        this.titulo = titulo;
        this.autor = autor;
    }

    public Comentarios(long id, String contenido, Date fechaCreacion, Date fechaUpdate, long idAutor, long idNoticia, String titulo) {
        this.id = id;
        this.contenido = contenido;
        this.fechaCreacion = fechaCreacion;
        this.fechaUpdate = fechaUpdate;
        this.idAutor = idAutor;
        this.idNoticia = idNoticia;
        this.titulo = titulo;
    }

  public Comentarios(String contenido, Date fechaCreacion, Date fechaUpdate, long idAutor, long idNoticia, String titulo) {
    this.contenido = contenido;
    this.fechaCreacion = fechaCreacion;
    this.fechaUpdate = fechaUpdate;
    this.idAutor = idAutor;
    this.idNoticia = idNoticia;
    this.titulo = titulo;
  }

    protected Comentarios(Parcel in) {
        id = in.readLong();
        contenido = in.readString();
        idAutor = in.readLong();
        idNoticia = in.readLong();
        titulo = in.readString();
    }

    public static final Creator<Comentarios> CREATOR = new Creator<Comentarios>() {
        @Override
        public Comentarios createFromParcel(Parcel in) {
            return new Comentarios(in);
        }

        @Override
        public Comentarios[] newArray(int size) {
            return new Comentarios[size];
        }
    };

    public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getContenido() {
    return contenido;
  }

  public void setContenido(String contenido) {
    this.contenido = contenido;
  }


  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }


  public Date getFechaUpdate() {
    return fechaUpdate;
  }

  public void setFechaUpdate(Date fechaUpdate) {
    this.fechaUpdate = fechaUpdate;
  }


  public long getIdAutor() {
    return idAutor;
  }

  public void setIdAutor(long idAutor) {
    this.idAutor = idAutor;
  }


  public long getIdNoticia() {
    return idNoticia;
  }

  public void setIdNoticia(long idNoticia) {
    this.idNoticia = idNoticia;
  }


  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

    public Autores getAutor() {
        return autor;
    }

    public void setAutor(Autores autor) {
        this.autor = autor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(contenido);
        dest.writeLong(idAutor);
        dest.writeLong(idNoticia);
        dest.writeString(titulo);
    }
}
