package Personas;

public abstract class Persona {
    protected String nombre;
    protected String apellido;
    protected int DNI;



    public Persona(String nombre, String apellido, int DNI) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
    }

    public abstract void realizarAcción();

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", DNI=" + DNI +
                '}';
    }
}
