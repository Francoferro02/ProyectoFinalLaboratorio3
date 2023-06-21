package Habitaciones;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Habitaciones.Suite")
public class Suite extends Habitacion{

    public String serviciosAdicionales;

@JsonCreator
    public Suite(@JsonProperty("numero")String numero, @JsonProperty("capacidad")int capacidad, @JsonProperty("terraza")boolean terraza, @JsonProperty("precio")double precio) {
        super(numero, capacidad, terraza, precio);
        this.serviciosAdicionales = "Incluyen  servicios de lavandería, servicios de transporte,servicios de spa, se notara la buena calidad se extenderá a los muebles, las tapicerías, las lámparas y, en general, a todos los elementos decorativos, así como a la vajilla, la cristalería, la cubertería y la ropa blanca. ";
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
