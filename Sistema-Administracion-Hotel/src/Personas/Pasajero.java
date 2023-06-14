package Personas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.LocalDate;
import java.util.Date;
@JsonTypeName("Personas.Pasajero")
public class Pasajero extends Persona {

    private String origen;
    private String domicilioOrigen;
    private String historia; //opcional
    private int cantDias;


    public Pasajero(@JsonProperty("nombre")String nombre, @JsonProperty("apellido")String apellido, @JsonProperty("DNI")String DNI, @JsonProperty("origen")String origen, @JsonProperty("domicilioOrigen")String domicilioOrigen, @JsonProperty("historia")String historia) {
        super(nombre, apellido, DNI);
        this.origen = origen;
        this.domicilioOrigen = domicilioOrigen;
        this.historia = historia;
    }

    public Pasajero() {
        super();
    }

    public String getOrigen() {
        return origen;
    }

    public String getDomicilioOrigen() {
        return domicilioOrigen;
    }

    public String getHistoria() {
        return historia;
    }

    public int getCantDias() {
        return cantDias;
    }

    public void setCantDias(int cantDias) {
        this.cantDias = cantDias;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setDomicilioOrigen(String domicilioOrigen) {
        this.domicilioOrigen = domicilioOrigen;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    @Override
    public void realizarAcción() {

    }

    @Override
    public String toString() {
        return "\nPasajero{" +
                "\norigen='" + origen + '\'' +
                "\n, domicilioOrigen='" + domicilioOrigen + '\'' +
                "\n, historia='" + historia + '\'' +
                "\n, cantDias=" + cantDias +
                "\n, nombre='" + nombre + '\'' +
                "\n, apellido='" + apellido + '\'' +
                "\n, DNI='" + DNI + '\'' +
                '}';
    }
}
