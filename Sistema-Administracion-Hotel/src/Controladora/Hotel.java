package Controladora;

import Contable.Factura;
import Contable.Reserva;
import Habitaciones.Comun;
import Habitaciones.Habitacion;
import Habitaciones.Suite;
import Personas.Empleado;
import Personas.Pasajero;
import Personas.Recepcionista;
import Servicios.Cochera;
import Servicios.Consumible;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.Main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Hotel<K, T> {

    private String serviciosIncluidos;
    public String nombre;
    public String direccion;
    public String ciudad;
    public int cantidadEstrellas;

    private double dineroTotal;
    private Cochera cochera;
    public ArrayList<Pasajero> listaPasajeros = new ArrayList<>();
    public TreeMap<String, Habitacion> mapHabitaciones = new TreeMap<>();
    public TreeMap<String, Factura> mapFacturas = new TreeMap<>();
    public ArrayList<Consumible> listaConsumibles = new ArrayList<>();
    public TreeMap<String, Empleado> mapEmpleados = new TreeMap<>();//clave=DNI
    public TreeMap<String, Reserva> mapReservas = new TreeMap<>();

    static final String ruta = "C:\\Tp final labo III\\ProyectoFinalLaboratorio3\\Sistema-Administracion-Hotel\\src\\Files\\";
    public File archivoHotel = new File(ruta + "Hotel.json");//SOLO GUARDA EL HOTEL
    public File archivoHabitaciones = new File(ruta + "Habitaciones.json");
    public File archivoFacturas = new File(ruta + "Facturas.json");
    public File archivoEmpleados = new File(ruta + "Empleados.json");
    public File archivoReservas = new File(ruta + "Reservas.json");
    public File archivoPasajeros = new File(ruta + "Pasajeros.json");
    public File archivoConsumibles = new File(ruta + "Consumibles.json");

    ObjectMapper mapper = new ObjectMapper();
    Scanner teclado = new Scanner(System.in);


    public Hotel() {
        this.nombre = "Lester Hotel";
        this.direccion = "Garay 074";
        this.ciudad = "Mar del Plata";
        this.cantidadEstrellas = 4;
        this.cochera = new Cochera(30, 30, 3000);
        this.serviciosIncluidos = "Cochera, Caja fuerte, Wi-Fi";
    }

    public double getDineroTotal() {
        return dineroTotal;
    }

    public Cochera getCochera() {
        return cochera;
    }

    public void login() {

    }

    public void menuRecepcionista(Recepcionista recepcionista) {
        System.out.println("Bienvenido " + recepcionista.getNombre() + " " + recepcionista.getApellido());
        int opcion = 0;
        opcionesRecepcionista();
        do {

            System.out.println("Que opcion desea realizar? ");
            opcion = teclado.nextInt();
            switch (opcion) {
                case 1:
                    clearScreen();
                    checkInRecepcionista(recepcionista);
                    break;
                case 2:
                    clearScreen();
                    recepcionista.informarCheckOut(listaPasajeros, mapReservas, mapHabitaciones, cochera);
                    break;
                case 3:
                    clearScreen();
                    recepcionista.informarCantHabitaciones();
                    break;
                case 4:
                    clearScreen();
                    recepcionista.verOcupaciones();
                    break;
                case 5:
                    clearScreen();
                    recepcionista.verDesocupadas();
                    break;
                case 6:
                    clearScreen();
                    opcionesRecepcionista();
                    break;
                case 7:
                    System.out.println("Hasta luego"); //Volver al login
                    clearScreen();
                    break;
                default:
                    System.out.println("Error, opcion no valida");
                    break;
            }
        } while (opcion != 7);
        menuPrincipal();
    }

    private void opcionesRecepcionista() {
        System.out.println("1: Check In");
        System.out.println("2: Check Out");
        System.out.println("3: Cantidad de habitaciones totales");
        System.out.println("4: Ver habitaciones ocupadas");
        System.out.println("5: Ver habitaciones desocupadas");
        System.out.println("6: Ver opciones");
        System.out.println("7: Salir");

    }

    private void menuAdministrador() {
    }

    public void menuPrincipal() {
        System.out.println("Bienvenido a Lester Hotel");
        int opcion = 0;
        opcionesCliente();
        do {
            System.out.println("Que desea realizar? ");
            opcion = teclado.nextInt();
            switch (opcion) {
                case 1:
                    realizarReserva();
                    break;
                case 2:
                    cancelarReserva();
                    break;
                case 3:
                    System.out.println(this); //Arreglar toString
                    break;
                case 4:
                    //realizar consumo = verificar que tenga una reserva (no todos pueden pedir)
                case 5:
                    login();
                    break;
                case 6:
                    opcionesCliente();
                    break;
                case 7:
                    System.out.println("Hasta luego");
            }
        } while (opcion != 7);
    }

    private void opcionesCliente() {
        System.out.println("1: Realizar reserva");
        System.out.println("2: Cancelar reserva");
        System.out.println("3: Ver datos de Lester");
        System.out.println("4: Realizar consumo");
        System.out.println("5: Login");
        System.out.println("6: Ver opciones");
        System.out.println("7: Salir");
    }

    private void registrarUsuario() {

    }

    private boolean verificarNombre() {
        return false;
    }

    private boolean verificarContrasena() {
        return false;
    }

    public void checkInRecepcionista(Recepcionista recepcionista) {
        if (recepcionista.informarCheckIn(mapReservas) == true) {
            realizarReserva();
        }
    }

    public void realizarReserva() {
        teclado.useDelimiter("\n");
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
        LocalDateTime fechaEntrada = LocalDateTime.of(anio, mes, dia, 18, 0);
        reserva.fechaEntrada = fechaEntrada;
        System.out.println("Ingrese la fecha de salida(Dia/Mes/Anio): ");
        System.out.println("Ingrese dia:");
        int diaSalida = teclado.nextInt();
        System.out.println("Ingrese mes:");
        int mesSalida = teclado.nextInt();
        System.out.println("Ingrese anio:");
        int anioSalida = teclado.nextInt();
        LocalDateTime fechaSalida = LocalDateTime.of(anioSalida, mesSalida, diaSalida, 10, 0);
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
        generarFactura(factura, reserva);
        setearDias(reserva);
        dineroTotal += factura.getPrecioTotal();
        mapFacturas.put(factura.getCodigoIdentificador(), factura);
        System.out.println("Felicitaciones ya realizaste tu reserva en Lester Hotel. Te esperamos pronto");
        System.out.println(factura);

        teclado.close();

    }

    public void cancelarReserva() {
        teclado.useDelimiter("\n");
        char seguir;
        double descontar;
        System.out.println("Ingrese su numero de reserva.");
        String numeroR = teclado.next();
        long dias = ChronoUnit.DAYS.between(LocalDateTime.now(), mapReservas.get(numeroR).fechaEntrada);
        if (mapReservas.containsKey(numeroR)) {
            if (dias < 3) {
                System.out.println("Su plazo para cancelar se ha extendido, y no tendra devolucion de dinero.");
                System.out.println("Esta seguro que quiere continuar? s/n");
                seguir = teclado.next().charAt(0);
                teclado.nextLine();
                if (seguir == 's') {
                    mapReservas.remove(numeroR);
                    mapFacturas.remove(numeroR);
                    System.out.println("Su reserva ha sido cancelada.");
                } else {
                    //AGREGAR LLAMADO MENU.
                }
            } else if (dias >= 7) {
                descontar = (75 * mapFacturas.get(numeroR).getPrecioTotal()) / 100;
                dineroTotal -= descontar;
                System.out.println("Se le devolvera el 75% del dinero.");
                System.out.println("Esta seguro que quiere continuar? s/n");
                seguir = teclado.next().charAt(0);
                teclado.nextLine();
                if (seguir == 's') {
                    mapReservas.remove(numeroR);
                    mapFacturas.remove(numeroR);
                    System.out.println("Su reserva ha sido cancelada.");
                } else {
                    //AGREGAR LLAMADO MENU.
                }
            } else {
                descontar = (50 * mapFacturas.get(numeroR).getPrecioTotal()) / 100;
                dineroTotal -= descontar;
                System.out.println("Se le devolvera el 50% del dinero.");
                System.out.println("Esta seguro que quiere continuar? s/n");
                seguir = teclado.next().charAt(0);
                teclado.nextLine();
                if (seguir == 's') {
                    mapReservas.remove(numeroR);
                    mapFacturas.remove(numeroR);
                    System.out.println("Su reserva ha sido cancelada.");
                } else {
                    //AGREGAR LLAMADO MENU.
                }
            }
        } else {
            System.out.println("Esa reserva no se encuentra.");
        }
    }

    private void generarFactura(Factura factura, Reserva reserva) {
        factura.setCodigoIdentificador(reserva.getIdentificador());
        factura.calcularPrecio(calcularDias(reserva), reserva, cochera.precioDia);
        factura.habitaciones = reserva.habitaciones;
        factura.setPasajero(reserva.getPasajeros().get(0));
        LocalDateTime ahora = LocalDateTime.now();
        factura.setFechaDeEmision(ahora);
        if (mapFacturas != null)
            for (String clave : mapFacturas.keySet()) {
                try {
                    if (factura.getCodigoIdentificador().equals(mapFacturas.get(clave).getCodigoIdentificador())) {
                        throw new IOException();
                    }
                } catch (IOException e) {
                    System.out.println("Esa factura ya se encuentra en la lista,revise correctamente los datos");
                }
            }
    }

    private void setearDias(Reserva reserva) {
        for (Pasajero pasajero : reserva.pasajeros) {
            pasajero.setCantDias(calcularDias(reserva));
        }
        for (Pasajero pasajero : listaPasajeros) {
            pasajero.setCantDias(calcularDias(reserva));
        }
    }


    private int calcularDias(Reserva reserva) {
        long dias = ChronoUnit.DAYS.between(reserva.fechaEntrada, reserva.fechaSalida);
        return (int) dias;
    }

    private ArrayList<Pasajero> registrarPasajero() {
        ArrayList<Pasajero> pasajeros = new ArrayList<>();
        boolean excepcionLanzada = false;
        char control;
        do {
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
                teclado.nextLine();
                System.out.printf("\nHistoria (Opcional): ");
                nuevo.setHistoria(teclado.next());
            } while (excepcionLanzada);

            pasajeros.add(nuevo);
            listaPasajeros.add(nuevo);
            System.out.printf("\nQuiere registrar a otro pasajero? s/n: ");
            teclado.nextLine();
            control = teclado.next().charAt(0);
        } while (control == 's');
        teclado.nextLine();
        return pasajeros;
    }

    private void registrarCochera(Reserva reserva) {
        int espacios;
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
        char continuar;
        String numero;
        boolean ocupacion;
        do {
            for (String number : mapHabitaciones.keySet()) {
                System.out.println(mapHabitaciones.get(number));
            }
            do {
                System.out.println("Ingrese el numero de habitacion que quiere reservar");
                teclado.nextLine();
                numero = teclado.next();
                ocupacion = verificarOcupacion(numero, fechaEntrada);
                if (ocupacion) {
                    System.out.println("Gran eleccion!!");
                    for (String h : mapHabitaciones.keySet()) {
                        if (h.equals(numero)) {
                            mapHabitaciones.get(h).setOcupada(true);
                            reserva.habitaciones.add(mapHabitaciones.get(h));
                        }
                    }
                } else {
                    System.out.println("La habitacion que selecciono se encuentra reservada hasta una fecha posterior a su fecha de entrada. Porfavor reintente en otra fecha o otra habitacion. Disculpe las molestias");
                }
            } while (!ocupacion);
            System.out.println("Quiere reservar otra habitacion mas?");
            continuar = teclado.next().charAt(0);
        } while (continuar == 's');
    }

    private boolean verificarOcupacion(String habitacion, LocalDateTime fechaEntrada) {
        try {
            for (String reserva1 : mapReservas.keySet()) {
                for (int i = 0; i < mapReservas.get(reserva1).habitaciones.size(); i++) {
                    if (mapReservas.get(reserva1).habitaciones.get(i).numero.equals(habitacion)) {
                        if (mapReservas.get(reserva1).fechaSalida.compareTo(fechaEntrada) >= 0) {
                            return false;  // La habitación está ocupada durante la fecha de entrada
                        }
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;  // La habitación está disponible
    }

    public void mostrarReservas() {
        for (String clave : mapReservas.keySet()) {
            System.out.println(mapReservas.get(clave));
        }
    }

    public void mostrarFactura() {
        for (String clave : mapFacturas.keySet()) {
            System.out.println(mapFacturas.get(clave));
        }
    }

    public void mostrarPasajeros() {
        for (Pasajero pasajero : listaPasajeros) {
            System.out.println(pasajero);
        }
    }


/*
    public void cargarHabitaciones() {
        Comun comun1 = new Comun("101", 4, false, 30000);
        Comun comun2 = new Comun("102", 2, true, 16000);
        Comun comun3 = new Comun("103", 3, false, 22000);
        Comun comun4 = new Comun("104", 2, true, 16000);
        Comun comun5 = new Comun("105", 2, false, 16000);
        Comun comun6 = new Comun("201", 2, false, 16000);
        Comun comun7 = new Comun("202", 3, true, 22000);
        Comun comun8 = new Comun("203", 4, false, 30000);
        Comun comun9 = new Comun("204", 4, true, 30000);
        Comun comun10 = new Comun("205", 2, false, 16000);
        Comun comun11 = new Comun("301", 3, false, 22000);
        Comun comun12 = new Comun("302", 3, true, 22000);
        Comun comun13 = new Comun("303", 2, false, 16000);
        Comun comun14 = new Comun("304", 2, true, 16000);
        Comun comun15 = new Comun("305", 4, false, 30000);
        Suite suite1 = new Suite("401", 5, true, 100000);
        Suite suite2 = new Suite("402", 5, true, 100000);
        Suite suite3 = new Suite("500", 6, true, 150000);

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

        this.escribirArchivoMap(this.archivoHabitaciones, (TreeMap<K, T>) this.mapHabitaciones);

    }

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

        this.escribirArchivoArrayList(archivoConsumibles, (ArrayList<T>) listaConsumibles);

    }

*/

    public void mostrarHabitaciones() {
        for (String clave : mapHabitaciones.keySet()) {
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
        return "\nHotel{" +
                "\nnombre='" + nombre + '\'' +
                "\n, direccion='" + direccion + '\'' +
                "\n, ciudad='" + ciudad + '\'' +
                "\n, cantidadEstrellas=" + cantidadEstrellas +
                "\n, cochera=" + cochera +
                "------------------------------------------------";
    }

    public void escribirArchivoMap(File archivo, TreeMap<K, T> mapa) {
        try {
            mapper.activateDefaultTyping(
                    mapper.getPolymorphicTypeValidator(),
                    ObjectMapper.DefaultTyping.NON_FINAL,
                    JsonTypeInfo.As.PROPERTY
            );
            this.mapper.writeValue(archivo, mapa);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR al escribir el archivo.");
        }

    }

    public TreeMap<K, T> leerArchivoMap(File archivo, Class<K> clave, Class<T> valor) {
        TreeMap<K, T> mapa = new TreeMap<>();
        try {
            if (archivo != null) {
                JavaType mapType = mapper.getTypeFactory().constructParametricType(TreeMap.class, clave, valor);
                mapper.activateDefaultTyping(
                        mapper.getPolymorphicTypeValidator(),
                        ObjectMapper.DefaultTyping.NON_FINAL,
                        JsonTypeInfo.As.PROPERTY
                );
                mapa = mapper.readValue(archivo, mapType);
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR al leer el archivo.");
        }
        return mapa;
    }

    public void escribirArchivoArrayList(File archivo, ArrayList<T> arrayList) {
        try {
            this.mapper.writeValue(archivo, arrayList);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR al escribir el archivo.");
        }

    }

    public ArrayList<T> leerArchivoArrayList(File archivo, Class<T> clazz) {
        ArrayList<T> arrayList = new ArrayList<>();
        try {
            if (archivo != null) {
                arrayList = mapper.readValue(archivo, mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz));
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR al leer el archivo.");
        }
        return arrayList;
    }

    public void escribirAuxiliar(File archivo) {
        Auxiliar auxiliar = new Auxiliar(dineroTotal, cochera);
        try {
            mapper.writeValue(archivo, auxiliar);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al escribir el archivo auxiliar");
        }
    }

    public void leerAuxiliar(File archivo) {

        Auxiliar auxiliar = new Auxiliar();
        try {
            if (archivo != null) {
                auxiliar = mapper.readValue(archivo, Auxiliar.class);
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al leer el archivo auxiliar");
        }
        dineroTotal = auxiliar.getDineroTotal();
        cochera = auxiliar.getCochera();
    }

    public void escribirTodosArchivos() {
        escribirArchivoArrayList(archivoPasajeros, (ArrayList<T>) listaPasajeros);
        escribirArchivoMap(archivoReservas, (TreeMap<K, T>) mapReservas);
        escribirArchivoMap(archivoFacturas, (TreeMap<K, T>) mapFacturas);
        escribirAuxiliar(archivoHotel);
        escribirArchivoMap(archivoHabitaciones, (TreeMap<K, T>) mapHabitaciones);
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}




