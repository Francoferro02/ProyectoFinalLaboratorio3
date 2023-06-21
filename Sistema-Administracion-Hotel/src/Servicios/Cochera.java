package Servicios;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Esta clase trata de la cochera del hotel, su espacio total, espacio disponible y el precio por día.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")

public class Cochera {

    private int espacioTotal;
    private int espacioDisponible;
    public final double precioDia = 2500;

    public Cochera() {
    }
    @JsonCreator
    public Cochera(@JsonProperty("espacioTotal") int espacioTotal,@JsonProperty("espacioDisponible") int espacioDisponible) {
        this.espacioTotal = espacioTotal;
        this.espacioDisponible = espacioDisponible;
    }

    public int getEspacioTotal() {
        return espacioTotal;
    }

    public int getEspacioDisponible() {
        return espacioDisponible;
    }


    public void setEspacioDisponible(int espacioDisponible) {
        this.espacioDisponible = espacioDisponible;
    }



    @Override
    public String toString() {
        return "\n------------------------------------------------" +
                "\nCochera{" +
                "\nespacioTotal=" + espacioTotal +
                "\n, espacioDisponible=" + espacioDisponible +
                "\n, precio por Dia=" + precioDia +
                '}';
    }
}
