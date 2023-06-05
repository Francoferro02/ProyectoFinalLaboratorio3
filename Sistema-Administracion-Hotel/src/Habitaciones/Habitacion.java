package Habitaciones;

import Servicios.Consumible;


public abstract class Habitacion {

    public int numero;
    private boolean ocupada;
    public int capacidad;
    public boolean terraza;

    public Consumible consumibles; //ingresar al archivo de consumibles y mostrarlo

    public Habitacion() {
    }

    public Habitacion(int numero, int capacidad, boolean terraza) {
        this.numero = numero;
        this.ocupada = false;
        this.capacidad = capacidad;
        this.terraza = terraza;
    }

    public int getNumero() {
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

    @Override
    public String toString() {
        return "\nHabitacion{" +
                "numero=" + numero +
                ", ocupada=" + ocupada +
                ", capacidad=" + capacidad +
                ", terraza=" + terraza +
                '}';
    }
}
