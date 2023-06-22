package Controladora;

import Contable.Factura;
import Contable.Reserva;
import Habitaciones.Habitacion;
import Personas.*;
import Servicios.Consumible;


/**
 * Lee todos los archivos y llama al menú principal.
 * {@link Hotel#leerArchivoMap(String, Class, Class)}.
 * {@link Hotel#leerArchivoArrayList(String, Class)}.
 * {@link Hotel#leerAuxiliar(String)}.
 * {@link Hotel#menuPrincipal()}.
 * @author Buixados Tomás.
 * @author Ferro Franco Andres.
 * @author Fazio Galo.
 * @author Escobar Francisco.
 */
public class Main {
    public static void main(String[] args){
        Hotel lester = new Hotel();
     /*
       Administrador administrador = new Administrador("Lester","Crest","285435367",3000000,20,"ADMINISTRADOR");
        Usuario user = new Usuario("Laboratorio3", Rol.ROL_ADMIN,administrador,"LesterCrest");
        lester.mapEmpleados.put(administrador.getDNI(),administrador);
        lester.listaUsuarios.add(user);
        Recepcionista recepcionista = new Recepcionista("Juan","Lopez","36443553",250000,4,"RECEPCIONISTA");
        Usuario user2 = new Usuario("recepcionista",Rol.ROL_EMPLEADO,recepcionista,recepcionista.getDNI());
        lester.mapEmpleados.put(recepcionista.getDNI(),recepcionista);
        lester.listaUsuarios.add(user2);
*/
        lester.mapHabitaciones = lester.leerArchivoMap("Habitaciones.json", String.class, Habitacion.class);
        lester.mapEmpleados = lester.leerArchivoMap("Empleados.json", String.class, Empleado.class);
        lester.mapReservas = lester.leerArchivoMap("Reservas.json", String.class, Reserva.class);
        lester.listaConsumibles = lester.leerArchivoArrayList("Consumibles.json", Consumible.class);
        lester.mapFacturas = lester.leerArchivoMap("Facturas.json", String.class, Factura.class);
        lester.listaPasajeros = lester.leerArchivoArrayList("Pasajeros.json", Pasajero.class);
        lester.leerAuxiliar("Hotel.json");
        lester.listaUsuarios = lester.leerArchivoArrayList("Usuarios.json", Usuario.class);
        lester.reportes = lester.leerArchivoArrayList("Reportes.json", String.class);

        lester.menuPrincipal();
    }

}
