package Habitaciones;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Habitaciones.Comun")
public class Comun extends Habitacion {

@JsonCreator
    public Comun(@JsonProperty("numero")String numero, @JsonProperty("capacidad")int capacidad, @JsonProperty("terraza")boolean terraza, @JsonProperty("precio")double precio) {
        super(numero, capacidad, terraza, precio);
    }


    @Override
    public String toString() {
        return "\n------------------------------------------------"+
                "\nComún: " +
                "\n- Número: " + numero +
                "\n- Ocupación: " + ocupada +
                "\n- Estado: " + estado +
                "\n- Capacidad: " + capacidad +
                "\n- Terraza: " + terraza +
                "\n- Precio: " + precio;
    }
}