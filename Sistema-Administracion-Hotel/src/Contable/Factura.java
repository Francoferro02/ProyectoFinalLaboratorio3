package Contable;

import Habitaciones.Habitacion;
import Personas.Pasajero;
import Servicios.Cochera;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Factura {

    private double precioTotal;
    private Pasajero pasajero;
    public ArrayList<Habitacion> habitaciones;

    private LocalDateTime fechaDeEmision;
    private String codigoIdentificador;

    public Factura() {
        this.habitaciones = new ArrayList<>();
    }

    public Factura(double precioTotal, Pasajero pasajero, Habitacion habitacion, LocalDate fechaDeEmision, String codigoIdentificador) {
        this.precioTotal = precioTotal;
        this.pasajero = pasajero;
        this.habitaciones = new ArrayList<>();
        this.fechaDeEmision = fechaDeEmision;
        this.codigoIdentificador = codigoIdentificador;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public ArrayList<Habitacion> getHabitacion() {
        return habitaciones;
    }

    public LocalDateTime getfechaDeEmision() {
        return fechaDeEmision;
    }

    public String getCodigoIdentificador() {
        return codigoIdentificador;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public void setFechaDeEmision(LocalDateTime fechaDeEmision) {
        this.fechaDeEmision = fechaDeEmision;
    }

    public void setCodigoIdentificador(String codigoIdentificador) {
        this.codigoIdentificador = codigoIdentificador;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public void calcularPrecio(int dias,Reserva reserva,double precioCochera){

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
