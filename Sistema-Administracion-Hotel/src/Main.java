import Contable.Factura;
import Contable.Reserva;
import Controladora.Hotel;
import Controladora.Rol;
import Habitaciones.Habitacion;
import Personas.*;
import Servicios.Consumible;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Random;


public class Main {
    public static void main(String[] args) throws IOException {
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
