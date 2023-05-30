package Contable;

import Habitaciones.Habitacion;
import Personas.Pasajero;

import java.time.LocalDate;
import java.util.UUID;

public class Factura {

    private double precioTotal;
    Pasajero pasajero;
    Habitacion habitacion;
    LocalDate fecha;
    String codigoIdentificador;

    public Factura() {
    }

    public Factura(double precioTotal, Pasajero pasajero, Habitacion habitacion, LocalDate fecha) {
        this.precioTotal = precioTotal;
        this.pasajero = pasajero;
        this.habitacion = habitacion;
        this.fecha = fecha;
        this.codigoIdentificador = (UUID.randomUUID().toString().toUpperCase());
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getCodigoIdentificador() {
        return codigoIdentificador;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public void calcularPrecio(){

    }

    @Override
    public String toString() {
        return "Contable.Factura{" +
                "precioTotal=" + precioTotal +
                ", pasajero=" + pasajero +
                ", habitacion=" + habitacion +
                ", fecha=" + fecha +
                ", codigoIdentificador='" + codigoIdentificador + '\'' +
                '}';
    }
}
