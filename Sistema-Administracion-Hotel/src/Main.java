import Controladora.Hotel;
import Servicios.Consumible;

import java.util.ArrayList;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        Hotel lester = new Hotel();
        //lester.cargarHabitaciones();
        lester.mapHabitaciones = lester.leerArchivoMap(lester.archivoHabitaciones);
        //lester.cargarConsumibles();
        lester.listaConsumibles = lester.leerArchivoArrayList(lester.archivoConsumibles, Consumible.class);
        lester.realizarReserva();
        //System.out.println(lester);
        //lester.mostrarHabitaciones();
        //lester.mostrarConsumibles();


       lester.escribirTodosArchivos(); //ABAJO DEL TODO
    }

}
