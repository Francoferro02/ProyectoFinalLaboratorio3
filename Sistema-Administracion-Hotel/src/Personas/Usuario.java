package Personas;

import Controladora.Rol;

public class Usuario {

    private String contraseña;
    private Rol rol;

    public Usuario() {
    }

    public Usuario(String contraseña, Rol rol) {
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "contraseña='" + contraseña + '\'' +
                ", rol=" + rol +
                '}';
    }
}
