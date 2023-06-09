import Controladora.Hotel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Hotel lester = new Hotel("Lester Hotel", "Garay 074", "Mar del Plata", 4);
        //lester.cargarHabitaciones();
        lester.mapHabitaciones = lester.leerArchivoMap(lester.archivoHabitaciones);
        //lester.cargarConsumibles();
        lester.listaConsumibles = lester.leerArchivoArrayList(lester.archivoConsumibles);
        //System.out.println(lester);

        lester.mostrarHabitaciones();

        lester.mostrarConsumibles();
        /*
        lester.realizarReserva();
        lester.mostrarReservas();
*/
    }

}
