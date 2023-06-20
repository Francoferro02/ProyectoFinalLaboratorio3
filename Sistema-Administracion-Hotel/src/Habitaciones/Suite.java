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
        this.serviciosAdicionales = "";
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
                "\nSuite: " +
                "\n- Número: " + numero +
                "\n- Ocupación: " + ocupada +
                "\n- Estado: " + estado +
                "\n- Capacidad: " + capacidad +
                "\n- Terraza: " + terraza +
                "\n- Precio: " + precio;
    }
}
