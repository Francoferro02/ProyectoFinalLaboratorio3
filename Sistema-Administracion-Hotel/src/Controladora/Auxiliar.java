package Controladora;

import Servicios.Cochera;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Clase que guarda los datos indicados de la clase controladora {@link Hotel}.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Auxiliar {

    private double dineroTotal;
    private Cochera cochera;

    public Auxiliar() {
    }
    @JsonCreator
    public Auxiliar(@JsonProperty("dineroTotal")double dineroTotal,@JsonProperty("cochera") Cochera cochera) {
        this.dineroTotal = dineroTotal;
        this.cochera = cochera;
    }

    public double getDineroTotal() {
        return dineroTotal;
    }

    public void setDineroTotal(double dineroTotal) {
        this.dineroTotal = dineroTotal;
    }

    public Cochera getCochera() {
        return cochera;
    }

    public void setCochera(Cochera cochera) {
        this.cochera = cochera;
    }
}
