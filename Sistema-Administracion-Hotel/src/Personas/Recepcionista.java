package Personas;

import Contable.Reserva;
import Habitaciones.Habitacion;
import Servicios.Cochera;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Clase específica se los recepcionistas de un hotel.
 * Contiene las características y métodos de los empleados, y ademas ciertas funciones específicas del hotel.
 */
@JsonTypeName("Personas.Recepcionista")
public class Recepcionista extends Empleado implements Mantenimiento {
    private Scanner teclado = new Scanner(System.in);

    public Recepcionista() {
        calcularSueldo();
    }

    public Recepcionista(@JsonProperty("nombre") String nombre, @JsonProperty("apellido") String apellido, @JsonProperty("DNI") String DNI, @JsonProperty("sueldo") double sueldo, @JsonProperty("antiguedad") int antiguedad, @JsonProperty("trabajador")String trabajador) {
        super(nombre, apellido, DNI, sueldo, antiguedad,trabajador);
        calcularSueldo();
    }

    /**
     * Función que se engarga de calcular el sueldo de un empleado teniendo en cuenta ciertas características.
     */
    @Override
    public void calcularSueldo() {
        double sueldo = this.sueldo;
        sueldo += sueldo*(this.antiguedad/100);
        this.setSueldo(sueldo);
    }

    /**
     * Esta función calcula la cantidad de días de vacaciones que un empleado debe tener, teniendo en cuenta ciertos factores
     */
    @Override
    public int calcularDiasVacaciones() {
        this.diasVacaciones = super.calcularDiasVacaciones();
        return this.diasVacaciones;
    }

    /**
     * Función que se encarga de registrar cuando el empleado entra al hotel
     */
    @Override
    public void fichaje() {
        super.fichaje();
    }

    /**
     * Función que se encarga de registrar cuando el empleado termina su turno en el hotel
     */
    @Override
    public void desFichaje() {
        super.desFichaje();
    }

    /**
     * Función la cual permite al recepcionista realizar el CheckIn de un pasajero.
     * @param mapReserva es el mapa de reservas del gotel para buscar dentro de está la reserva a relizarle el CheckIn.
     * @return true si bi tiene reserva el pasajero, y false si ya tiene y la habitación esta lista.
     */
    public boolean informarCheckIn(TreeMap<String, Reserva> mapReserva,TreeMap<String, Habitacion> mapHabitacion) {
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
                    for (Habitacion habitacion : mapReserva.get(codReserva).getHabitaciones()) {
                        mapHabitacion.get(habitacion.getNumero()).setOcupada(true);
                    }
                    System.out.println("Su habitacion/es ya se encuentra lista");
                    return false;
                } else {
                    exceptionLaunch = true;
                    throw new RuntimeException("Ese numero de reserva no es válido");
                }

            } while (exceptionLaunch == true);

        } else {
            return true;
        }
    }

    /**
     * Esta función permite al recepcionista realizar un CheckOut de un pasajero, y marca como disponible la habitación y cocheras ocupadas por estos pasajeros.
     * @param ListaPasajeros lista de pasajeros del hotel.
     * @param mapReserva mapa de reservas realizadas en el hotel.
     * @param mapHabitaciones mapa de habitaciones que tiene el hotel.
     * @param cochera cocheras del hotel.
     */
    public void informarCheckOut(ArrayList<Pasajero> ListaPasajeros, TreeMap<String, Reserva> mapReserva, TreeMap<String, Habitacion> mapHabitaciones, Cochera cochera) {
        String claveIdentificador;
        System.out.println("Buenos dias, su estadia en Lester Hotel ha finalizado. Porfavor indique su clave de reserva");
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
        System.out.println("Check Out de la reserva " + mapReserva.get(claveIdentificador).getIdentificador() +" realizado correctamente.");
        mapReserva.remove(claveIdentificador);
    }

    /**
     * Cantidad de habitaciones total del hotel.
     * @param cantidad cantidad de habitaciones del hotel.
     */
    public void informarCantHabitaciones(int cantidad) {
        System.out.println("Cantidad de habitaciones total: " + cantidad);
    }

    /**
     * Filtra todas las habitaciones ocupadas por del hotel.
     * @param map todas las habitaciones del hotel.
     */
    public void verOcupaciones(TreeMap<String, Habitacion> map) {
        for (String k : map.keySet()) {
            if (map.get(k).isOcupada() == true) {
                System.out.println(map.get(k));
                System.out.println(map.get(k).getEstado());
            }
        }
    }

    /**
     * Filtra todas las habitaciones desocupadas por del hotel.
     * @param map todas las habitaciones del hotel.
     */
    public void verDesocupadas(TreeMap<String, Habitacion> map) {
        for (String k : map.keySet()) {
            if (map.get(k).isOcupada() == false) {
                System.out.println(map.get(k));
            }
        }
    }

    /**
     * Esta función busca una habitación en específico del hotel a través de su código.
     * @param map todas las habitaciones del hotel.
     * @param numero el numero de la habitación a buscar.
     */
    public void buscarHabitacion(TreeMap<String, Habitacion> map, String numero) {
        System.out.println(map.get(numero));
    }

    /**
     * Habitaciones las cuales no estan disponibles por distintos problemas.
     * @param mapa todas las habitaciones del hotel.
     */
    @Override
    public void mostrarHabitacionesConProblemas(TreeMap<String, Habitacion> mapa) {
        for(String clave : mapa.keySet()){
            if(!(mapa.get(clave).getEstado().equals("Disponible"))){
                System.out.println(mapa.get(clave));
            }
        }
    }

    /**
     * Función que reporta todas las habitaciones las cuales tienen algún inconveniente.
     * @param mapa todas las habitaciones del hotel.
     * @param listaReportes lista de todas las habitaciones con sus respectivos reportes.
     */
    @Override
    public void darReporte(TreeMap<String, Habitacion> mapa , ArrayList<String> listaReportes) {
        for(String clave : mapa.keySet()){
            if(!(mapa.get(clave).getEstado().equals("Disponible"))){
                listaReportes.add("Habitacion "+mapa.get(clave).getNumero()+" en "+mapa.get(clave).getEstado()+" informar a mantenimiento");
            }
        }
    }

    @Override
    public String toString() {
        return "\n------------------------------------------------"+
                "\nRecepcionista: " +
                "\n- Nombre: " + nombre +
                "\n- Apellido: " + apellido +
                "\n- DNI: " + DNI +
                "\n- Sueldo: " + sueldo +
                "\n- Dias de vacaciones: " + diasVacaciones +
                "\n- Antiguedad: " + antiguedad +
                "\n- Trabajador: " + trabajador;
    }

    public void cerrarTeclado() {
        this.teclado.close();
    }

}

