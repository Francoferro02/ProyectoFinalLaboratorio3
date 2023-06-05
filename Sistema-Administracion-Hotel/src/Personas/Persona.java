package Personas;

public abstract class Persona {
    protected String nombre;
    protected String apellido;
    protected String DNI;

    public Persona() {

    }

    public Persona(String nombre, String apellido, String DNI) {
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }
}
