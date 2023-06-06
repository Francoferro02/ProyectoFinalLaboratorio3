import Controladora.Hotel;

public class Main {
    public static void main(String[] args) {
        Hotel lester = new Hotel("Lester Hotel", "Garay 074", "Mar del Plata", 4);
        lester.cargarHabitaciones();
        lester.cargarConsumibles();

        System.out.println(lester);
        lester.mostrarHabitaciones();
        lester.mostrarConsumibles();
        lester.realizarReserva();
        lester.mostrarReservas();

    }

}
