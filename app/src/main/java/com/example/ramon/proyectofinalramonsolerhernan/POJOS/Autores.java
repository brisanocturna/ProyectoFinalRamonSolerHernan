package com.example.ramon.proyectofinalramonsolerhernan.POJOS;


public class Autores {

  private long id;
  private String nombre;
  private String apellidos;
  private String nick;
  private String password;
  private String email;

  public Autores() {
  }

  public Autores(long id, String nombre, String apellidos, String nick, String password, String email) {
    this.id = id;
    this.nombre = nombre;
    this.apellidos = apellidos;
    this.nick = nick;
    this.password = password;
    this.email = email;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }


  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }


  public String getNick() {
    return nick;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
