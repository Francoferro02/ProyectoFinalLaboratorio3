package Contable;

import Habitaciones.Habitacion;
import Personas.Pasajero;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Reserva {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public LocalDateTime fechaEntrada; //Revisar que hacer con esto

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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

    public void setFechaEntrada(LocalDateTime fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public void setPasajeros(ArrayList<Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones;
    }
    @JsonSetter
    public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
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
