package com.example.ramon.proyectofinalramonsolerhernan.POJOS;


import java.util.ArrayList;
import java.util.Date;

public class Noticias {

  private long id;
  private String titulo;
  private String contenido;
  private Date fechaCreacion;
  private Date fechaUpdate;
  private String imagen;
  private long idAutor;
  private Autores autor;
  private ArrayList<Comentarios> comentarios;

  public Noticias(long id, String titulo, String contenido, Date fechaCreacion, Date fechaUpdate, String imagen, long idAutor) {
    this.id = id;
    this.titulo = titulo;
    this.contenido = contenido;
    this.fechaCreacion = fechaCreacion;
    this.fechaUpdate = fechaUpdate;
    this.imagen = imagen;
    this.idAutor = idAutor;
  }

  public Noticias(String titulo, String contenido, Date fechaCreacion, Date fechaUpdate, String imagen, long idAutor) {
    this.titulo = titulo;
    this.contenido = contenido;
    this.fechaCreacion = fechaCreacion;
    this.fechaUpdate = fechaUpdate;
    this.imagen = imagen;
    this.idAutor = idAutor;
  }

  public Noticias(long id, String titulo, String contenido, Date fechaCreacion, Date fechaUpdate,
                  String imagen, long idAutor, Autores autor, ArrayList<Comentarios> comentarios){
    this.id = id;
    this.titulo = titulo;
    this.contenido = contenido;
    this.fechaCreacion = fechaCreacion;
    this.fechaUpdate = fechaUpdate;
    this.imagen = imagen;
    this.idAutor = idAutor;
    this.autor = autor;
    this.comentarios = comentarios;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
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


  public String getImagen() {
    return imagen;
  }

  public void setImagen(String imagen) {
    this.imagen = imagen;
  }


  public long getIdAutor() {
    return idAutor;
  }

  public void setIdAutor(long idAutor) {
    this.idAutor = idAutor;
  }

  public Autores getAutor() {
    return autor;
  }

  public void setAutor(Autores autor) {
    this.autor = autor;
  }

  public ArrayList<Comentarios> getComentarios() {
    return comentarios;
  }

  public void setComentarios(ArrayList<Comentarios> comentarios) {
    this.comentarios = comentarios;
  }
}
