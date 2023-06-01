package Personas;

public class Servicio extends Empleado{

    Trabajadores trabajadores;

    public Servicio(String nombre, String apellido, String DNI, double sueldo, int diasVacaciones, int antiguedad, Trabajadores rol) {
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
