package Personas;

import Controladora.Rol;
import Habitaciones.Comun;
import Habitaciones.Suite;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Administrador.class, name = "Personas.Administrador"),
        @JsonSubTypes.Type(value = Servicio.class, name = "Personas.Servicio"),
        @JsonSubTypes.Type(value = Recepcionista.class, name = "Personas.Recepcionista")

})
@JsonTypeName("Personas.Empleado")
public  abstract class Empleado extends Persona{

    protected double sueldo;
    protected  int diasVacaciones;
    protected  int antiguedad;
    protected Trabajadores trabajador;

    public Empleado() {
    }

    public Empleado(String nombre, String apellido, String DNI, double sueldo, int diasVacaciones, int antiguedad, Trabajadores trabajador) {
        super(nombre, apellido, DNI);
        this.sueldo = sueldo;
        this.diasVacaciones = diasVacaciones;
        this.antiguedad = antiguedad;
        this.trabajador = trabajador;
    }

    public abstract void realizarAcción();

    public abstract void calcularSueldo();

    public abstract void calcularDiasVacaciones();

    public abstract void fichaje();

    public abstract void desFichaje();

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public int getDiasVacaciones() {
        return diasVacaciones;
    }

    public void setDiasVacaciones(int diasVacaciones) {
        this.diasVacaciones = diasVacaciones;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public Trabajadores getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajadores trabajador) {
        this.trabajador = trabajador;
    }

    @Override
    public String toString() {
        return "\nEmpleado{" +
                "\nsueldo=" + sueldo +
                "\n, diasVacaciones=" + diasVacaciones +
                "\n, antiguedad=" + antiguedad +
                "\n, nombre='" + nombre + '\'' +
                "\n, apellido='" + apellido + '\'' +
                "\n, DNI=" + DNI;
    }
}
