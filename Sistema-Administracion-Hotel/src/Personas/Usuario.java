package Personas;

import Controladora.Rol;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Clase de los usuarios del hotel.
 * Define ciertas caracteristicas del usuario, como su nombre, contraseña, que tipo de usuario es (pasajero - empleado - administrador) y sus características básicas.
 */
public class Usuario {

    public String nombreDeUsuario;
    public String contraseña;
    public Rol rol;
    public Persona persona;

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
        return "\n------------------------------------------------"+
                "\n  Usuario: " +
                "\n- Nombre de usuario: " + nombreDeUsuario +
                "\n- Contrasenia: " + contraseña +
                "\n- Rol: " + rol +
                "\n- Persona: " + persona;
    }
}
