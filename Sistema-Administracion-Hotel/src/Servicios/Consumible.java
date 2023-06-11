package Servicios;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

public class Consumible {

    private double precio;
    public String nombre;
    public String descripcion;

    public Consumible() {
    }

    public Consumible(double precio, String nombre, String descripcion) {
        this.precio = precio;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Servicios.Consumibles{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
