package Habitaciones;

public class Comun extends Habitacion{

    private double precio;

    public Comun(int numero, int capacidad, boolean terraza, double precio) {
        super(numero, capacidad, terraza);
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Comun{" +
                "precio=" + precio +
                ", numero=" + numero +
                ", capacidad=" + capacidad +
                ", terraza=" + terraza +
                '}';
    }
}
