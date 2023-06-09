package Personas;

import com.fasterxml.jackson.annotation.*;


/**
 * Clase principal de las personas que se relacionan con el hotel {@link Pasajero} y {@link Empleado}.
 * Contiene las características básicas de una persona.
 * Es heredada a todos los tipos de personas que se encuentran en el hotel.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Pasajero.class, name = "Personas.Pasajero"),
        @JsonSubTypes.Type(value = Empleado.class, name = "Personas.Empleado"),
        @JsonSubTypes.Type(value = Usuario.class, name = "Usuario")

})
@JsonTypeName("Personas.Persona")

public abstract class Persona {
    protected String nombre;
    protected String apellido;
    protected String DNI;

    public Persona() {

    }
@JsonCreator
    public Persona(@JsonProperty("nombre")String nombre, @JsonProperty("apellido")String apellido, @JsonProperty("DNI")String DNI) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
    }



    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDNI() {
        return DNI;
    }

    @Override
    public String toString() {
        return  "\n- Nombre: " + nombre +
                "\n- Apellido: " + apellido +
                "\n- DNI:" + DNI;
    }
}
