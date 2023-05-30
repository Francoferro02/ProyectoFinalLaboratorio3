package Personas;

public class Recepcionista extends Empleado implements Gerenciamiento{


    public Recepcionista(String nombre, String apellido, int DNI, double sueldo, int diasVacaciones, int antiguedad) {
        super(nombre, apellido, DNI, sueldo, diasVacaciones, antiguedad);
        this.usuario = new Usuario();
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

    @Override
    public void mostrarDatosUsuario() {
        super.mostrarDatosUsuario();
    }

    public void informarCheckIn(){
    }

    public void informarCheckOut(){

    }

    private void reservarHabitacion(){

    }

    public void informarCantHabitaciones(){

    }

    public void verOcupaciones(){

    }

    public void verDesocupadas(){

    }

    private double calcularPrecio(){

    }

    public void generarFactura(){

    }

    @Override
    public String toString() {
        return "Recepcionista{" +
                "sueldo=" + sueldo +
                ", diasVacaciones=" + diasVacaciones +
                ", antiguedad=" + antiguedad +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", DNI=" + DNI +
                '}';
    }
}
