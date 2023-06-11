import Controladora.Hotel;
import Habitaciones.Habitacion;
import Servicios.Consumible;


public class Main {
    public static void main(String[] args) {
        Hotel lester = new Hotel();
        //lester.cargarHabitaciones();
        lester.mapHabitaciones = lester.leerArchivoMap(lester.archivoHabitaciones, String.class, Habitacion.class);
        //lester.cargarConsumibles();
        lester.listaConsumibles = lester.leerArchivoArrayList(lester.archivoConsumibles, Consumible.class);
        //lester.realizarReserva();
        //System.out.println(lester);
        //lester.mostrarHabitaciones();
        //lester.mostrarConsumibles();


       lester.escribirTodosArchivos();//AL FINAL
    }
}
