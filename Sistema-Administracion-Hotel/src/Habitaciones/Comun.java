package Habitaciones;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Habitaciones comunes del hotel.
 */
@JsonTypeName("Habitaciones.Comun")
public class Comun extends Habitacion {

@JsonCreator
    public Comun(@JsonProperty("numero")String numero, @JsonProperty("capacidad")int capacidad, @JsonProperty("terraza")boolean terraza, @JsonProperty("precio")double precio) {
        super(numero, capacidad, terraza, precio);
    }


    @Override
    public String toString() {
        return "\n------------------------------------------------"+
                "\n  Comun: " +
                "\n- Numero: " + numero +
                "\n- Ocupacion: " + ocupada +
                "\n- Estado: " + estado +
                "\n- Capacidad: " + capacidad +
                "\n- Terraza: " + terraza +
                "\n- Precio: " + precio;
    }
}