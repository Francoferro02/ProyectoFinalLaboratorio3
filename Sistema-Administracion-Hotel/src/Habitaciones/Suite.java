package Habitaciones;

public class Suite extends Habitacion{

    public String serviciosAdicionales;


    public Suite(int numero, int capacidad, boolean terraza, double precio) {
        super(numero, capacidad, terraza, precio);
        this.serviciosAdicionales = "";
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "Suite{" +
                "numero=" + numero +
                ", capacidad=" + capacidad +
                ", terraza=" + terraza +
                ", precio=" + precio +
                ", consumibles=" + consumibles +
                '}';
    }
}
