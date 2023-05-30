package Personas;

public class Servicios extends Empleado{

    Trabajadores trabajadores;

    public Servicios(String nombre, String apellido, int DNI, double sueldo, int diasVacaciones, int antiguedad,Trabajadores rol) {
        super(nombre, apellido, DNI, sueldo, diasVacaciones, antiguedad);
        this.usuario = new Usuario();
        this.trabajadores = rol;
    }

    @Override
    public void mostrarDatosUsuario() {
        super.mostrarDatosUsuario();
    }

    @Override
    public void realizarAcción() {

    }

    @Override
    public void calcularSueldo() {

    }

    @Override
    public void calcularDiasVacaciones() {

    }

    public void agregarTrabajadores(){

    }

    @Override
    public String toString() {
        return "Servicios{" +
                "trabajadores=" + trabajadores+
                ", sueldo=" + sueldo +
                ", diasVacaciones=" + diasVacaciones +
                ", antiguedad=" + antiguedad +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", DNI=" + DNI +
                '}';
    }
}
