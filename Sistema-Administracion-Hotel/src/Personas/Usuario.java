package Personas;

import Controladora.Rol;

public class Usuario {

    private String nombreDeUsuario;
    private String contraseña;
    private Rol rol;
    private Persona persona;

    public Usuario() {
    }

    public Usuario(String contraseña, Rol rol, Persona persona, String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.contraseña = contraseña;
        this.rol = rol;
        this.persona = persona;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
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
                "username='" + nombreDeUsuario + '\''+
                "contraseña='" + contraseña + '\''+
                ", rol=" + rol +
                '}';
    }
}
