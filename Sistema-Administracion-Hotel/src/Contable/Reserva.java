package Contable;

import Habitaciones.Habitacion;
import Personas.Pasajero;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private boolean lateCheckOut;
    private boolean earlyCheckIn;

    public Reserva() {
        this.identificador = (this.identificador = (UUID.randomUUID().toString().toUpperCase())).substring(0,5);
        this.pasajeros = new ArrayList<>();
        this.habitaciones = new ArrayList<>();
        this.lateCheckOut = false;
        this.earlyCheckIn = false;
    }

    public Reserva( LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.identificador = (this.identificador = (UUID.randomUUID().toString().toUpperCase())).substring(0,5);
        this.habitaciones = new ArrayList<>();
        this.pasajeros = new ArrayList<>();
        this.lateCheckOut = false;
        this.earlyCheckIn = false;

    }

    public boolean isLateCheckOut() {
        return lateCheckOut;
    }

    public void setLateCheckOut(boolean lateCheckOut) {
        this.lateCheckOut = lateCheckOut;
    }

    public boolean isEarlyCheckIn() {
        return earlyCheckIn;
    }

    public void setEarlyCheckIn(boolean earlyCheckIn) {
        this.earlyCheckIn = earlyCheckIn;
    }

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDateTime fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public ArrayList<Pasajero> getPasajeros() {
        return pasajeros;
    }
    @JsonProperty("pasajeros")
    public void setPasajeros(ArrayList<Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }
    @JsonProperty("habitaciones")
    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
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

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @Override
    public String toString() {
        return  "\n------------------------------------------------" +
                "\nReserva{" +
                "\n, fechaEntrada=" + fechaEntrada +
                "\n, fechaSalida=" + fechaSalida +
                "\n, pasajero=" + pasajeros +
                "\n, habitacion=" + habitaciones +
                "\n, identificador='" + identificador + '\'';
    }

    public void marcarReserva() {

    }
}
