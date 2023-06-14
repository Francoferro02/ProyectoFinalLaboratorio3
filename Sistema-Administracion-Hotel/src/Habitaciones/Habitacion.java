package Habitaciones;

import Servicios.Consumible;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Comun.class, name = "Habitaciones.Comun"),
        @JsonSubTypes.Type(value = Suite.class, name = "Habitaciones.Suite")
})
public abstract class Habitacion {

    public String numero;
    public boolean ocupada;
    public int capacidad;
    public boolean terraza;

    public double precio;


    public ArrayList<Consumible> consumibles = new ArrayList<>(); //ingresar al archivo de consumibles y mostrarlo

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


    public boolean isTerraza() {
        return terraza;
    }

    public ArrayList<Consumible> getConsumibles() {
        return consumibles;
    }


    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setTerraza(boolean terraza) {
        this.terraza = terraza;
    }

    public void setConsumibles(ArrayList<Consumible> consumibles) {
        this.consumibles = consumibles;
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
