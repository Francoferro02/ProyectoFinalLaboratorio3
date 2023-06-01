package Personas;

import java.util.ArrayList;
import java.util.Scanner;

public class Recepcionista extends Empleado implements Gerenciamiento{
    Scanner teclado = new Scanner(System.in);

    public Recepcionista(String nombre, String apellido, String DNI, double sueldo, int diasVacaciones, int antiguedad) {
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

    public ArrayList<Pasajero> registrarPasajero(){
        ArrayList<Pasajero> pasajeros = new ArrayList<>();

        char control = 's';

        while (control == 's') {
            Pasajero nuevo = new Pasajero();
            System.out.printf("\nPrimer nombre: ");
            nuevo.setNombre(teclado.next());
            System.out.printf("\nApellido: ");
            nuevo.setApellido(teclado.next());
            System.out.printf("\nDNI: ");
            nuevo.setDNI(teclado.next());
            System.out.printf("\nNacionalidad: ");
            nuevo.setOrigen(teclado.next());
            System.out.printf("\nDomicilio: ");
            nuevo.setDomicilioOrigen(teclado.next());
            System.out.printf("\nHistoria (Opcional): ");
            nuevo.setHistoria(teclado.next());
            nuevo.setRegistrado(true);
            System.out.printf("\nQuiere cochera? s/n: ");
            char a = teclado.next().toLowerCase().charAt(0);
            if (a == 's') {
                nuevo.setCochera(true);
                //falta en este caso descontar un espacio de cochera del total
            } else {
                nuevo.setCochera(false);
            }
            pasajeros.add(nuevo);

            System.out.printf("\nQuiere registrar a otro pasajero?: ");
            control = teclado.next().toLowerCase().charAt(0);
        }

        return pasajeros;

    }

    public void informarCheckIn(){
        ArrayList<Pasajero> pasajeros = new ArrayList<>();//registrarPasajero();
        System.out.printf("\nCuantos días se va a hospedar?: ");
        int cant = teclado.nextInt();
        for (Pasajero pasajero: pasajeros) {
            pasajero.setCantDias(cant);
        }

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
        return 0;
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

    public void cerrarTeclado(){
        this.teclado.close();
    }

}

