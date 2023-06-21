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
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalFactory;
import com.sun.tools.javac.Main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clase principal del proyecto, funciona como controladora del hotel.
 *
 * @param <K>
 * @param <T> Estos parametros permiten que la escritura y lectura de archivos a traves de un map o Arraylist, sea de forma genérica.
 */
public class Hotel<K, T> {
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime fechaBackUp;

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

    public ArrayList<String> reportes = new ArrayList<>();

    static final String ruta = "H:\\Laboratorio-3\\ProyectoFinalLabo3\\Sistema-Administracion-Hotel\\src\\Files\\";

    ObjectMapper mapper = new ObjectMapper();
    Scanner teclado = new Scanner(System.in);


    public Hotel() {
        this.fechaBackUp = LocalDateTime.now();
        this.nombre = "Lester Hotel";
        this.direccion = "Garay 074";
        this.ciudad = "Mar del Plata";
        this.cantidadEstrellas = 4;
        this.cochera = new Cochera(30, 30);
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

    /**
     * Este método es el menú al cual se entra a la página del hotel, tenes tres opciones:
     * La primera te permite iniciar sesión {@link Hotel#login()}, si esta acción fue exitosa, se retornará tu usuario a la variable local user. Sino, user pasará a ser NULL.
     * La segunda es para crearte un usuario {@link Hotel#registrarUsuario()}, si esta acción fue exitosa, se retornará tu usuario recien creado a la variable local user. Sino, user pasará a ser NULL.
     * La tercera es para salir del programa.
     * Si la creación del usuario o el iniciar sesión fue exitoso, serás redirigido hacía el menú de tu respectivo rol. {@link Hotel#menuRecepcionista(Usuario)},{@link Hotel#menuAdministrador(Usuario)},{@link Hotel#menuEmpleado(Usuario)} y {@link Hotel#menuPasajero(Usuario)}
     * Si la creación del usuario o el iniciar sesión NO fue exitoso, entraras dentro de un catch el cual te dirá que no estás logueado, ya que no se permite entrar a ningun menú con un usuario NULL.
     */
    public void menuPrincipal() {
        teclado.useDelimiter("\n");
        System.out.println("\n");
        System.out.println("Bienvenido a Lester Hotel");
        mostrarServiciosIncluidos();
        System.out.println("\n");
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
                } else if (user.getPersona() instanceof Servicio) {
                    menuEmpleado(user);
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

    /**
     * Este método es el menú de los recepcionistas del hotel, dentro de el cuentan con todas las funciones necesarias para realizar su trabajo.
     *Se selecciona la función que se quiere realizar a través de un switch case.
     *
     * {@link Hotel#opcionesRecepcionista()}
     *
     *      * 1-{@link Hotel#checkInRecepcionista(Usuario)}, 2-{@link Recepcionista#informarCheckOut(ArrayList, TreeMap, TreeMap, Cochera)}, 3-{@link Recepcionista#informarCantHabitaciones(int)},
     *      * 4-{@link Recepcionista#verOcupaciones(TreeMap)}, 5-{@link Recepcionista#verDesocupadas(TreeMap)}, 6-{@link Recepcionista#buscarHabitacion(TreeMap, String)},
     *      * 7-{@link Recepcionista#mostrarHabitacionesConProblemas(TreeMap)}, 8-{@link Recepcionista#darReporte(TreeMap, ArrayList)}, 10-{@link Hotel#mostrarReservas()},
     *      * 10-{@link Hotel#menuEmpleado(Usuario)}}
     * @param user este usuario es con el cual se ha iniciado sesión.
     */
    public void menuRecepcionista(Usuario user) {
        Recepcionista recepcionista = (Recepcionista) user.getPersona();
        System.out.println();
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
                    eventoHabNoDisponible();
                    recepcionista.mostrarHabitacionesConProblemas(mapHabitaciones);
                    break;
                case 8:
                    System.out.println("Creando reporte de todas las habitaciones con problemas");
                    recepcionista.darReporte(mapHabitaciones, reportes);
                    escribirArchivoArrayList("Reportes.json", (ArrayList<T>) reportes);
                    break;
                case 9:
                    System.out.println(reportes);
                    break;
                case 10:
                    mostrarReservas();
                    break;
                case 11:
                    menuEmpleado(user);
                    break;
                case 12:
                    System.out.println("Hasta luego"); //Volver al login
                    break;
                default:
                    System.out.println("Error, opcion no valida");
                    break;
            }
        } while (opcion != 12);
        menuPrincipal();
    }

    /**
     * Este método muestra las distintas opciones del recepcionista.
     */
    private void opcionesRecepcionista() {
        System.out.println();
        System.out.println("1: Check In");
        System.out.println("2: Check Out");
        System.out.println("3: Cantidad de habitaciones totales");
        System.out.println("4: Ver habitaciones ocupadas");
        System.out.println("5: Ver habitaciones desocupadas");
        System.out.println("6: Ver habitacion especifica");
        System.out.println("7: Ver habitaciones con problemas");
        System.out.println("8: Dar reportes");
        System.out.println("9: Ver historial de reportes");
        System.out.println("10: Ver todas las reservas");
        System.out.println("11: Ver menú de empleado");
        System.out.println("12: Salir");

    }

    /**
     * Este método es el menú de el administrador del hotel, dentro de el cuentan con todas las funciones necesarias para realizar su trabajo.
     *Se selecciona la función que se quiere realizar a través de un switch case.
     *
     * {@link Hotel#opcionesAdministrador()}
     *
     * 1-{@link Administrador#crearUsuarioEmpleado(ArrayList, TreeMap)},2-{@link Administrador#eliminarUsuario(ArrayList, TreeMap)},3-{@link Hotel#mostrarEmpleados()},
     * 4-{@link Hotel#mostrarUsuarios()},5-{@link Administrador#darPermisos(TreeMap)},6-{@link Administrador#agregarConsumibles(ArrayList)},
     * 7-{@link Administrador#generarBackUp(Hotel, String)},8-{@link Hotel#menuEmpleado(Usuario)},
     *
     * @param user este usuario es con el cual se ha iniciado sesión.
     */
    private void menuAdministrador(Usuario user) {
        Administrador administrador = (Administrador) user.getPersona();
        System.out.println();
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
                    mostrarEmpleados();
                    break;
                case 4:
                    mostrarUsuarios();
                    break;
                case 5:
                    administrador.darPermisos(mapEmpleados);
                    escribirArchivoMap("Empleados.json", (TreeMap<K, T>) mapEmpleados);
                    break;
                case 6:
                    administrador.agregarConsumibles(listaConsumibles);
                    escribirArchivoArrayList("Consumibles.json", (ArrayList<T>) listaConsumibles);
                    break;
                case 7:
                    administrador.generarBackUp(this, this.ruta);
                    break;
                case 8:
                    menuEmpleado(user);
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

    /**
     * Este método muestra las distintas opciones del administrador.
     */
    private void opcionesAdministrador() {
        System.out.println();
        System.out.println("1: Crear Usuario");
        System.out.println("2: Eliminar Usuario");
        System.out.println("3: Ver empleados");
        System.out.println("4: Ver usuarios");
        System.out.println("5: Dar Permisos");
        System.out.println("6: Agregar Consumibles");
        System.out.println("7: Generar BackUp");
        System.out.println("8: Ver menu empleado");
        System.out.println("9: Exit");
    }

    /**
     * Este método es el menú de el pasajero del hotel, dentro de el cuentan con distintas opciones como la de realizar un CheckIn o un CheckOut, pedir un consumible, entre otras.
     * Se selecciona la función que se quiere realizar a través de un switch case.
     *
     * {@link Hotel#opcionesPasajero()}
     * 
     * 1- {@link Hotel#realizarReserva(Usuario)},
     * 2- {@link Hotel#cancelarReserva(Usuario)},
     * 3- {@link Hotel#verUsuario(Usuario)},
     * 5- {@link Pasajero#pedirConsumible(ArrayList, TreeMap)},
     * 6- {@link Hotel#lateCheckOut(Usuario)},
     * 7- {@link Hotel#earlyCheckIn(Usuario)},
     * 8- {@link Hotel#verMisReservas(Usuario)},
     * 9- {@link Hotel#verMisFacturas(Usuario)},
     * 10- {@link Hotel#modificarUsuario(Usuario)},
     * 11- {@link Hotel#eliminarUsuario(Usuario)},
     *
     * @param usuario este usuario es con el cual se ha iniciado sesión.
     */
    private void menuPasajero(Usuario usuario) {
        Pasajero pasajero = (Pasajero) usuario.getPersona();
        boolean checkOut;
        boolean checkIn;
        int opcion = 0;
        System.out.println();
        System.out.println("Bienvenido " + pasajero.getNombre() + " " + pasajero.getApellido());
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

    /**
     * Este método muestra las distintas opciones del pasajero.
     */
    private void opcionesPasajero() {
        System.out.println();
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

    /**
     * Este método es el menú de los empleados del hotel, dentro de el cuentan con distintas opciones para realizar sus tareas diarias.
     *Se selecciona la función que se quiere realizar a través de un switch case.
     *
     * {@link Hotel#opcionesEmpleado()}
     *
     * 1- {@link Empleado#fichaje()},
     * 2- {@link Empleado#desFichaje()}, {@link Servicio#notificacionesServicio(TreeMap, ArrayList)},
     * 3- {@link Servicio#darReporte(TreeMap, ArrayList)},
     * 5- {@link Empleado#calcularDiasVacaciones()},
     *
     * @param user este usuario es con el cual se ha iniciado sesión.
     */
    private void menuEmpleado(Usuario user) {
        Class<?> clase;
        Empleado empleado;

        if (user.getPersona() instanceof Servicio) {
            clase = Servicio.class;
        } else if (user.getPersona() instanceof Recepcionista) {
            clase = Recepcionista.class;
        } else {
            clase = Administrador.class;
        }

        empleado = (Empleado) clase.cast(user.getPersona());

        System.out.println("Bienvenido " + empleado.getNombre() + " " + empleado.getApellido());
        int opcion = 0;
        do {
            opcionesEmpleado();
            System.out.println("Que opcion desea realizar?");
            teclado.nextLine();
            opcion = teclado.nextInt();
            switch (opcion) {
                case 1:
                    empleado.fichaje();
                    if (empleado instanceof Servicio) {
                        ((Servicio) empleado).notificacionesServicio(mapHabitaciones, reportes);
                    }
                    break;
                case 2:
                    empleado.desFichaje();
                    break;
                case 3:
                    if (empleado instanceof Servicio) {
                        ((Servicio) empleado).darReporte(mapHabitaciones, reportes);
                    } else {
                        System.out.println("Su cargo no lo habilita para esta accion.");
                    }
                    escribirArchivoArrayList("Reportes.json", (ArrayList<T>) reportes);
                    break;
                case 4:
                    System.out.println(empleado);
                    break;
                case 5:
                    System.out.println("Tiene " + empleado.calcularDiasVacaciones() + " días de vacaciones disponibles.");
                    break;
                case 6:
                    if (empleado instanceof Recepcionista) {
                        menuRecepcionista(user);
                    } else if (empleado instanceof Administrador) {
                        menuAdministrador(user);
                    } else {
                        System.out.println("Hasta luego"); //Volver al login
                    }
                    break;
                default:
                    System.out.println("Error, opcion no valida");
                    break;
            }
        } while (opcion != 6);

        menuPrincipal();
    }

    /**
     * Este método muestra las distintas opciones de los empleados.
     */
    private void opcionesEmpleado() {
        System.out.println();
        System.out.println("¡Recuerde fichar primero!");
        System.out.println("1: Fichar entrada");
        System.out.println("2: Fichar salida");
        System.out.println("3: Realizar reporte");
        System.out.println("4: Ver datos");
        System.out.println("5: Calcular dias de vacaciones");
        System.out.println("6: Salir");
    }

    /**
     * Este método permite a los pasajeros pedir un late CheckOut (retirarse del hotel más tarde lo habitual).
     * Si se cumplen ciertas condiciones, el hotel les brindará una horas extras dentro de el.
     *
     * @param user es el pasajero el cual pidió el late CheckOut.
     */
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
        if (!reserva.isLateCheckOut()) {
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
        } else {
            System.out.println("En esta reserva ya se ha hecho un late CheckOut");

        }


    }

    /**
     * Este método permite a los pasajeros pedir un early CheckIn (ingresar unas horas antes al hotel).
     * Si se cumplen ciertas condiciones, el hotel les permitirá ingresar antes de lo habitual.
     *
     * @param user es el pasajero el cual pidió el late CheckOut.
     */
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
        if (!reserva.isEarlyCheckIn()) {
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
        } else {
            System.out.println("En esta reserva ya se ha realizado un early CheckIn");
        }
    }

    /**
     * Este método permite al pasajero ver las reservas que ya ha realizado.
     *
     * @param user es el pasajero que pidió ver sus reservas.
     */
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

    /**
     * Este método permite al pasajero ver sus respectivas facturas.
     *
     * @param user es el pasajero que pidió ver sus facturas.
     */
    public void verMisFacturas(Usuario user) {
        for (String clave : mapFacturas.keySet()) {
            if (user.getPersona() instanceof Pasajero) {
                if (mapFacturas.get(clave).getPasajero().equals(user.getPersona())) {
                    System.out.println(mapFacturas.get(clave));
                }
            }
        }
    }

    /**
     * Este método permite al usuario ver sus datos.
     *
     * @param usuario
     */
    private void verUsuario(Usuario usuario) {
        System.out.println(usuario);
    }

    /**
     * Este método es la segunda opción del menú principal. A traves de este una persona crea su usuario.
     *
     * @return el usuario recien creado.
     */
    private Usuario registrarUsuario() {
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

    /**
     * Esta funcion realiza la validación correspondiente para que una persona inicie sesión.
     *
     * @return retorna el usuario ingresado o en caso de que no se encuentre el usuario devuelve null.
     */
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
            System.out.printf("Ingrese la contrasenia: ");
            String contra = teclado.next();
            verif = verificarContrasena(user, contra, i);
            if (verif) {
                return user;
            }
            i--;
        }
        return null;
    }

    /**
     * Está función verifica que al crearse un usuario, el nombre de usuario elegido no se encuentre registrado anteriormente.
     *
     * @param nombreDeUsuario es el nombre de usuario que la persona ha elegido para su nuevo usuario.
     * @return si el username ya se encuentra utilizado por otra persona, se retornará true. Si el nombre está disponible para este nuevo usuario, se retornará false.
     */
    private boolean verificarNombre(String nombreDeUsuario) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNombreDeUsuario().equals(nombreDeUsuario)) {
                System.out.println("Ese nombre de usuario ya se encuentra registrado.");
                return true;
            }
        }
        return false;
    }

    /**
     * Esta función busca dentro de toda la lista de usuarios, si existe el username con el cual se está iniciando sesión.
     *
     * @param nombreDeUsuario es el username con el cual se está intentando de iniciar sesión.
     * @return retornará el usuario en caso de que esté se encuentre registrado. Si este no existe, se retornará NULL.
     */
    private Usuario buscarPorNombre(String nombreDeUsuario) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNombreDeUsuario().equals(nombreDeUsuario)) {
                return usuario;
            }
        }
        System.out.println("Nombre de usuario incorrecto");
        return null;
    }

    /**
     * Esta función verifica que la persona ingrese la contraseña correcta de su usuario.
     *
     * @param user     es el usuario en el cual se está intentando de iniciar sesión.
     * @param contra   es la contraseña ingresada por la persona.
     * @param intentos es la cantidad de intentos restantes que tiene la persona para errar la contraseña (máximo 3).
     * @return si la persona ingresa la contraseña correctamente se retorna true, sino false.
     */
    private boolean verificarContrasena(Usuario user, String contra, int intentos) {
        if (user.getContraseña().equals(contra)) {
            return true;
        }
        System.out.println("La contrasenia ingresada es incorrecta. Le quedan " + (intentos - 1) + " intentos.");
        return false;
    }

    /**
     * Esta función permite al usuario modificar sus datos.
     *
     * @param user es el usuario que solicitó cambiar cierto dato de el.
     */
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
                        System.out.println("No tiene registrada direccion");
                    }
                    break;
                case 6:
                    if (user.getPersona() instanceof Pasajero) {
                        System.out.println("Ingrese su nuevo pais");
                        ((Pasajero) user.getPersona()).setOrigen(teclado.next());
                    } else {
                        System.out.println("No tiene registrado pais");
                    }
                    break;
                case 7:
                    if (user.getPersona() instanceof Pasajero) {
                        System.out.println("Ingrese su historia");
                        ((Pasajero) user.getPersona()).setHistoria(teclado.next());
                    } else {
                        System.out.println("No tiene registrada una historia");
                    }
                    break;
                default:
                    System.out.println("Esa opcion es incorrecta");
                    break;
            }
            System.out.println("Desea modificar algun otro aspecto de usuario?");
            continuar = teclado.next().charAt(0);
        } while (continuar == 's');
        escribirArchivoArrayList("Pasajeros.json", (ArrayList<T>) listaPasajeros);
        escribirArchivoArrayList("Usuarios.json", (ArrayList<T>) listaUsuarios);
    }

    /**
     * Son las opciones disponibles que tiene el usuario para modificar.
     */
    private void menuModificacion() {
        System.out.println("Modificacion de usuario");
        System.out.println("1: Nombre de usuario");
        System.out.println("2: Contrasenia");
        System.out.println("3: Nombre");
        System.out.println("4: Apellido");
        System.out.println("5: Direccion");
        System.out.println("6: Ciudad");
        System.out.println("7: Historia");
    }

    /**
     * Esta función elimina al usuario que solicitó ser eliminado (pasajero).
     *
     * @param user es el usuario que solicitó ser eliminado.
     */
    public void eliminarUsuario(Usuario user) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.equals(user)) {
                listaUsuarios.remove(user);
                listaPasajeros.remove((Pasajero) user.getPersona());
            }
        }
        escribirArchivoArrayList("Usuarios.json", (ArrayList<T>) listaUsuarios);
    }

    /**
     * Esta función permite al recepcionista realizar un CheckIn presencial a un pasajero.
     *
     * {@link Hotel#realizarReserva(Usuario)} llama a este metodo en caso de que no encuentre el codigo de reserva.
     *
     * @param user es el recepcionista que está realiando el CheckIn.
     */
    public void checkInRecepcionista(Usuario user) {
        Recepcionista recepcionista = (Recepcionista) user.getPersona();
        if (recepcionista.informarCheckIn(mapReservas) == true) {
            realizarReserva(user);
        }
    }

    /**
     * Esta función permite al usuario realizar su propia reserva a través de internet. Y a su vez permite al recepcionista realizar una reserva de forma presencial.
     * En ambos casos se piden los datos detallados de la reserva a realizar (fecha - cochera - opción de pago).
     * ,{@link Hotel#registrarPasajeros(Usuario)}
     * ,{@link Hotel#eventoHabNoDisponible()}
     * ,{@link Hotel#registrarHabitacion(Reserva, LocalDateTime)} ()}
     * ,{@link Hotel#registrarCochera(Reserva)} ()}
     * ,{@link Hotel#generarFactura(Factura, Reserva)} ()}
     * ,{@link Hotel#menuPaynet(int)} ()}
     * ,{@link Hotel#setearDias(Reserva)}
     *
     * @param user es el usuario que solicitó realizar una reserva (pasajero o recepcionista)
     */
    public void realizarReserva(Usuario user) {
        eventoHabNoDisponible();
        Reserva reserva = new Reserva();
        teclado.nextLine();
        if (user.getPersona() instanceof Pasajero) {
            System.out.println("Bienvenido/a al sistema de reservas de habitaciones del Hotel Lester.");
        } else if (user.getPersona() instanceof Recepcionista) {
            System.out.println("Bienvenido/a al sistema de reservas de habitaciones del Hotel Lester, a continuación le solicitaremos los datos de los pasajeros: ");
        }
        reserva.pasajeros = registrarPasajeros(user);
        LocalDateTime fechaEntrada;
        do {
            System.out.println("Ingrese la fecha de entrada (Dia/Mes/Año): ");
            System.out.println("Ingrese dia:");
            int dia = teclado.nextInt();
            System.out.println("Ingrese mes:");
            int mes = teclado.nextInt();
            System.out.println("Ingrese año:");
            int anio = teclado.nextInt();
            fechaEntrada = LocalDateTime.of(anio, mes, dia, 18, 0);
            if (fechaEntrada.compareTo(LocalDateTime.now()) <= 0) {
                System.out.println("La fecha de entrada ingresada es anterior a la fecha actual, por favor intente nuevamente");
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
                System.out.println("La fecha de salida es anterior a la fecha de entrada");
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
        System.out.println("A continuación se imprimirá la factura de su reserva. ACLARACIÓN IMPORTANTE: El precio final incluye la estadía y la cochera. LOS CONSUMIBLES SE PAGAN AL MOMENTO DE ORDENARLOS");
        Factura factura = new Factura();
        generarFactura(factura, reserva);
        System.out.println(factura);
        System.out.println("Redirigiendo a Paynet esto puede tardar unos segundos...");
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        lock.lock(); // Bloquear el lock
        try {
            condition.await(6, TimeUnit.SECONDS); // Pausar durante 6 segundos
        } catch (InterruptedException e) {
            System.out.println("Error al ingresar a Paynet");
        } finally {
            lock.unlock(); // Desbloquear el lock
        }
        int opcionPago = 0;
        System.out.println("Como quiere abonar en Lester Hotel");
        System.out.println("1: VISA CREDITO 1 CUOTA");
        System.out.println("2: VISA DEBITO");
        System.out.println("3: TRANSFERENCIA BANCARIA");
        System.out.println("4: Cancelar Pago");
        opcionPago = teclado.nextInt();
        if ((opcionPago < 5 && opcionPago > 0)) {
            boolean pago = menuPaynet(opcionPago);
            if (pago == true) {
                dineroTotal += factura.getPrecioTotal();
            }
        } else {
            System.out.println("Opción incorrecta");
            realizarReserva(user);
        }
        setearDias(reserva);
        mapFacturas.put(factura.getCodigoIdentificador(), factura);
        System.out.println("Felicitaciones ya realizaste tu reserva en Lester Hotel. Te esperamos pronto");
        escribirArchivoMap("Reservas.json", (TreeMap<K, T>) mapReservas);
        escribirArchivoMap("Facturas.json", (TreeMap<K, T>) mapFacturas);
        escribirArchivoMap("Habitaciones.json", (TreeMap<K, T>) mapHabitaciones);
        escribirAuxiliar("Hotel.json");
    }

    /**
     * Esta función permite al pasajero abonar su estadía en el hotel de la forma en la que desee.
     *
     * @param opcion es la opción de pago con la que el pasajero decidió abonar su reserva.
     * @return true si la transacción de pago fue exitosa. Sino se retornará false.
     */
    private boolean menuPaynet(int opcion) {
        teclado.useDelimiter("\n");
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        lock.lock(); // Bloquear el lock
        long numeroTarjeta = 0;
        int seguridad = 0;
        String nombre;
        do {
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese su nombre completo");
                    nombre = teclado.next();
                    System.out.println("Ingrese su número de tarjeta bancaria credito");
                    numeroTarjeta = teclado.nextLong();
                    System.out.println("Ingrese su número de seguridad de la tarjeta");
                    seguridad = teclado.nextInt();
                    System.out.println("Procesando tu transacción...");
                    try {
                        condition.await(6, TimeUnit.SECONDS); // Pausar durante 6 segundos
                    } catch (InterruptedException e) {
                        System.out.println("Error en la transacción");
                    } finally {
                        lock.unlock(); // Desbloquear el lock
                    }
                    System.out.println("Transacción realizada");
                    System.out.println("Saliendo de Paynet...");
                    return true;
                case 2:
                    System.out.println("Ingrese su nombre completo");
                    nombre = teclado.next();
                    System.out.println("Ingrese su número de tarjeta bancaria débito");
                    numeroTarjeta = teclado.nextLong();
                    System.out.println("Ingrese su número de seguridad de la tarjeta");
                    seguridad = teclado.nextInt();
                    System.out.println("Procesando tu transacción...");
                    try {
                        condition.await(6, TimeUnit.SECONDS); // Pausar durante 6 segundos
                    } catch (InterruptedException e) {
                        System.out.println("Error en la transacción");
                    } finally {
                        lock.unlock(); // Desbloquear el lock
                    }
                    System.out.println("Transacción realizada");
                    System.out.println("Saliendo de Paynet...");
                    return true;
                case 3:
                    System.out.println("Envie el total de la operación al siguiente alias bancario: Lester.Hotel.MDQ y envie un comprobante a lesterhotelmdq@gmail.com indicando su nombre completo y número de reserva");
                    System.out.println("Saliendo de Paynet...");
                    try {
                        condition.await(6, TimeUnit.SECONDS); // Pausar durante 6 segundos
                    } catch (InterruptedException e) {
                        System.out.println("Error en la transacción");
                    } finally {
                        lock.unlock(); // Desbloquear el lock
                    }
                    return true;
                case 4:
                    System.out.println("Saliendo de Paynet...");
                    try {
                        condition.await(6, TimeUnit.SECONDS); // Pausar durante 6 segundos
                    } catch (InterruptedException e) {
                        System.out.println("Error en la transaccion");
                    } finally {
                        lock.unlock(); // Desbloquear el lock
                    }
            }
        } while (opcion != 4);
        return false;
    }

    /**
     * Esta función permite al pasajero cancelar su reserva hecha anteriormente.
     * Si la persona cancela la reserva en un plazo menor a 3 días de el checkIn no se le devuelve dinero,
     * si es mayor a una semana se le devuelve el 75% y
     * si es mayor a 3 dias pero menor a una semana el 50%
     *
     * @param user es el usuario del pasajero que solicitó la cancelación.
     */
    public void cancelarReserva(Usuario user) {
        teclado.useDelimiter("\n");
        char seguir;
        double descontar;
        System.out.println("Ingrese su número de reserva.");
        String numeroR = teclado.next();
        long dias = ChronoUnit.DAYS.between(LocalDateTime.now(), mapReservas.get(numeroR).fechaEntrada);
        if (mapReservas.containsKey(numeroR)) {
            if (dias < 3) {
                System.out.println("Su plazo para cancelar se ha extendido, y no tendra devolución de dinero.");
                System.out.println("Esta seguro que quiere continuar? s/n");
                seguir = teclado.next().charAt(0);
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
                System.out.println("Se le devolverá el 75% del dinero.");
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
                System.out.println("Se le devolverá el 50% del dinero.");
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

    /**
     * Esta función genera una factura automáticamente despues de realizar una reserva.
     *
     * @param factura es la nueva factura a realizar.
     * @param reserva es la reserva que se llevó a cabo anteriormente.
     * @throws IOException en caso de que la factura ya se encuentre registrada.
     */
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

    /**
     * Esta función setea la cantidad de dias que el pasajero se quedará en el hotel.
     *
     * @param reserva es la reserva que se está llevando a cabo.
     */
    private void setearDias(Reserva reserva) {
        for (Pasajero pasajero : reserva.pasajeros) {
            pasajero.setCantDias(calcularDias(reserva));
        }
        for (Pasajero pasajero : listaPasajeros) {
            pasajero.setCantDias(calcularDias(reserva));
        }
    }

    /**
     * Esta función calcula los días que hay entre la fecha de entrada y la fecha de salida de una reserva.
     * Utilizamos el metodo truncatedTo para que calcule la diferencia en dias sin tener en cuenta las horas.
     *
     * @param reserva es la reserva la cual se quieren calcular sus días de duración.
     * @return la cantidad de días de duración de la reserva.
     */
    private int calcularDias(Reserva reserva) {
        LocalDateTime fechaEntradaSinHora = reserva.fechaEntrada.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime fechaSalidaSinHora = reserva.fechaSalida.truncatedTo(ChronoUnit.DAYS);
        long dias = ChronoUnit.DAYS.between(fechaEntradaSinHora, fechaSalidaSinHora);
        return (int) dias;
    }

    /**
     * Esta función guarda dentro de una lista todos los pasajeros que vendran al hotel con la reserva que está realizandose.
     * {@link Hotel#registrarPasajero()} esta funcion se encarga de la carga individual de los datos de los pasajeros
     *
     * @param user es el usuario que está realizando la reserva
     * @return la lista de pasajeros de la actual reserva.
     */
    private ArrayList<Pasajero> registrarPasajeros(Usuario user) {
        ArrayList<Pasajero> pasajeros = new ArrayList<>();
        char control = 's';
        Pasajero nuevo = new Pasajero();
        if (user.getRol() == Rol.ROL_USER) {
            nuevo = (Pasajero) user.getPersona();
        } else {
            nuevo = registrarPasajero();
        }
        pasajeros.add(nuevo);
        listaPasajeros.add(nuevo);

        while (control == 's') {
            System.out.printf("\nQuiere registrar a otro pasajero? s/n: ");
            control = teclado.next().charAt(0);
            if (control == 's') {
                nuevo = registrarPasajero();
                pasajeros.add(nuevo);
                listaPasajeros.add(nuevo);
            }
        }
        teclado.nextLine();
        escribirArchivoArrayList("Pasajeros.json", (ArrayList<T>) listaPasajeros);
        return pasajeros;
    }

    /**
     * Permite al pasajero reservar una cochera dentro de su estadía en el hotel.
     *
     * @param reserva es la reserva que se está llevando a cabo.
     */
    private void registrarCochera(Reserva reserva) {
        int espacios;
        System.out.printf("\nQuiere cochera? s/n: ");
        char a = teclado.next().toLowerCase().charAt(0);
        if (a == 's') {
            reserva.setCochera(true);
            do {
                System.out.println("Cuantos vehículos quiere almacenar? ");
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

    /**
     * Pregunta datos del pasajero que se quiere añadir a la reserva.
     * Verifica que el DNI del pasajero no se encuentre registrado:
     *
     * @return el pasajero con sus datos completos.
     * @throws IOException por el DNI ya registrado.
     */
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
                System.out.println("El DNI ingresado ya se encuentra registrado, por favor intente con un nuevo pasajero");
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

    /**
     * Está función le permite al pasajero elegir su habitación dentro de las disponibles.
     * Se utilizan dos funciones para verificar la condicion de la habitacion {@link Hotel#verificarOcupacion(String, LocalDateTime)} y {@link Hotel#verificarEstado(String)}
     *
     * @param reserva      es la reserva que se está llevando a cabo.
     * @param fechaEntrada es la fecha de entrada de la reserva que se está realizando.
     */
    private void registrarHabitacion(Reserva reserva, LocalDateTime fechaEntrada) {
        char continuar;
        String numero;
        boolean ocupacion;
        boolean estado;
        do {
            mostrarHabitaciones();
            do {
                System.out.println("Ingrese el número de habitación que quiere reservar: ");
                teclado.nextLine();
                numero = teclado.next();
                ocupacion = verificarOcupacion(numero, fechaEntrada);
                estado = verificarEstado(numero);
                if (ocupacion && estado) {
                    System.out.println("Gran elección!!");
                    for (String h : mapHabitaciones.keySet()) {
                        if (h.equals(numero)) {
                            mapHabitaciones.get(h).setOcupada(true);
                            reserva.habitaciones.add(mapHabitaciones.get(h));
                        }
                    }
                } else if (!estado) {
                    System.out.println("La habitación que seleccionó se encuentra " + mapHabitaciones.get(numero).getEstado().toLowerCase() + ". Por favor reintente con otra habitación. Disculpe las molestias.");
                } else if (!ocupacion) {
                    System.out.println("La habitación que seleccionó se encuentra reservada hasta una fecha posterior a su fecha de entrada. Por favor reintente en otra fecha u otra habitación. Disculpe las molestias.");
                } else {
                    System.out.println("La habitación que seleccionó no se encuetra disponible. Por favor reintente con otra habitación. Disculpe las molestias.");
                }
            } while (!ocupacion || !estado);
            System.out.println("Quiere reservar otra habitación más?");
            continuar = teclado.next().charAt(0);
        } while (continuar == 's');
    }

    /**
     * Verifica si la habitación elegida no estará ocupada para cuando lleguen los pasajeros.
     *
     * @param habitacion   es el numero de habitación que se quiere reservar.
     * @param fechaEntrada es la fecha en la cual llegan los pasajeros.
     * @return true si la habitación estará disponible, false si no lo estará.
     */
    private boolean verificarOcupacion(String habitacion, LocalDateTime fechaEntrada) {
        for (String reserva1 : mapReservas.keySet()) {
            for (int i = 0; i < mapReservas.get(reserva1).habitaciones.size(); i++) {
                if (mapReservas.get(reserva1).habitaciones.get(i).numero.equals(habitacion)) {
                    if (mapReservas.get(reserva1).fechaSalida.compareTo(fechaEntrada) >= 0) {
                        return false;  // La habitación está ocupada durante la fecha de entrada
                    }
                }
            }
        }
        return true;  // La habitación está disponible
    }

    /**
     * Verifica el estado de la habitación que se quiere reservar.
     *
     * @param habitacion es el numero de habitación que se quiere reservar.
     * @return true si la habitación se encuentra en estado "Disponible", sino retornará false.
     */
    private boolean verificarEstado(String habitacion) {
        if (mapHabitaciones.get(habitacion).getEstado().equals("Disponible")) {
            return true;
        } else {
            return false;
        }
    }


    private void mostrarServiciosIncluidos() {
        System.out.println("En cuanto a las instalaciones nuestro hotel cuenta con ascensor,piscina al aire libre y climatizada, calefaccion y bar,");
        System.out.println(" asi como aire acondicionado en salas comunes y las habitaciones, telefono,television,baño privado, caja fuerte y minibar en la habitacion.");
        System.out.println("Estacionamiento de pago para los huespedes con Valet Parking");
        System.out.println("Disponemos de un area de recepcion con servicio de atencion al cliente las 24 horas del dia por personal cualificado");
        System.out.println("Un espacio separado para guardar el equipaje y un servicio de consigna para equipajes voluminosos o pesados a peticion del huesped.");
        System.out.println("Acceso gratuito a internet Wi-Fi en las habitaciones y areas comunes.");
        System.out.println("Limpieza diaria de las habitaciones y el cambio de toallas y sabanas.");
        System.out.println("Contamos con un restaurant para el desayuno,almuerzo y cena de primer nivel.");
        System.out.println("Contamos con un sistema de camaras en las salas comunes para garantizar la seguridad de los huespedes y sus pertenencias.");
        System.out.printf("Nuestro personal se caracteriza por ser amable y servicial, y estar disponible para ayudar a los huespedes con cualquier necesidad o pregunta.");
    }

    /**
     * Este método muestra todas las reservas que se han realizado en el hotel.
     */

    public void mostrarReservas() {
        for (String clave : mapReservas.keySet()) {
            System.out.println(mapReservas.get(clave));
        }
    }

    /**
     * Este método muestra todas las facturas realizadas en el hotel.
     */
    public void mostrarFactura() {
        for (String clave : mapFacturas.keySet()) {
            System.out.println(mapFacturas.get(clave));
        }
    }

    /**
     * Este método muestra todos los pasajeros registrados en el hotel.
     */
    public void mostrarPasajeros() {
        for (Pasajero pasajero : listaPasajeros) {
            System.out.println(pasajero);
        }
    }

    /**
     * Este método muestra todos los empleados del hotel.
     */
    public void mostrarEmpleados() {
        for (String empleado : mapEmpleados.keySet()) {
            System.out.println(mapEmpleados.get(empleado));
        }
    }

    /**
     * Este método muestra todos los usuarios registrados en el hotel.
     */
    public void mostrarUsuarios() {
        for (Usuario usuario : listaUsuarios) {
            System.out.println(usuario);
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

    /**
     * Esta función muestra todas las habitaciones del hotel.
     */
    public void mostrarHabitaciones() {
        for (String clave : mapHabitaciones.keySet()) {

            System.out.println(mapHabitaciones.get(clave));

        }
    }

    /**
     * Esta función muestra todos los consumibles del hotel.
     */
    public void mostrarConsumibles() {
        for (Consumible consumible : listaConsumibles) {
            System.out.println(consumible);
        }
    }

    /**
     * Esta funcion genera un estado aleatorio a las habitaciones, para así recreear ciertos problemas que pueden surgir en un hotel.
     */
    private void eventoHabNoDisponible() {
        ArrayList<String> nros = new ArrayList<>();
        String string = null;
        Random random = new Random();
        boolean problema = false;
        int num = 0;
        for (String numero : mapHabitaciones.keySet()) {
            nros.add(numero);
        }
        for (String clave : nros) {
            if ((!mapHabitaciones.get(clave).isOcupada()) && !problema) {
                num = random.nextInt(nros.size() - 1);
                string = nros.get(num);
                problema = true;
            }
        }
        mapHabitaciones.get(string).setEstado(problemas());
    }

    /**
     * Son los estados en el cual puede encontrarse una habitación.
     *
     * @return un String como el estado de la habitación.
     */
    private String problemas() {
        Random random = new Random();
        int num = random.nextInt(5 - 1 + 1) + 1;
        String problema = "Disponible";
        if (num == 1) {
            problema = "En reparacion funcional";
        } else if (num == 2) {
            problema = "En desinfeccion";
        } else if (num == 3) {
            problema = "En reparacion electrica";
        } else if (num == 4) {
            problema = "En reparacion estetica";
        } else if (num == 5) {
            problema = "En limpieza";
        }
        return problema;
    }


    /**
     * Este método escribe a los archivos los cuales necesiten leer una colección map.
     *
     * @param archivo es el archivo a escribir
     * @param mapa    es la colección a leer.
     */
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

    /**
     * Esta función permite a un map ser escrito a través de lo leído en un archivo.
     *
     * @param archivo es el archivo a leer.
     * @param clave   es el tipo de de clave qie tiene el map.
     * @param valor   es el tipo de clase que contiene el map.
     * @return el TreeMap cargado.
     */
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

    /**
     * Esta función permite a un archivo ser escrito a través del contenido de un Arraylist.
     *
     * @param archivo   es el archivo a escribir.
     * @param arrayList es la lista que se va a leer.
     */
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

    /**
     * Está función escribe a un ArrayList con el contenido de un archivo.
     *
     * @param archivo es el archivo a leer.
     * @param clazz   es el tipo de clase del ArrayList.
     * @return el ArrayList cargado con los datos del archivo.
     */
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

    /**
     * Escribe un archivo auxiliar con el dinero total y las cocheras del hotel.
     *
     * @param archivo es el archivo escrito con el dinero total y las cocheras del hotel.
     */
    public void escribirAuxiliar(String archivo) {
        Auxiliar auxiliar = new Auxiliar(dineroTotal, cochera);
        try {
            mapper.writeValue(new File(ruta + archivo), auxiliar);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al escribir el archivo auxiliar");
        }
    }

    /**
     * Perimte cargar a una instancia auxiliar con lo leído de un archivo auxiliar.
     *
     * @param archivo el archivo auxiliar cargado.
     */
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

    /**
     * Esta función escribe a todos los archivos que existen.
     */
    public void escribirTodosArchivos() {
        escribirArchivoMap("Empleados.json", (TreeMap<K, T>) mapEmpleados);
        escribirArchivoMap("Reservas.json", (TreeMap<K, T>) mapReservas);
        escribirArchivoMap("Facturas.json", (TreeMap<K, T>) mapFacturas);
        escribirArchivoMap("Habitaciones.json", (TreeMap<K, T>) mapHabitaciones);
        escribirAuxiliar("Hotel.json");
        escribirArchivoArrayList("Pasajeros.json", (ArrayList<T>) listaPasajeros);
        escribirArchivoArrayList("Usuarios.json", (ArrayList<T>) listaUsuarios);
        escribirArchivoArrayList("Reportes.json", (ArrayList<T>) reportes);
    }



    @Override
    public String toString() {
        return "\n- " + nombre + '\'' +
                "\n- Direccion: " + direccion + '\'' +
                "\n- Ciudad: " + ciudad + '\'' +
                "\n- Cantidad de estrellas: " + cantidadEstrellas;
    }

}




