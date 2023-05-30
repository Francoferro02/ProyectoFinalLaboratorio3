package Habitaciones;

public class Suite extends Habitacion{

   private double precio;
   public String serviciosAdicionales;


    public Suite(int numero, boolean ocupada, int capacidad, boolean terraza, double precio, String serviciosAdicionales) {
        super(numero, ocupada, capacidad, terraza);
        this.precio = precio;
        this.serviciosAdicionales = serviciosAdicionales;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "Suite{" +
                "precio=" + precio +
                ", serviciosAdicionales='" + serviciosAdicionales + '\'' +
                ", numero=" + numero +
                ", capacidad=" + capacidad +
                ", terraza=" + terraza +
                ", consumibles=" + consumibles +
                ", serviciosBasicos='" + serviciosBasicos + '\'' +
                '}';
    }
}
