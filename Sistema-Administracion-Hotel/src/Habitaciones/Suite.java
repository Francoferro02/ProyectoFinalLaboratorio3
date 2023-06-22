package Habitaciones;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Habitaciones suites del hotel, con sus servicios adicionales que las distinguen.
 */
@JsonTypeName("Habitaciones.Suite")
public class Suite extends Habitacion{

    public String serviciosAdicionales = "Incluyen  servicios de lavanderia, servicios de transporte,servicios de spa, se notara la buena calidad se extendera a los muebles, las tapicerias, las lamparas y, en general, a todos los elementos decorativos, asi como a la vajilla, la cristaleria, la cuberteria y la ropa blanca. ";

@JsonCreator
    public Suite(@JsonProperty("numero")String numero, @JsonProperty("capacidad")int capacidad, @JsonProperty("terraza")boolean terraza, @JsonProperty("precio")double precio) {
        super(numero, capacidad, terraza, precio);
    }

    public double getPrecio() {
        return precio;
    }

    public String getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public void setServiciosAdicionales(String serviciosAdicionales) {
        this.serviciosAdicionales = serviciosAdicionales;
    }

    @Override
    public String toString() {
        return "\n------------------------------------------------"+
                "\n  Suite: " +
                "\n- Numero: " + numero +
                "\n- Ocupacion: " + ocupada +
                "\n- Estado: " + estado +
                "\n- Capacidad: " + capacidad +
                "\n- Terraza: " + terraza +
                "\n- Servicios adicionales: " + serviciosAdicionales +
                "\n- Precio: " + precio;
    }
}
