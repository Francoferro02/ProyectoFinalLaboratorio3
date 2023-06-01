package Controladora;
import Contable.Factura;
import Habitaciones.Comun;
import Habitaciones.Habitacion;
import Habitaciones.Suite;
import Personas.Empleado;
import Personas.Gerenciamiento;
import Personas.Pasajero;
import Servicios.Cochera;
import Servicios.Consumible;
import java.util.ArrayList;
import java.util.HashMap;



public class Hotel{

    public String nombre;
    public String direccion;
    public String ciudad;
    public int cantidadEstrellas;
    public String serviciosIncluidos;
    private Cochera cochera;
    public ArrayList<Pasajero> listaPasajeros = new ArrayList<>();
    public ArrayList<Empleado> listaEmpleados = new ArrayList<>();
    public ArrayList<Gerenciamiento> listaGerentes = new ArrayList<>();
    public HashMap<Integer, Habitacion> mapHabitaciones = new HashMap<>();
    public HashMap<String, Factura> mapFacturas = new HashMap<>();
    public ArrayList<Consumible> listaConsumibles = new ArrayList<>();
    private HashMap<String, String> nombreYcontrasena = new HashMap<>();

    public Hotel() {
    }

    public Hotel(String nombre, String direccion, String ciudad, int cantidadEstrellas) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.cantidadEstrellas = cantidadEstrellas;
        this.cochera = new Cochera(30, 30, 3000);
        this.serviciosIncluidos = "Cochera, Caja fuerte, tu vieja, Wi-Fi";
    }

    public void login() {

    }

    private void menuRecepcionista() {

    }

    private void menuAdministrador() {
    }

    public void menuCliente(){

    }
    private void registrarUsuario(){

    }

    private boolean verificarNombre(){
        return false;
    }

    private boolean verificarContrasena(){
        return false;
    }

    public void reservarHabitacion(){

    }

    public void solicitarConsumo(){

    }

    public void cargarHabitaciones(){
        Comun comun1 = new Comun(101, 4, false, 30000);
        Comun comun2 = new Comun(102, 2, true, 16000);
        Comun comun3 = new Comun(103, 3, false, 22000);
        Comun comun4 = new Comun(104, 2, true, 16000);
        Comun comun5 = new Comun(105, 2, false, 16000);
        Comun comun6 = new Comun(201, 2, false, 16000);
        Comun comun7 = new Comun(202, 3, true, 22000);
        Comun comun8 = new Comun(203, 4, false, 30000);
        Comun comun9 = new Comun(204, 4, true, 30000);
        Comun comun10 = new Comun(205, 2, false, 16000);
        Comun comun11 = new Comun(301, 3, false, 22000);
        Comun comun12 = new Comun(302, 3, true, 22000);
        Comun comun13 = new Comun(303, 2, false, 16000);
        Comun comun14 = new Comun(304, 2, true, 16000);
        Comun comun15 = new Comun(305, 4, false, 30000);
        Suite suite1 = new Suite(401, 5, true, 100000);
        Suite suite2 = new Suite(402, 5, true, 100000);
        Suite suite3 = new Suite(500, 6, true, 150000);

        this.mapHabitaciones.put(comun1.numero, comun1);
        this.mapHabitaciones.put(comun2.numero, comun2);
        this.mapHabitaciones.put(comun3.numero, comun3);
        this.mapHabitaciones.put(comun4.numero, comun4);
        this.mapHabitaciones.put(comun5.numero, comun5);
        this.mapHabitaciones.put(comun6.numero, comun6);
        this.mapHabitaciones.put(comun7.numero, comun7);
        this.mapHabitaciones.put(comun8.numero, comun8);
        this.mapHabitaciones.put(comun9.numero, comun9);
        this.mapHabitaciones.put(comun10.numero, comun10);
        this.mapHabitaciones.put(comun11.numero, comun11);
        this.mapHabitaciones.put(comun12.numero, comun12);
        this.mapHabitaciones.put(comun13.numero, comun13);
        this.mapHabitaciones.put(comun14.numero, comun14);
        this.mapHabitaciones.put(comun15.numero, comun15);
        this.mapHabitaciones.put(suite1.numero, suite1);
        this.mapHabitaciones.put(suite2.numero, suite2);
        this.mapHabitaciones.put(suite3.numero, suite3);


    }

    public void cargarConsumibles(){
        Consumible consu1 = new Consumible(800, "Agua mineral", "Villavicencio 500ml");
        Consumible consu2 = new Consumible(1000, "Gaseosa", "Coca-Cola, Sprite, Fanta 600ml");
        Consumible consu3 = new Consumible(1600, "Papas fritas", "Lay's 300gr");
        Consumible consu4 = new Consumible(5000, "Champagne", "Chandon 450ml");
        Consumible consu5 = new Consumible(18000, "Whiskey", "Black Label 750ml");
        Consumible consu6 = new Consumible(1200, "Maní", "Pehuamar 300gr");
        Consumible consu7 = new Consumible(1000, "Barra de granola", "Integra 80gr");
        Consumible consu8 = new Consumible(3000, "Bombones", "Ferrero Rocher 8 unidades 96gr");
        Consumible consu9 = new Consumible(1000, "Alfajor", "Havanna 60gr");
        Consumible consu10 = new Consumible(8000, "Vino", "Malbec Catena Zapata 750ml");

        this.listaConsumibles.add(consu1);
        this.listaConsumibles.add(consu2);
        this.listaConsumibles.add(consu3);
        this.listaConsumibles.add(consu4);
        this.listaConsumibles.add(consu5);
        this.listaConsumibles.add(consu6);
        this.listaConsumibles.add(consu7);
        this.listaConsumibles.add(consu8);
        this.listaConsumibles.add(consu9);
        this.listaConsumibles.add(consu10);

    }

    public void mostrarHabitaciones(){
        for (int habitacion: mapHabitaciones.keySet()) {
            System.out.println(mapHabitaciones.get(habitacion));
        }
    }

    public void mostrarConsumibles(){
        for (Consumible consumible: listaConsumibles) {
            System.out.println(consumible);
        }
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", cantidadEstrellas=" + cantidadEstrellas +
                ", cochera=" + cochera +
                '}';
    }
}

