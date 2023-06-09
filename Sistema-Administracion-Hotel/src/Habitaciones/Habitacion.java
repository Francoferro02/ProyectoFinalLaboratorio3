package Habitaciones;

import Servicios.Consumible;


public abstract class Habitacion {

    public String numero;
    private boolean ocupada;
    public int capacidad;
    public boolean terraza;

    public double precio;


    public Consumible consumibles; //ingresar al archivo de consumibles y mostrarlo

    public Habitacion() {
    }

    public Habitacion(String numero, int capacidad, boolean terraza, double precio) {
        this.numero = numero;
        this.ocupada = false;
        this.capacidad = capacidad;
        this.terraza = terraza;
        this.precio = precio;
    }

    public String getNumero() {
        return numero;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "\nHabitacion: "+ numero +
                "\nOcupada=" + ocupada +
                "\nCapacidad=" + capacidad +
                "\nTerraza=" + terraza +
                "\nPrecio=" + precio +
                "\n---------------------------------";
    }
}
