package com.app.bambuappabz;

public class Usuarios {
    private String pass;
    private String Nombre;
    private String Apellido;
    private String Correo;




    public Usuarios() {
    }

    public String getpass() {
        return pass;
    }

    public void setpass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
