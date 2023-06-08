package Personas;

import Controladora.Rol;

public  abstract class Empleado extends Persona{

    protected double sueldo;
    protected  int diasVacaciones;
    protected  int antiguedad;
    protected Usuario usuario;

    public Empleado(String nombre, String apellido, String DNI, double sueldo, int diasVacaciones, int antiguedad) {
        super(nombre, apellido, DNI);
        this.sueldo = sueldo;
        this.diasVacaciones = diasVacaciones;
        this.antiguedad = antiguedad;
    }

    @Override
    public abstract void realizarAcción();

    public abstract void calcularSueldo();

    public abstract void calcularDiasVacaciones();
    public void mostrarDatosUsuario(){
        System.out.println(this.usuario);
    }
    @Override
    public String toString() {
        return "Empleado{" +
                "sueldo=" + sueldo +
                ", diasVacaciones=" + diasVacaciones +
                ", antiguedad=" + antiguedad +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", DNI=" + DNI +
                '}';
    }
}
