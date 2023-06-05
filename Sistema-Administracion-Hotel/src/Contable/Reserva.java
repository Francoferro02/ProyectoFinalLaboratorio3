package Contable;

import Habitaciones.Habitacion;
import Personas.Pasajero;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Reserva {
    public LocalDate fechaEntrada; //Revisar que hacer con esto
    public LocalDate fechaSalida;
    public ArrayList<Pasajero> pasajeros;
    public ArrayList<Habitacion> habitacion;

    private boolean cochera;

    private int espaciosCochera;
    public String identificador;

    public Reserva() {
        this.identificador = (UUID.randomUUID().toString().toUpperCase());
    }

    public Reserva( LocalDate fechaEntrada, LocalDate fechaSalida, ArrayList<Habitacion> habitacion) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.pasajeros = new ArrayList<>();
        this.habitacion = habitacion;
        this.identificador = (UUID.randomUUID().toString().toUpperCase());
    }


    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public ArrayList<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public ArrayList<Habitacion> getHabitacion() {
        return habitacion;
    }


    public String getIdentificador() {
        return identificador;
    }

    public boolean isCochera() {
        return cochera;
    }

    public void setCochera(boolean cochera) {
        this.cochera = cochera;
    }

    public int getEspaciosCochera() {
        return espaciosCochera;
    }

    public void setEspaciosCochera(int espaciosCochera) {
        this.espaciosCochera = espaciosCochera;
    }

    @Override
    public String toString() {
        return "Contable.Reserva{" +
                ", fechaEntrada=" + fechaEntrada +
                ", fechaSalida=" + fechaSalida +
                ", pasajero=" + pasajeros +
                ", habitacion=" + habitacion +
                ", identificador='" + identificador + '\'' +
                '}';
    }

    public void marcarReserva() {

    }
}
