package Personas;

import Contable.Reserva;
import Habitaciones.Habitacion;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

@JsonTypeName("Personas.Recepcionista")
public class Recepcionista extends Empleado implements Gerenciamiento {
    Scanner teclado = new Scanner(System.in);

    public Recepcionista(@JsonProperty("nombre") String nombre, @JsonProperty("apellido") String apellido, @JsonProperty("DNI") String DNI, @JsonProperty("sueldo") double sueldo, @JsonProperty("diasVacaciones") int diasVacaciones, @JsonProperty("antiguedad") int antiguedad) {
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


    public boolean informarCheckIn(TreeMap<String, Reserva> mapReserva) {
        char tiene = 's';
        String codReserva;
        boolean exceptionLaunch = false;
        System.out.println("Buenos dias, les saludo atentamente. Usted se encuentra con reserva?");
        tiene = teclado.next().charAt(0);
        if (tiene == 's') {
            do {
                System.out.println("Indique su codigo de reserva");
                codReserva = teclado.next();
                if (mapReserva.containsKey(codReserva)) {
                    System.out.println("Su habitacion ya se encuentra lista");
                    return false;
                } else {
                    exceptionLaunch = true;
                    throw new RuntimeException("Ese nuemero de reserva no es valido");
                }

            } while (exceptionLaunch == true);

        } else {
            System.out.println("Perfecto dejeme registrar a los pasajeros");
            return true;
        }
    }

    public void informarCheckOut(ArrayList<Pasajero> ListaPasajeros, TreeMap<String, Reserva> mapReserva, TreeMap<String, Habitacion> mapHabitaciones) {
        String claveIdentificador;
        System.out.println("Buenos dias, su estadia en Lester Hotel ha finalizado. Porfavor indiqueme su clave de reserva");
        claveIdentificador = teclado.next();
        for (String claveReserva : mapReserva.keySet()) {
            if (mapReserva.get(claveReserva).identificador.equals(claveIdentificador)) {
                for (Pasajero pasajero : mapReserva.get(claveReserva).pasajeros) {
                    ListaPasajeros.remove(pasajero);
                }
                for (Habitacion habitacion : mapReserva.get(claveReserva).habitaciones) {
                    mapHabitaciones.get(habitacion.numero).setOcupada(false);
                }
            }
        }
        mapReserva.remove(claveIdentificador);
    }

    private void reservarHabitacion() {

    }

    public void informarCantHabitaciones() {

    }

    public void verOcupaciones() {

    }

    public void verDesocupadas() {

    }

    private double calcularPrecio() {
        return 0;
    }

    public void generarFactura() {

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

    public void cerrarTeclado() {
        this.teclado.close();
    }

}

