package com.example.irir1.myapplication.models;

/**
 * Mapeo de objetos JSON de planificados/id
 */

public class Message {
    private int id_asignar;
    private int id_clientes;

    private String nombres;
    private String codigo;


    public Message(int id_clientes ,int id_asignar, String nombres, String codigo) {
        this.id_asignar = id_asignar;
        this.id_clientes = id_clientes;
        this.nombres = nombres;
        this.codigo = codigo;

    }

    public int getiId_asignar() {
        return id_asignar;
    }
    public int getiId_clientes() {
        return id_clientes;
    }

    public String getNom() {
        return nombres;
    }

    public String getCod() {
        return codigo;
    }


}
