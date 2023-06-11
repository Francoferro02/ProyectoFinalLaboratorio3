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
        return "Comun{" +
                "numero=" + numero +
                ", capacidad=" + capacidad +
                ", terraza=" + terraza +
                ", precio=" + precio +
                '}';
    }
}