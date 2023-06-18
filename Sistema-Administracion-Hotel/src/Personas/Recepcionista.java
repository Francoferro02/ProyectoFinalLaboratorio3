package Personas;

import Contable.Reserva;
import Habitaciones.Habitacion;
import Servicios.Cochera;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

@JsonTypeName("Personas.Recepcionista")
public class Recepcionista extends Empleado implements Gerenciamiento {
    private Scanner teclado = new Scanner(System.in);

    public Recepcionista() {
    }

    public Recepcionista(@JsonProperty("nombre") String nombre, @JsonProperty("apellido") String apellido, @JsonProperty("DNI") String DNI, @JsonProperty("sueldo") double sueldo, @JsonProperty("antiguedad") int antiguedad, @JsonProperty("trabajador")String trabajador) {
        super(nombre, apellido, DNI, sueldo, antiguedad,trabajador);
    }


    @Override
    public void realizarAcción() {

    }

    @Override
    public void calcularSueldo() {

    }

    @Override
    public void calcularDiasVacaciones() {
        super.calcularDiasVacaciones();
    }

    @Override
    public void fichaje() {
        super.fichaje();
    }

    @Override
    public void desFichaje() {
        super.desFichaje();
    }

    public boolean informarCheckIn(TreeMap<String, Reserva> mapReserva) {
        char tiene = 's';
        String codReserva;
        boolean exceptionLaunch = false;
        System.out.println("Tiene reserva? s/n");
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

    public void informarCheckOut(ArrayList<Pasajero> ListaPasajeros, TreeMap<String, Reserva> mapReserva, TreeMap<String, Habitacion> mapHabitaciones, Cochera cochera) {
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
                cochera.setEspacioDisponible(cochera.getEspacioDisponible() + mapReserva.get(claveReserva).getEspaciosCochera());
            }
        }
        mapReserva.remove(claveIdentificador);
    }


    public void informarCantHabitaciones(int cantidad) {
        System.out.println("Cantidad de habitaciones total: " + cantidad);
    }

    public void verOcupaciones(TreeMap<String, Habitacion> map) {
        for (String k : map.keySet()) {
            if (map.get(k).isOcupada() == true) {
                System.out.println(map.get(k));
                System.out.println(map.get(k).getEstado());
            }
        }
    }

    public void verDesocupadas(TreeMap<String, Habitacion> map) {
        for (String k : map.keySet()) {
            if (map.get(k).isOcupada() == false) {
                System.out.println(map.get(k));
            }
        }
    }

    public void buscarHabitacion(TreeMap<String, Habitacion> map, String numero) {
        System.out.println(map.get(numero));
    }

    @Override
    public String toString() {
        return "\n------------------------------------------------"+
                "\nRecepcionista{" +
                "\nsueldo=" + sueldo +
                "\n, diasVacaciones=" + diasVacaciones +
                "\n, antiguedad=" + antiguedad +
                "\n, trabajador=" + trabajador +
                "\n, nombre='" + nombre + '\'' +
                "\n, apellido='" + apellido + '\'' +
                "\n, DNI='" + DNI + '\'';
    }

    public void cerrarTeclado() {
        this.teclado.close();
    }

}

