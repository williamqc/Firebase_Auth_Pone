package com.example.william.firebase_auth_phone;

/**
 * Created by William on 31/10/2017.
 */

public class User {
    private String nombre;
    private String numero;
    private String Uid;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public User(String nombre, String numero, String Uid ){
        this.nombre = nombre;
        this.numero =numero;
        this.Uid =Uid;

    }
}
