import Contable.Factura;
import Contable.Reserva;
import Controladora.Hotel;
import Habitaciones.Habitacion;
import Personas.Empleado;
import Personas.Pasajero;
import Personas.Recepcionista;
import Personas.Usuario;
import Servicios.Consumible;


public class Main {
    public static void main(String[] args) {
        Hotel lester = new Hotel();
        Recepcionista recepcionista = new Recepcionista("Jose", "Lopez", "34534546", 200000, 20, 6);
        lester.mapHabitaciones = lester.leerArchivoMap(lester.archivoHabitaciones, String.class, Habitacion.class);
        //lester.leerArchivoMap(lester.archivoEmpleados, String.class, Empleado.class);
        //lester.mapReservas = lester.leerArchivoMap(lester.archivoReservas, String.class, Reserva.class);
        lester.listaConsumibles = lester.leerArchivoArrayList(lester.archivoConsumibles, Consumible.class);
        //lester.mapFacturas = lester.leerArchivoMap(lester.archivoFacturas, String.class, Factura.class);
        lester.listaPasajeros = lester.leerArchivoArrayList(lester.archivoPasajeros, Pasajero.class);
        lester.leerAuxiliar(lester.archivoHotel);
        lester.listaUsuarios = lester.leerArchivoArrayList(lester.archivoUsuarios, Usuario.class);
        //lester.mapEmpleados.put(recepcionista.getDNI(),recepcionista);
        //lester.escribirArchivoMap(lester.archivoEmpleados,lester.mapEmpleados);
        //System.out.println(lester.mapEmpleados.get(recepcionista.getDNI()));
        //lester.checkInRecepcionista(recepcionista);
        //recepcionista.informarCheckOut(lester.listaPasajeros,lester.mapReservas,lester.mapHabitaciones, lester.getCochera()); //Revisar archivo de pasajeros entero(Estructura y etiquetado de JSON)
        //lester.cargarHabitaciones();
        //lester.cargarConsumibles();
        //lester.leerTodosLosArchivos();
        //lester.realizarReserva();
        //System.out.println(lester);
        //lester.mostrarFactura();
        //  lester.cancelarReserva();
        //  lester.mostrarPasajeros();
        //lester.mostrarConsumibles();
        //System.out.println(lester.getDineroTotal());
        //System.out.println(lester.getCochera());
        //lester.menuRecepcionista(recepcionista);
        lester.menuPrincipal();


        lester.escribirTodosArchivos();//AL FINAL
        //lester.mostrarHabitaciones();
        //lester.mostrarReservas();
    }
}
