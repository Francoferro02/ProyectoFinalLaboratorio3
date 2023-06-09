package Personas;

import java.time.LocalDate;
import java.util.Date;

public class Pasajero extends Persona {

    private String origen;
    private String domicilioOrigen;
    private String historia; //opcional
    private Boolean registrado;
    private int cantDias;

    public Pasajero(String nombre, String apellido, String DNI, String origen, String domicilioOrigen, String historia, Boolean registrado, int cantDias) {
        super(nombre, apellido, DNI);
        this.origen = origen;
        this.domicilioOrigen = domicilioOrigen;
        this.historia = historia;
        this.registrado = registrado;
        this.cantDias = cantDias;

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

    public Boolean getRegistrado() {
        return registrado;
    }

    public int getCantDias() {
        return cantDias;
    }


    public void setRegistrado(Boolean registrado) {
        this.registrado = registrado;
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
}
