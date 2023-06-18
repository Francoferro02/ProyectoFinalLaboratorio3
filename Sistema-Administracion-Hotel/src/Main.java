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
       /* Administrador administrador = new Administrador("Lester","Crest","285435367",3000000,20,6,Trabajadores.ADMINISTRADOR.getAbreviaturas(0));
        Usuario user = new Usuario("Laboratorio3", Rol.ROL_ADMIN,administrador,"LesterCrest");
        lester.mapEmpleados.put(administrador.getDNI(),administrador);
        lester.listaUsuarios.add(user);
        System.out.println(lester.listaUsuarios);*/
        lester.mapHabitaciones = lester.leerArchivoMap("Habitaciones.json", String.class, Habitacion.class);
        lester.mapEmpleados = lester.leerArchivoMap("Empleados.json", String.class, Empleado.class);
        lester.mapReservas = lester.leerArchivoMap("Reservas.json", String.class, Reserva.class);
        lester.listaConsumibles = lester.leerArchivoArrayList("Consumibles.json", Consumible.class);
        lester.mapFacturas = lester.leerArchivoMap("Facturas.json", String.class, Factura.class);
        lester.listaPasajeros = lester.leerArchivoArrayList("Pasajeros.json", Pasajero.class);
        lester.leerAuxiliar("Hotel.json");
        lester.listaUsuarios = lester.leerArchivoArrayList("Usuarios.json", Usuario.class);
        lester.menuPrincipal();
    }
}
