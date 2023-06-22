package Servicios;


/**
 * Consumibles los cuales pueden pedir los pasajeros al hospedarse en el hotel.
 */
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

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "\n------------------------------------------------"+
                "\n- Nombre: " + nombre + '\'' +
                "\n- Precio: " + precio +
                "\n- Descripcion: " + descripcion;
    }
}
