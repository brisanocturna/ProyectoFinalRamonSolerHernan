package com.example.ramon.proyectofinalramonsolerhernan.POJOS;


import java.util.Date;

public class Comentarios {

  private long id;
  private String contenido;
  private Date fechaCreacion;
  private Date fechaUpdate;
  private long idAutor;
  private long idNoticia;
  private String titulo;

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

}
