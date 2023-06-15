package Personas;

import Controladora.Rol;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")

public class Usuario {

    private String nombreDeUsuario;
    private String contraseña;
    private Rol rol;
    private Persona persona;

    public Usuario() {
    }
    @JsonCreator
    public Usuario(@JsonProperty("contraseña")String contraseña, @JsonProperty("rol")Rol rol, @JsonProperty("persona")Persona persona, @JsonProperty("nombreDeUsuario")String nombreDeUsuario) {
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
