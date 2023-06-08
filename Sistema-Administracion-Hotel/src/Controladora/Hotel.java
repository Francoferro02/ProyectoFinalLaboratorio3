package Controladora;

import Contable.Factura;
import Contable.Reserva;
import Habitaciones.Comun;
import Habitaciones.Habitacion;
import Habitaciones.Suite;
import Personas.Empleado;
import Personas.Gerenciamiento;
import Personas.Pasajero;
import Personas.Usuario;
import Servicios.Cochera;
import Servicios.Consumible;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class Hotel <K,T>{

    private String serviciosIncluidos;
    public String nombre;
    public String direccion;
    public String ciudad;
    public int cantidadEstrellas;

    private double dineroTotal;
    private Cochera cochera;
    public ArrayList<Pasajero> listaPasajeros = new ArrayList<>();
    public HashMap<Integer, Habitacion> mapHabitaciones = new HashMap<>();
    public HashMap<String, Factura> mapFacturas = new HashMap<>();
    public ArrayList<Consumible> listaConsumibles = new ArrayList<>();
    private HashMap<String, Empleado> mapEmpleados = new HashMap<>();//clave=DNI
    public HashMap<String, Reserva> mapReservas = new HashMap<>();

    static final String ruta = "D:\\Documents\\Facultad\\Programación y Laboratorio III\\TP Final\\ProyectoFinalLaboratorio3\\Sistema-Administracion-Hotel\\src\\Files\\";
    public File archivoHotel = new File(ruta+"Hotel.json");//SOLO GUARDA EL HOTEL
    public File archivoHabitaciones = new File(ruta+"Habitaciones.json");
    public File archivoFacturas = new File(ruta+"Facturas.json");
    public File archivoEmpleados = new File(ruta+"Empleados.json");
    public File archivoReservas = new File(ruta+"Reservas.json");
    public File archivoPasajeros = new File(ruta+"Pasajeros.json");
    public File archivoConsumibles = new File(ruta+"Consumibles.json");

    ObjectMapper mapper = new ObjectMapper();
    Scanner teclado = new Scanner(System.in);

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

    public void menuCliente() {

    }

    private void registrarUsuario() {

    }

    private boolean verificarNombre() {
        return false;
    }

    private boolean verificarContrasena() {
        return false;
    }

    public void realizarReserva() {
        Reserva reserva = new Reserva();
        System.out.println("Bienvenido/a al sistema de reservas de habitaciones del hotel Lester, a continuacion le solicitaremos los datos de los hospedantes");
        reserva.pasajeros = registrarPasajero();
        System.out.println("Ingrese la fecha de entrada(Dia/Mes/Anio): ");
        System.out.println("Ingrese dia:");
        int dia = teclado.nextInt();
        System.out.println("Ingrese mes:");
        int mes = teclado.nextInt();
        System.out.println("Ingrese anio:");
        int anio = teclado.nextInt();
        LocalDateTime fechaEntrada = LocalDateTime.of(anio, mes, dia, 18, 00);
        reserva.fechaEntrada = fechaEntrada;
        System.out.println("Ingrese la fecha de salida(Dia/Mes/Anio): ");
        System.out.println("Ingrese dia:");
        dia = teclado.nextInt();
        System.out.println("Ingrese mes:");
        mes = teclado.nextInt();
        System.out.println("Ingrese anio:");
        anio = teclado.nextInt();
        LocalDateTime fechaSalida = LocalDateTime.of(anio, mes, dia, 10,00);
        reserva.fechaSalida = fechaSalida;
        if (cochera.getEspacioDisponible() > 0) {
            registrarCochera(reserva);
        } else {
            System.out.println("En este momento nuestra cochera se encuentra ocupada, lamentamos el inconveniente");
        }
        System.out.println("Nuestras habitaciones disponibles son las siguientes...");
        registrarHabitacion(reserva, fechaEntrada);
        mapReservas.put(reserva.identificador, reserva);
        //Sistema de facturacion
        System.out.println("A continuacion se imprimira la factura de su reserva. ACLARACION IMPORTANTE: El precio final incluye la estadia y la cochera. LOS CONSUMIBLES SE PAGAN AL MOMENTO DE ORDENARLO");
        Factura factura = new Factura();
        generarFactura(factura,reserva);
        System.out.println("Felicitaciones ya realizaste tu reserva en Lester Hotel. Te esperamos pronto");
        System.out.println(factura);
    }

    private void generarFactura(Factura factura, Reserva reserva){
        factura.setCodigoIdentificador(reserva.getIdentificador());
        factura.calcularPrecio(calcularDias(reserva), reserva,cochera.precioDia);
        factura.habitaciones = reserva.habitaciones;
        factura.setPasajero(reserva.getPasajeros().get(0));
        LocalDateTime ahora = LocalDateTime.now();
        factura.setFechaDeEmision(ahora);
        dineroTotal += factura.getPrecioTotal();
        if (mapFacturas != null)
            for (String clave: mapFacturas.keySet()) {
                try {
                    if (factura.getCodigoIdentificador().equals(mapFacturas.get(clave).getCodigoIdentificador())){
                         throw new IOException();
                    }
                }catch (IOException e){
                    System.out.println("Esa factura ya se encuentra en la lista,revise correctamente los datos");
                }
            }
    }



    private int calcularDias(Reserva reserva){
        long dias = (reserva.fechaSalida.toEpochSecond(ZoneOffset.UTC) - reserva.fechaEntrada.toEpochSecond(ZoneOffset.UTC));
        dias = dias/(1000*60*60*24);
        return (int) dias;
    }
    private ArrayList<Pasajero> registrarPasajero() {
        ArrayList<Pasajero> pasajeros = new ArrayList<>();
        boolean excepcionLanzada = false;
        char control = 's';
        while (control == 's') {
            Pasajero nuevo = new Pasajero();
            do {
                System.out.printf("\nPrimer nombre: ");
                nuevo.setNombre(teclado.next());
                System.out.printf("\nApellido: ");
                nuevo.setApellido(teclado.next());
                System.out.printf("\nDNI: ");
                nuevo.setDNI(teclado.next());
                try {
                    for (Pasajero p : listaPasajeros) {
                        if (p.getDNI().equals(nuevo.getDNI())) {
                            throw new IOException();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("El DNI ingresado ya se encuentra registrado, porfavor intente con un nuevo pasajero");
                    excepcionLanzada = true;
                }
                System.out.printf("\nNacionalidad: ");
                nuevo.setOrigen(teclado.next());
                System.out.printf("\nDomicilio: ");
                nuevo.setDomicilioOrigen(teclado.next());
                System.out.printf("\nHistoria (Opcional): ");
                nuevo.setHistoria(teclado.next());
            } while (excepcionLanzada == true);
            nuevo.setRegistrado(true);
            pasajeros.add(nuevo);
            listaPasajeros.add(nuevo);
            System.out.printf("\nQuiere registrar a otro pasajero? s/n: ");
            control = teclado.next().charAt(0);
        }
        return pasajeros;
    }

    private void registrarCochera(Reserva reserva) {
        int espacios = 0;
        System.out.printf("\nQuiere cochera? s/n: ");
        char a = teclado.next().toLowerCase().charAt(0);
        if (a == 's') {
            reserva.setCochera(true);
            do {
                System.out.println("Cuantos vehiculos quiere almacenar? Quedan disponibles " + cochera.getEspacioDisponible());
                espacios = teclado.nextInt();
                if (espacios > cochera.getEspacioDisponible()) {
                    System.out.println("No tenemos suficiente espacio disponible en nuestra cochera, el MAXIMO es: " + cochera.getEspacioDisponible());
                } else {
                    cochera.setEspacioDisponible(cochera.getEspacioDisponible() - espacios);
                    reserva.setEspaciosCochera(espacios);
                    System.out.println("Su cochera se encuentra reservada");
                }
            } while (espacios > cochera.getEspacioDisponible());
        } else {
            reserva.setCochera(false);
        }
    }

    private void registrarHabitacion(Reserva reserva, LocalDateTime fechaEntrada) {
        char continuar = 's';
        int numero = 0;
        boolean ocupacion;
        do {
            for (Integer number : mapHabitaciones.keySet()) {
                System.out.println(mapHabitaciones.get(number));
            }
            do {
                System.out.println("Ingrese el numero de habitacion que quiere reservar");
                numero = teclado.nextInt();
                ocupacion = verificarOcupacion(numero, fechaEntrada);
                if (!ocupacion) {
                    System.out.println("La habitacion que selecciono se encuentra reservada hasta una fecha posterior a su fecha de entrada.");
                } else {
                    System.out.println("Gran eleccion!!");
                    reserva.habitaciones.add(mapHabitaciones.get(numero));
                }
            } while (!ocupacion);
            System.out.println("Quiere reservar otra habitacion mas?");
            continuar = teclado.next().charAt(0);
        } while (continuar == 's');
    }

    private boolean verificarOcupacion(int habitacion, LocalDateTime fechaEntrada) {
        int cont = 0;
        for (String reserva1 : mapReservas.keySet()) {
            if (mapReservas.get(reserva1).habitaciones.get(cont).numero == habitacion) {
                if (mapReservas.get(reserva1).fechaSalida.compareTo(fechaEntrada) == 1 || mapReservas.get(reserva1).fechaSalida.compareTo(fechaEntrada) == 0) {
                    return false;
                } else if (mapReservas.get(reserva1).fechaSalida.compareTo(fechaEntrada) < 0) {
                    return true;
                }
            }
            cont++;
        }
        return true;
    }

    public void mostrarReservas() {
        System.out.println("holaaaa");
        for (String clave : mapReservas.keySet()) {
            System.out.println(mapReservas.get(clave));
        }
    }

    public void solicitarConsumo() {

    }
/*
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

        this.escribirArchivoMap(this.archivoHabitaciones, (HashMap<K, T>) this.mapHabitaciones);

    }
*/
    public void cargarConsumibles() {
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

    public void mostrarHabitaciones() {
        for(int clave : mapHabitaciones.keySet()){
            System.out.println(mapHabitaciones.get(clave));
        }
    }

    public void mostrarConsumibles() {
        for (Consumible consumible : listaConsumibles) {
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

    public void escribirArchivoMap(File archivo, HashMap<K,T> mapa){
        try{
            if(archivo!=null) {
                this.mapper.writeValue(archivo, mapa);
            }else {
                throw new IOException();
            }
        }catch (IOException e){
            System.out.println("ERROR al escribir el archivo.");
        }

    }

    public HashMap<K,T> leerArchivoMap(File archivo){
        HashMap<K,T> mapa = new HashMap<K,T>();
        try{
            if(archivo!=null) {
                mapa = this.mapper.readValue(archivo, mapa.getClass());
            }else {
                throw new IOException();
            }
        }catch (IOException e){
            System.out.println("ERROR al leer el archivo.");
        }
        return mapa;
    }
}


