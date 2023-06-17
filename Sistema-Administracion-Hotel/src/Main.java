import Contable.Factura;
import Contable.Reserva;
import Controladora.Hotel;
import Controladora.Rol;
import Habitaciones.Habitacion;
import Personas.*;
import Servicios.Consumible;


public class Main {
    public static void main(String[] args) {
        Hotel lester = new Hotel();
       /* Administrador administrador = new Administrador("Lester","Crest","285435367",3000000,20,6,Trabajadores.ADMINISTRADOR);
        Usuario user = new Usuario("Laboratorio3", Rol.ROL_ADMIN,administrador,"LesterCrest");
        lester.mapEmpleados.put(administrador.getDNI(),administrador);
        lester.listaUsuarios.add(user);
        */
        lester.mapHabitaciones = lester.leerArchivoMap(lester.archivoHabitaciones, String.class, Habitacion.class);
        lester.leerArchivoMap(lester.archivoEmpleados, String.class, Empleado.class);
        lester.mapReservas = lester.leerArchivoMap(lester.archivoReservas, String.class, Reserva.class);
        lester.listaConsumibles = lester.leerArchivoArrayList(lester.archivoConsumibles, Consumible.class);
        lester.mapFacturas = lester.leerArchivoMap(lester.archivoFacturas, String.class, Factura.class);
        lester.listaPasajeros = lester.leerArchivoArrayList(lester.archivoPasajeros, Pasajero.class);
        lester.leerAuxiliar(lester.archivoHotel);
        lester.listaUsuarios = lester.leerArchivoArrayList(lester.archivoUsuarios, Usuario.class);
        lester.menuPrincipal();
    }
}
