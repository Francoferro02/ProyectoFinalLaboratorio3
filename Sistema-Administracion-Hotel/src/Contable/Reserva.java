package Contable;

import Habitaciones.Habitacion;
import Personas.Pasajero;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Reserva {
    public LocalDateTime fechaEntrada; //Revisar que hacer con esto
    public LocalDateTime fechaSalida;
    public ArrayList<Pasajero> pasajeros;
    public ArrayList<Habitacion> habitaciones;

    private boolean cochera;

    private int espaciosCochera;
    public String identificador;

    public Reserva() {
        this.identificador = (UUID.randomUUID().toString().toUpperCase());
        this.pasajeros = new ArrayList<>();
        this.habitaciones = new ArrayList<>();
    }

    public Reserva( LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.identificador = (UUID.randomUUID().toString().toUpperCase());
        this.habitaciones = new ArrayList<>();
        this.pasajeros = new ArrayList<>();

    }


    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public ArrayList<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public ArrayList<Habitacion> getHabitacion() {
        return habitaciones;
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
        return "Reserva{" +
                ", fechaEntrada=" + fechaEntrada +
                ", fechaSalida=" + fechaSalida +
                ", pasajero=" + pasajeros +
                ", habitacion=" + habitaciones +
                ", identificador='" + identificador + '\'' +
                '}';
    }

    public void marcarReserva() {

    }
}
