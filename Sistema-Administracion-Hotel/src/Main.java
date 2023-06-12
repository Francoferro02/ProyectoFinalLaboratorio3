import Contable.Factura;
import Contable.Reserva;
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
        lester.mapReservas = lester.leerArchivoMap(lester.archivoReservas,String.class, Reserva.class);
        lester.mapFacturas = lester.leerArchivoMap(lester.archivoFacturas,String.class, Factura.class);
        //lester.mostrarReservas();
        lester.realizarReserva();
        //System.out.println(lester);
        //lester.mostrarFactura();
        //lester.mostrarHabitaciones();
        //lester.mostrarConsumibles();


       lester.escribirTodosArchivos();//AL FINAL
    }
}
