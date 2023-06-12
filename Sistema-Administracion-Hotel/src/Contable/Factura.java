package Contable;

import Habitaciones.Habitacion;
import Personas.Pasajero;
import Servicios.Cochera;
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

public class Factura {

    private double precioTotal;
    private Pasajero pasajero;
    public ArrayList<Habitacion> habitaciones;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime fechaDeEmision;
    private String codigoIdentificador;

    public Factura() {
        this.habitaciones = new ArrayList<>();
    }

    public Factura(double precioTotal, Pasajero pasajero, LocalDateTime fechaDeEmision, String codigoIdentificador) {
        this.precioTotal = precioTotal;
        this.pasajero = pasajero;
        this.habitaciones = new ArrayList<>();
        this.fechaDeEmision = fechaDeEmision;
        this.codigoIdentificador = codigoIdentificador;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones;
    }
    @JsonProperty("habitaciones")
    public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public LocalDateTime getFechaDeEmision() {
        return fechaDeEmision;
    }

    public void setFechaDeEmision(LocalDateTime fechaDeEmision) {
        this.fechaDeEmision = fechaDeEmision;
    }

    public String getCodigoIdentificador() {
        return codigoIdentificador;
    }

    public void setCodigoIdentificador(String codigoIdentificador) {
        this.codigoIdentificador = codigoIdentificador;
    }

    public void calcularPrecio(int dias, Reserva reserva, double precioCochera){

        for (Habitacion h: reserva.habitaciones) {
            precioTotal += h.precio * dias;
        }
        if (reserva.isCochera() == true){
        precioTotal += ((precioCochera * reserva.getEspaciosCochera()) * dias);
        }
    }

    @Override
    public String toString() {
        return "Factura{" +
                "precio Total=" + precioTotal +
                ", pasajero=" + pasajero +
                ", habitacion=" + habitaciones +
                ", fecha de emision=" + fechaDeEmision +
                ", codigo Identificador='" + codigoIdentificador + '\'' +
                '}';
    }
}
