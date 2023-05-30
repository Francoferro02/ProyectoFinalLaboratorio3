package Contable;

import Habitaciones.Habitacion;
import Personas.Pasajero;

import java.time.LocalDate;
import java.util.UUID;

public class Reserva {

    public Boolean reservada;
    public LocalDate fechaEntrada;
    public LocalDate fechaSalida;
    public Pasajero pasajero;
    public Habitacion habitacion;
    public String identificador;

    public Reserva() {
    }

    public Reserva(Boolean reservada, LocalDate fechaEntrada, LocalDate fechaSalida, Pasajero pasajero, Habitacion habitacion) {
        this.reservada = reservada;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.pasajero = pasajero;
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

    public Pasajero getPasajero() {
        return pasajero;
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
                ", pasajero=" + pasajero +
                ", habitacion=" + habitacion +
                ", identificador='" + identificador + '\'' +
                '}';
    }

    public void marcarReserva(){

    }
}
