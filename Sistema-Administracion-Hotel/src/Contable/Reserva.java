package Contable;

import Habitaciones.Habitacion;
import Personas.Pasajero;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Reserva {

    public Boolean reservada;
    public LocalDate fechaEntrada;
    public LocalDate fechaSalida;
    public ArrayList<Pasajero> pasajeros;
    public Habitacion habitacion;
    public String identificador;

    public Reserva() {
    }

    public Reserva(Boolean reservada, LocalDate fechaEntrada, LocalDate fechaSalida, Habitacion habitacion) {
        this.reservada = reservada;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.pasajeros = new ArrayList<>();
        this.habitacion = habitacion;
        this.identificador = (UUID.randomUUID().toString().toUpperCase());
    }

    public Boolean getReservada() {
        return reservada;
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

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public String getIdentificador() {
        return identificador;
    }

    @Override
    public String toString() {
        return "Contable.Reserva{" +
                "reservada=" + reservada +
                ", fechaEntrada=" + fechaEntrada +
                ", fechaSalida=" + fechaSalida +
                ", pasajero=" + pasajeros +
                ", habitacion=" + habitacion +
                ", identificador='" + identificador + '\'' +
                '}';
    }

    public void marcarReserva(){

    }
}
