package Controladora;

import Contable.Factura;
import Contable.Reserva;
import Habitaciones.Comun;
import Habitaciones.Habitacion;
import Habitaciones.Suite;
import Personas.*;
import Servicios.Cochera;
import Servicios.Consumible;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sun.tools.javac.Main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Hotel<K, T> {
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime fechaBackUp;
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
    public ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    public TreeMap<String, Reserva> mapReservas = new TreeMap<>();

    static final String ruta = "C:\\Users\\Escobar\\IdeaProjects\\ProyectoFinalLaboratorio3\\ProyectoFinalLaboratorio3\\Sistema-Administracion-Hotel\\src\\Files\\";

    ObjectMapper mapper = new ObjectMapper();
    Scanner teclado = new Scanner(System.in);


    public Hotel() {
        this.fechaBackUp = LocalDateTime.now();
        this.nombre = "Lester Hotel";
        this.direccion = "Garay 074";
        this.ciudad = "Mar del Plata";
        this.cantidadEstrellas = 4;
        this.cochera = new Cochera(30, 30);
        this.serviciosIncluidos = "Cochera, Caja fuerte, Wi-Fi";
        this.dineroTotal = 1000000;
    }

    public double getDineroTotal() {
        return dineroTotal;
    }

    public Cochera getCochera() {
        return cochera;
    }

    public LocalDateTime getFechaBackUp() {
        return fechaBackUp;
    }

    public void setFechaBackUp(LocalDateTime fechaBackUp) {
        this.fechaBackUp = fechaBackUp;
    }

    public void menuPrincipal() {
        teclado.useDelimiter("\n");
        System.out.println("Bienvenido a Lester Hotel");
        System.out.println("1: INGRESAR");
        System.out.println("2: REGISTRARSE");
        System.out.println("3: EXIT");
        System.out.println("Que desea realizar? ");
        int principio = 0;
        principio = teclado.nextInt();
        teclado.nextLine();
        Usuario user = new Usuario();
        switch (principio) {
            case 1:
                user = login();
                break;
            case 2:
                user = registrarUsuario();
                break;
            case 3:
                System.out.println("Vuelva pronto");
                escribirTodosArchivos();
                System.exit(0);
        }
        try {
            if (user.getRol().equals(Rol.ROL_USER)) {
                menuPasajero(user);
            } else if (user.getRol().equals(Rol.ROL_EMPLEADO)) {
                if (user.getPersona() instanceof Recepcionista) {
                    menuRecepcionista(user);
                }
            } else if (user.getRol().equals(Rol.ROL_ADMIN)) {
                menuAdministrador(user);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            System.out.println("El usuario no se encuentra logueado");
        }
        teclado.close();
    }

    public void menuRecepcionista(Usuario user) {
        Recepcionista recepcionista = (Recepcionista) user.getPersona();
        System.out.println("Bienvenido " + recepcionista.getNombre() + " " + recepcionista.getApellido());
        int opcion = 0;
        do {
            opcionesRecepcionista();
            System.out.println("Que opcion desea realizar? ");
            teclado.nextLine();
            opcion = teclado.nextInt();
            switch (opcion) {
                case 1:
                    checkInRecepcionista(user);
                    break;
                case 2:
                    recepcionista.informarCheckOut(listaPasajeros, mapReservas, mapHabitaciones, cochera);
                    break;
                case 3:
                    recepcionista.informarCantHabitaciones(mapHabitaciones.size());
                    break;
                case 4:
                    recepcionista.verOcupaciones(mapHabitaciones);
                    break;
                case 5:
                    recepcionista.verDesocupadas(mapHabitaciones);
                    break;
                case 6:
                    System.out.println("Ingrese numero de habitacion");
                    recepcionista.buscarHabitacion(mapHabitaciones, teclado.next());
                    break;
                case 7:
                    verUsuario(user);
                case 8:
                    opcionesRecepcionista();
                    break;
                case 9:
                    System.out.println("Hasta luego"); //Volver al login
                    break;
                default:
                    System.out.println("Error, opcion no valida");
                    break;
            }
        } while (opcion != 9);
        menuPrincipal();
    }

    private void opcionesRecepcionista() {
        System.out.println("1: Check In");
        System.out.println("2: Check Out");
        System.out.println("3: Cantidad de habitaciones totales");
        System.out.println("4: Ver habitaciones ocupadas");
        System.out.println("5: Ver habitaciones desocupadas");
        System.out.println("6: Ver habitacion especifica");
        System.out.println("7: Ver usuario");
        System.out.println("8: Ver opciones");
        System.out.println("9: Salir");

    }

    private void menuAdministrador(Usuario user) {
        Administrador administrador = (Administrador) user.getPersona();
        System.out.println("Bienvenido " + administrador.getNombre() + " " + administrador.getApellido());
        int opcion = 0;
        do {
            opcionesAdministrador();
            System.out.println("Que opcion desea realizar? ");
            teclado.nextLine();
            opcion = teclado.nextInt();
            switch (opcion) {
                case 1:
                    Usuario empleado = administrador.crearUsuarioEmpleado(listaUsuarios, mapEmpleados);
                    listaUsuarios.add(empleado);
                    mapEmpleados.put(empleado.getNombreDeUsuario(), (Empleado) empleado.getPersona());
                    escribirArchivoMap("Empleados.json", (TreeMap<K, T>) mapEmpleados);
                    escribirArchivoArrayList("Usuarios.json", (ArrayList<T>) listaUsuarios);
                    break;
                case 2:
                    administrador.eliminarUsuario(listaUsuarios, mapEmpleados);
                    escribirArchivoMap("Empleados.json", (TreeMap<K, T>) mapEmpleados);
                    escribirArchivoArrayList("Usuarios.json", (ArrayList<T>) listaUsuarios);
                    break;
                case 3:
                    administrador.darPermisos(mapEmpleados);
                    escribirArchivoMap("Empleados.json", (TreeMap<K, T>) mapEmpleados);
                    break;
                case 4:
                    administrador.agregarConsumibles(listaConsumibles);
                    escribirArchivoArrayList("Consumibles.json", (ArrayList<T>) listaConsumibles);
                    break;
                case 5:
                    administrador.generarBackUp(this, this.ruta);
                    break;
                case 6:
                    administrador.fichaje();
                    break;
                case 7:
                    administrador.desFichaje();
                    break;
                case 8:
                    System.out.println(administrador);
                    break;
                case 9:
                    System.out.println("Hasta luego " + user.getPersona().getNombre());
                    break;
                default:
                    System.out.println("Error, opcion no valida");
                    break;
            }
        } while (opcion != 9);

        menuPrincipal();
    }

    private void opcionesAdministrador() {
        System.out.println("1- Crear Usuario");
        System.out.println("2- Eliminar Usuario");
        System.out.println("3- Dar Permisos");
        System.out.println("4- Agregar Consumibles");
        System.out.println("5- Generar BackUp");
        System.out.println("6- Fichar");
        System.out.println("7- Desfichar");
        System.out.println("8- Ver datos");
        System.out.println("9- Exit");
    }

    private void menuPasajero(Usuario usuario) {
        Pasajero pasajero = (Pasajero) usuario.getPersona();
        boolean checkOut;
        boolean checkIn;
        int opcion = 0;
        do {
            opcionesPasajero();
            System.out.println("Que desea realizar? ");
            teclado.nextLine();
            opcion = teclado.nextInt();
            switch (opcion) {
                case 1:
                    realizarReserva(usuario);
                    break;
                case 2:
                    cancelarReserva(usuario);
                    break;
                case 3:
                    verUsuario(usuario);
                    break;
                case 4:
                    System.out.println(this); //Arreglar toString
                    break;
                case 5:
                    dineroTotal += pasajero.pedirConsumible(listaConsumibles, mapReservas);
                    break;
                case 6:
                    lateCheckOut(usuario);
                    break;
                case 7:
                    earlyCheckIn(usuario);
                    break;
                case 8:
                    verMisReservas(usuario);
                    break;
                case 9:
                    verMisFacturas(usuario);
                    break;
                case 10:
                    modificarUsuario(usuario);
                    break;
                case 11:
                    eliminarUsuario(usuario);
                    menuPrincipal();
                    break;
                case 12:
                    System.out.println("Hasta luego");
                    break;
            }
        } while (opcion != 12);
        menuPrincipal();
    }

    private void opcionesPasajero() {
        System.out.println("1: Realizar reserva");
        System.out.println("2: Cancelar reserva");
        System.out.println("3: Ver usuario");
        System.out.println("4: Ver datos de Lester");
        System.out.println("5: Realizar consumo");
        System.out.println("6: Late Check out");
        System.out.println("7: Early Check in");
        System.out.println("8: Ver mis reservas");
        System.out.println("9: Ver mis facturas");
        System.out.println("10: Modificar usuario");
        System.out.println("11: Eliminar usuario");
        System.out.println("12: Salir");
    }

    public void lateCheckOut(Usuario user) {

        teclado.nextLine();
        String codigo;
        int horas;
        System.out.println("Ingrese su codigo de reserva");
        codigo = teclado.next();
        Reserva reserva = null;

        try {
            if (mapReservas.containsKey(codigo)) {
                reserva = mapReservas.get(codigo);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            System.out.println("Ese codigo de reserva no es valido");
            menuPasajero(user);
        }
        if(!reserva.isLateCheckOut()) {
            System.out.println("Ingrese cuantos horas extras desea quedarse. Tenga en cuenta que se le cobrara medio dia hasta las 2 horas y mas de eso se le cobra el dia completo. Solamente se puede solicitar una vez");
            horas = teclado.nextInt();
            LocalDateTime horasAgregadas;
            horasAgregadas = reserva.fechaSalida.plusHours(horas);
            reserva.fechaSalida = horasAgregadas;
            if (horas <= 2 && horas > 0) {
                for (Habitacion habitacion : reserva.habitaciones) {
                    mapFacturas.get(codigo).setPrecioTotal(mapFacturas.get(codigo).getPrecioTotal() + (habitacion.getPrecio() / 2));
                }
            } else if (horas > 2) {
                for (Habitacion habitacion : reserva.habitaciones) {
                    mapFacturas.get(codigo).setPrecioTotal(mapFacturas.get(codigo).getPrecioTotal() + habitacion.getPrecio());
                }
            } else {
                System.out.println("Esa cantidad no es valida");
            }
            System.out.println("Cambio de hora realizado");
            escribirArchivoMap("Reservas.json", (TreeMap<K, T>) mapReservas);
            escribirArchivoMap("Facturas.json", (TreeMap<K, T>) mapFacturas);
            reserva.setLateCheckOut(true);
        }else{
            System.out.println("En esta reserva ya se ha hecho un late CheckOut");

        }


    }

    public void earlyCheckIn(Usuario user) {
        teclado.nextLine();
        String codigo;
        int horas;
        System.out.println("Ingrese su codigo de reserva");
        codigo = teclado.next();
        Reserva reserva = null;
        try {
            if (mapReservas.containsKey(codigo)) {
                reserva = mapReservas.get(codigo);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            System.out.println("Ese codigo de reserva no es valido");
            menuPasajero(user);
        }
        if(!reserva.isEarlyCheckIn()) {
            System.out.println("Ingrese cuantos horas antes desea ingresar. Nuestro hotel dispone de un Early Check in de maximo 4 horas. Solamente se puede solicitar una vez");
            horas = teclado.nextInt();
            if (horas > 4) {
                System.out.println("Maximo de horas permitido superado");
                menuPasajero(user);
            }
            LocalDateTime horasRestadas;
            horasRestadas = reserva.fechaEntrada.minusHours(horas);
            reserva.fechaEntrada = horasRestadas;
            System.out.println("Cambio de hora realizado");
            escribirArchivoMap("Reservas.json", (TreeMap<K, T>) mapReservas);
            escribirArchivoMap("Facturas.json", (TreeMap<K, T>) mapFacturas);
            reserva.setEarlyCheckIn(true);
        }else {
            System.out.println("En esta reserva ya se ha realizado un early CheckIn");
        }
    }

    public void verMisReservas(Usuario user) {
        for (String clave : mapReservas.keySet()) {
            for (Pasajero pasajero : mapReservas.get(clave).pasajeros) {
                if (user.getPersona() instanceof Pasajero) {
                    if (pasajero.equals(user.getPersona())) {
                        System.out.println(mapReservas.get(clave));
                    }
                }
            }
        }
    }

    public void verMisFacturas(Usuario user) {
        for (String clave : mapFacturas.keySet()) {
            if (user.getPersona() instanceof Pasajero) {
                if (mapFacturas.get(clave).getPasajero().equals(user.getPersona())) {
                    System.out.println(mapFacturas.get(clave));
                }
            }
        }
    }

    private void verUsuario(Usuario usuario) {
        System.out.println(usuario);
    }

    private Usuario registrarUsuario() {
        teclado.nextLine();
        Usuario user = new Usuario();
        boolean verif = true;
        while (verif) {
            System.out.printf("Ingrese un nombre de usuario: ");
            user.setNombreDeUsuario(teclado.nextLine());
            verif = verificarNombre(user.getNombreDeUsuario());
        }
        System.out.printf("\nIngrese su contraseña: ");
        user.setContraseña(teclado.nextLine());
        user.setRol(Rol.ROL_USER);
        user.setPersona(registrarPasajero());
        listaUsuarios.add(user);
        escribirArchivoArrayList("Usuarios.json", (ArrayList<T>) listaUsuarios);
        return user;
    }

    public Usuario login() {
        Usuario user;
        do {
            System.out.printf("Ingrese el nombre de usuario: ");
            String aBuscar = teclado.next();
            user = buscarPorNombre(aBuscar);
        } while (user == null);
        boolean verif = false;
        int i = 3;
        teclado.nextLine();
        while (!verif && i > 0) {
            System.out.println("Hola, " + user.getNombreDeUsuario());
            System.out.printf("Ingrese la contraseña: ");
            String contra = teclado.next();
            verif = verificarContrasena(user, contra, i);
            if (verif) {
                return user;
            }
            i--;
        }
        return null;
    }


    private boolean verificarNombre(String nombreDeUsuario) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNombreDeUsuario().equals(nombreDeUsuario)) {
                System.out.println("Ese nombre de usuario ya se encuentra registrado.");
                return true;
            }
        }
        return false;
    }

    private Usuario buscarPorNombre(String nombreDeUsuario) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNombreDeUsuario().equals(nombreDeUsuario)) {
                return usuario;
            }
        }
        System.out.println("Nombre de usuario incorrecto");
        return null;
    }

    private boolean verificarContrasena(Usuario user, String contra, int intentos) {
        if (user.getContraseña().equals(contra)) {
            return true;
        }
        System.out.println("La contraseña ingresada es incorrecta. Le quedan " + (intentos - 1) + " intentos.");
        return false;
    }

    public void modificarUsuario(Usuario user) {
        int opcion = 0;
        char continuar = 's';
        teclado.nextLine();
        do {
            menuModificacion();
            System.out.println("Que quiere modificar?");
            opcion = teclado.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese su nuevo nombre de usuario");
                    user.setNombreDeUsuario(teclado.next());
                    break;
                case 2:
                    System.out.println("Ingrese su nueva contrasenia");
                    user.setContraseña(teclado.next());
                    break;
                case 3:
                    System.out.println("Ingrese su nuevo nombre");
                    user.getPersona().setNombre(teclado.next());
                    break;
                case 4:
                    System.out.println("Ingrese su nuevo apellido");
                    user.getPersona().setApellido(teclado.next());
                    break;
                case 5:
                    if (user.getPersona() instanceof Pasajero) {
                        System.out.println("Ingrese su nueva direccion");
                        ((Pasajero) user.getPersona()).setDomicilioOrigen(teclado.next());
                    } else {
                        System.out.println("No tenes registrado direccion");
                    }
                    break;
                case 6:
                    if (user.getPersona() instanceof Pasajero) {
                        System.out.println("Ingrese su nuevo pais");
                        ((Pasajero) user.getPersona()).setOrigen(teclado.next());
                    } else {
                        System.out.println("No tenes registrado pais");
                    }
                    break;
                case 7:
                    if (user.getPersona() instanceof Pasajero) {
                        System.out.println("Ingrese su historia");
                        ((Pasajero) user.getPersona()).setHistoria(teclado.next());
                    } else {
                        System.out.println("No tenes registrado una historia");
                    }
                    break;
                default:
                    System.out.println("Esa opcion es incorrecta");
                    break;
            }
            System.out.println("Desea modificar algún otro aspecto de usuario?");
            continuar = teclado.next().charAt(0);
        } while (continuar == 's');
        escribirArchivoArrayList("Pasajeros.json", (ArrayList<T>) listaPasajeros);
        escribirArchivoArrayList("Usuarios.json", (ArrayList<T>) listaUsuarios);
    }

    private void menuModificacion() {
        System.out.println("Modificacion de usuario");
        System.out.println("1-Nombre de usuario");
        System.out.println("2-Contrasenia");
        System.out.println("3-Nombre");
        System.out.println("4-Apellido");
        System.out.println("5-Direccion");
        System.out.println("6-Ciudad");
        System.out.println("7-Historia");
    }

    public void eliminarUsuario(Usuario user) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.equals(user)) {
                listaUsuarios.remove(user);
                listaPasajeros.remove((Pasajero) user.getPersona());
            }
        }
        escribirArchivoArrayList("Usuarios.json", (ArrayList<T>) listaUsuarios);
    }

    public void checkInRecepcionista(Usuario user) {
        Recepcionista recepcionista = (Recepcionista) user.getPersona();
        if (recepcionista.informarCheckIn(mapReservas) == true) {
            realizarReserva(user);
        }
    }

    public void realizarReserva(Usuario user) {
        Reserva reserva = new Reserva();
        teclado.nextLine();
        if(user.getPersona() instanceof Pasajero) {
            System.out.println("Bienvenido/a al sistema de reservas de habitaciones del hotel Lester, a continuacion le solicitaremos los datos de los acompañantes");
        }else if (user.getPersona() instanceof Recepcionista){
            System.out.println("Bienvenido/a al sistema de reservas de habitaciones del hotel Lester, a continuacion le solicitaremos los datos de los pasajeros");
        }
        reserva.pasajeros = registrarPasajeros(user);
        LocalDateTime fechaEntrada;
        do {
            System.out.println("Ingrese la fecha de entrada(Dia/Mes/Año): ");
            System.out.println("Ingrese dia:");
            int dia = teclado.nextInt();
            System.out.println("Ingrese mes:");
            int mes = teclado.nextInt();
            System.out.println("Ingrese año:");
            int anio = teclado.nextInt();
            fechaEntrada = LocalDateTime.of(anio, mes, dia, 18, 0);
            if (fechaEntrada.compareTo(LocalDateTime.now()) <= 0) {
                System.out.println("La fecha de entrada ingresada es menor a la fecha actual, porfavor reintente nuevamente");
            }
        } while (fechaEntrada.compareTo(LocalDateTime.now()) <= 0);
        reserva.fechaEntrada = fechaEntrada;
        LocalDateTime fechaSalida;
        do {
            System.out.println("Ingrese la fecha de salida(Dia/Mes/Año): ");
            System.out.println("Ingrese dia:");
            int diaSalida = teclado.nextInt();
            System.out.println("Ingrese mes:");
            int mesSalida = teclado.nextInt();
            System.out.println("Ingrese año:");
            int anioSalida = teclado.nextInt();
            fechaSalida = LocalDateTime.of(anioSalida, mesSalida, diaSalida, 10, 0);
            if (fechaSalida.compareTo(fechaEntrada) < 0) {
                System.out.println("La fecha de salida es menor a la fecha de entrada");
            }
        } while (fechaSalida.compareTo(fechaEntrada) < 0);
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
        escribirArchivoMap("Reservas.json", (TreeMap<K, T>) mapReservas);
        escribirArchivoMap("Facturas.json", (TreeMap<K, T>) mapFacturas);
        escribirArchivoMap("Habitaciones.json", (TreeMap<K, T>) mapHabitaciones);
        escribirAuxiliar("Hotel.json");
    }

    public void cancelarReserva(Usuario user) {
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
                    for (Habitacion habitacion : mapReservas.get(numeroR).habitaciones) {
                        mapHabitaciones.get(habitacion.getNumero()).setOcupada(false);
                    }
                    System.out.println("Su reserva ha sido cancelada.");
                } else {
                    menuPasajero(user);
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
                    menuPasajero(user);
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
                    menuPasajero(user);
                }
            }
        } else {
            System.out.println("Esa reserva no se encuentra.");
        }
        escribirArchivoMap("Reservas.json", (TreeMap<K, T>) mapReservas);
        escribirArchivoMap("Habitaciones.json", (TreeMap<K, T>) mapHabitaciones);
        escribirArchivoMap("Facturas.json", (TreeMap<K, T>) mapFacturas);
    }

    private void generarFactura(Factura factura, Reserva reserva) {
        factura.setCodigoIdentificador(reserva.getIdentificador());
        factura.calcularPrecio(calcularDias(reserva), reserva, cochera.precioDia);
        factura.habitaciones = reserva.habitaciones;
        factura.setPasajero(reserva.getPasajeros().get(0));
        LocalDateTime ahora = LocalDateTime.now();
        factura.setFechaDeEmision(ahora);
        if (mapFacturas != null) {
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
        LocalDateTime fechaEntradaSinHora = reserva.fechaEntrada.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime fechaSalidaSinHora = reserva.fechaSalida.truncatedTo(ChronoUnit.DAYS);
        long dias = ChronoUnit.DAYS.between(fechaEntradaSinHora, fechaSalidaSinHora);
        return (int) dias;
    }

    private ArrayList<Pasajero> registrarPasajeros(Usuario user) {
        ArrayList<Pasajero> pasajeros = new ArrayList<>();
        char control = 's';
        Pasajero nuevo = new Pasajero();
        if (user.getRol() == Rol.ROL_USER) {
            nuevo = (Pasajero) user.getPersona();
        }
        pasajeros.add(nuevo);
        listaPasajeros.add(nuevo);

        while (control == 's') {
            System.out.printf("\nQuiere registrar a otro pasajero? s/n: ");
            control = teclado.next().charAt(0);
            if(control == 's') {
                nuevo = registrarPasajero();
                pasajeros.add(nuevo);
                listaPasajeros.add(nuevo);
            }
        }
        teclado.nextLine();
        escribirArchivoArrayList("Pasajeros.json", (ArrayList<T>) listaPasajeros);
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
                    System.out.println("No tenemos suficiente espacio disponible en nuestra cochera, el MÁXIMO es: " + cochera.getEspacioDisponible());
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

    private Pasajero registrarPasajero() {
        boolean excepcionLanzada = false;
        teclado.useDelimiter("\n");
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
        } while (excepcionLanzada);
        System.out.printf("\nNacionalidad: ");
        nuevo.setOrigen(teclado.next());
        System.out.printf("\nDomicilio: ");
        nuevo.setDomicilioOrigen(teclado.next());
        teclado.nextLine();
        System.out.printf("\nHistoria (Opcional): ");
        nuevo.setHistoria(teclado.next());
        return nuevo;
    }

    private void registrarHabitacion(Reserva reserva, LocalDateTime fechaEntrada) {
        char continuar;
        String numero;
        boolean ocupacion;
        do {
            mostrarHabitaciones();
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

    public void escribirArchivoMap(String archivo, TreeMap<K, T> mapa) {
        try {
            mapper.activateDefaultTyping(
                    mapper.getPolymorphicTypeValidator(),
                    ObjectMapper.DefaultTyping.NON_FINAL,
                    JsonTypeInfo.As.PROPERTY
            );
            this.mapper.writeValue(new File(ruta + archivo), mapa);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR al escribir el archivo.");
        }

    }

    public TreeMap<K, T> leerArchivoMap(String archivo, Class<K> clave, Class<T> valor) {
        TreeMap<K, T> mapa = new TreeMap<>();
        File file = new File(ruta + archivo);
        try {
            if (archivo != null) {
                JavaType mapType = mapper.getTypeFactory().constructParametricType(TreeMap.class, clave, valor);
                mapper.activateDefaultTyping(
                        mapper.getPolymorphicTypeValidator(),
                        ObjectMapper.DefaultTyping.NON_FINAL,
                        JsonTypeInfo.As.PROPERTY
                );
                mapa = mapper.readValue(file, mapType);
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR al leer el archivo.");
        }
        return mapa;
    }

    public void escribirArchivoArrayList(String archivo, ArrayList<T> arrayList) {
        try {
            mapper.activateDefaultTyping(
                    mapper.getPolymorphicTypeValidator(),
                    ObjectMapper.DefaultTyping.NON_FINAL,
                    JsonTypeInfo.As.PROPERTY);
            this.mapper.writeValue(new File(ruta + archivo), arrayList);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR al escribir el archivo.");
        }

    }

    public ArrayList<T> leerArchivoArrayList(String archivo, Class<T> clazz) {
        ArrayList<T> arrayList = new ArrayList<>();
        File file = new File(ruta + archivo);
        try {
            if (archivo != null) {
                mapper.activateDefaultTyping(
                        mapper.getPolymorphicTypeValidator(),
                        ObjectMapper.DefaultTyping.NON_FINAL,
                        JsonTypeInfo.As.PROPERTY
                );
                arrayList = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz));
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR al leer el archivo.");
        }
        return arrayList;
    }

    public void escribirAuxiliar(String archivo) {
        Auxiliar auxiliar = new Auxiliar(dineroTotal, cochera);
        try {
            mapper.writeValue(new File(ruta + archivo), auxiliar);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al escribir el archivo auxiliar");
        }
    }

    public void leerAuxiliar(String archivo) {
        Auxiliar auxiliar = new Auxiliar();
        File file = new File(ruta + archivo);
        try {
            if (archivo != null) {
                auxiliar = mapper.readValue(file, Auxiliar.class);
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
        escribirArchivoMap("Empleados.json", (TreeMap<K, T>) mapEmpleados);
        escribirArchivoMap("Reservas.json", (TreeMap<K, T>) mapReservas);
        escribirArchivoMap("Facturas.json", (TreeMap<K, T>) mapFacturas);
        escribirArchivoMap("Habitaciones.json", (TreeMap<K, T>) mapHabitaciones);
        escribirAuxiliar("Hotel.json");
        escribirArchivoArrayList("Pasajeros.json", (ArrayList<T>) listaPasajeros);
        escribirArchivoArrayList("Usuarios.json", (ArrayList<T>) listaUsuarios);
    }

}




