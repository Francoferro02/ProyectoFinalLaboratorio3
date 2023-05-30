package Habitaciones;

import Servicios.Consumibles;

public abstract class Habitacion {

    public int numero;
    private boolean ocupada;
    public int capacidad;
    public boolean terraza;
    public Consumibles consumibles; //ingresar al archivo de consumibles y mostrarlo
    public String serviciosBasicos;

    public Habitacion() {
    }

    public Habitacion(int numero, boolean ocupada, int capacidad, boolean terraza) {
        this.numero = numero;
        this.ocupada = ocupada;
        this.capacidad = capacidad;
        this.terraza = terraza;
        this.serviciosBasicos = ""; //texto con servicios basicos
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public int getCapacidad() {
        return capacidad;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "numero=" + numero +
                ", ocupada=" + ocupada +
                ", capacidad=" + capacidad +
                ", terraza=" + terraza +
                ", consumibles=" + consumibles +
                ", serviciosBasicos='" + serviciosBasicos + '\'' +
                '}';
    }
}
