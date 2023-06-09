package Habitaciones;

public class Comun extends Habitacion {


    public Comun(String numero, int capacidad, boolean terraza, double precio) {
        super(numero, capacidad, terraza, precio);
    }

    @Override
    public String toString() {
        return "Comun{" +
                "numero=" + numero +
                ", capacidad=" + capacidad +
                ", terraza=" + terraza +
                ", precio=" + precio +
                '}';
    }
}