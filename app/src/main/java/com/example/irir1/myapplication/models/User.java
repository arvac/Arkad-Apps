package com.example.irir1.myapplication.models;

/**
 * Created by Belal on 14/04/17.
 */

public class User {

    private int id_cuadrilla;
    private String nombres;
    private String direccion;
    private String email;
    private String password;

    public User(String nombres, String email, String password, String direccion) {
        this.nombres = nombres;
        this.email = email;
        this.password = password;
        this.direccion = direccion;
    }

    public User(int id, String nombres, String email, String direccion){
        this.id_cuadrilla = id;
        this.nombres = nombres;
        this.email = email;
        this.direccion = direccion;
    }

    public User(int id, String nombres, String email, String password, String direccion) {
        this.id_cuadrilla = id;
        this.nombres = nombres;
        this.email = email;
        this.password = password;
        this.direccion = direccion;
    }

    public int getId() {
        return id_cuadrilla;
    }

    public String getName() {
        return nombres;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getGender() {
        return direccion;
    }
}
