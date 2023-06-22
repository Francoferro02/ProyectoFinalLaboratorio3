package Personas;

import Controladora.Hotel;
import Controladora.Rol;
import Servicios.Consumible;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Clase del administrador del hotel.
 * Contiene ciertos métodos especiales que solo pueden ser utilizados por el.
 */
@JsonTypeName("Personas.Administrador")
public class Administrador extends Empleado {
    private Scanner teclado = new Scanner(System.in);
    private ObjectMapper mapper = new ObjectMapper();

    public Administrador(@JsonProperty("nombre") String nombre, @JsonProperty("apellido") String apellido, @JsonProperty("DNI") String DNI, @JsonProperty("sueldo") double sueldo, @JsonProperty("antiguedad") int antiguedad, @JsonProperty("trabajador") String trabajador) {
        super(nombre, apellido, DNI, sueldo, antiguedad, trabajador);
        calcularSueldo();
    }

    /**
     * Función que se engarga de calcular el sueldo de un empleado teniendo en cuenta ciertas características.
     */
    @Override
    public void calcularSueldo() {
        double sueldo = this.sueldo;
        sueldo += sueldo*(this.antiguedad/100);
        this.setSueldo(sueldo);
    }

    /**
     * Esta función calcula la cantidad de días de vacaciones que un empleado debe tener, teniendo en cuenta ciertos factores
     */
    @Override
    public int calcularDiasVacaciones() {
        super.calcularDiasVacaciones();
        return diasVacaciones;
    }

    /**
     * Función que se encarga de registrar cuando el empleado entra al hotel
     */
    @Override
    public void fichaje() {
        super.fichaje();
    }

    /**
     * Función que se encarga de registrar cuando el empleado termina su turno en el hotel
     */
    @Override
    public void desFichaje() {
        super.desFichaje();
    }

    /**
     * Permite al administrador crear un usuario para un empleado del hotel.
     * {@link Administrador#registrarEmpleado(String, int)}.
     * {@link Administrador#opcionesTrabajador()}.
     * {@link Administrador#menuRegistroTrabajador(int)}.
     * @param listaUsuario la lista de usuarios del hotel, la cual se usa para verificar que no haya ningun usuario con el username nuevo.
     * @param mapEmpleado mapa de empleados del hotel.
     * @return el nuevo usuario del empleado.
     */
    public Usuario crearUsuarioEmpleado(ArrayList<Usuario> listaUsuario, TreeMap<String, Empleado> mapEmpleado) {
        String DNI = null;
        Usuario user = new Usuario();
        boolean verif = true;
        while (verif) {
            System.out.printf("Ingrese un nombre de usuario (DNI): ");
            DNI = teclado.next();
            for (Usuario u : listaUsuario) {
                if (!(u.getNombreDeUsuario().equals(DNI))) {
                    user.setNombreDeUsuario(DNI);
                    verif = false;
                }
            }
        }
        teclado.nextLine();
        System.out.printf("\nIngrese su contraseña: ");
        user.setContraseña(teclado.next());
        int opcionEmpleado = 0;
        do {
            System.out.println("Va a registrar recepcionista o servicio? (1 o 2)");
            opcionEmpleado = teclado.nextInt();
            user.setPersona(registrarEmpleado(DNI, opcionEmpleado));
        } while (user.getPersona() == null);
        user.setRol(Rol.ROL_EMPLEADO);
        teclado.nextLine();
        int opcionRol = 0;
        if (user.getPersona() instanceof Servicio) {
            opcionesTrabajador();
            System.out.println("Que servicio esta registrando?");
            opcionRol = teclado.nextInt();
            while (opcionRol > 8) {
                System.out.println("Opcion invalida, vuelva a ingresarla: ");
                opcionesTrabajador();
                opcionRol = teclado.nextInt();
            }
            ((Servicio) user.getPersona()).setTrabajador(menuRegistroTrabajador(opcionRol));
            System.out.println("Trabajador creado correctamente.");
        } else if (user.getPersona() instanceof Recepcionista) {
            ((Recepcionista) user.getPersona()).setTrabajador(Trabajadores.RECEPCIONISTA.getAbreviaturas(0));
            System.out.println("Recepcionista creado correctamente.");
        }
        return user;
    }

    /**
     * Opciones de las cuales puede ejercer un trabajador del hotel.
     */
    private void opcionesTrabajador() {
        System.out.println("1:ADMINISTRADOR");
        System.out.println("2:MUCAMA");
        System.out.println("3:CHEF");
        System.out.println("4:AYUDANTE COCINA");
        System.out.println("5:GUARDAVIDAS");
        System.out.println("6:PORTERO");
        System.out.println("7:VALET PARKING");
        System.out.println("8:MANTENIMIENTO");
    }

    /**
     * Función que retorna el tipo de empleo que ejerce el nuevo empleado.
     * {@link Trabajadores#getAbreviaturas(int)}.
     * @param opcion el numero de opción de trabajo que ejercer el nuevo empleado.
     * @return el tipo de trabajador que el nuevo empleado ejerce.
     */
    private String menuRegistroTrabajador(int opcion) {
        int tipo;
        do {

            switch (opcion) {
                case 1:
                    return Trabajadores.ADMINISTRADOR.getAbreviaturas(0);
                case 2:
                    return Trabajadores.MUCAMA.getAbreviaturas(0);
                case 3:
                    return Trabajadores.CHEF.getAbreviaturas(0);
                case 4:
                    System.out.println("Que tipo de ayudante de cocina?: ");
                    System.out.println("1: MESERO - 2: BACHERO - 3: SUBJEFE DE COCINA - 4: SERVICIO A LA HABITACION");
                    tipo = teclado.nextInt();
                    while (opcion < 1 && opcion > 4) {
                        System.out.println("Opcion incorrecta, ingrese otra: ");
                        System.out.println("1: MESERO - 2: BACHERO - 3: SUBJEFE DE COCINA - 4: SERVICIO A LA HABITACION");
                        tipo = teclado.nextInt();
                    }
                    switch (tipo) {
                        case 1:
                            return Trabajadores.AYUDANTE_COCINA.getAbreviaturas(0);
                        case 2:
                            return Trabajadores.AYUDANTE_COCINA.getAbreviaturas(1);
                        case 3:
                            return Trabajadores.AYUDANTE_COCINA.getAbreviaturas(2);
                        case 4:
                            return Trabajadores.AYUDANTE_COCINA.getAbreviaturas(3);

                    }
                    break;
                case 5:
                    return Trabajadores.GUARDAVIDAS.getAbreviaturas(0);
                case 6:
                    return Trabajadores.PORTERO.getAbreviaturas(0);
                case 7:
                    return Trabajadores.VALET_PARKING.getAbreviaturas(0);
                case 8:
                    System.out.println("Que tipo de Mantenimiento: ");
                    System.out.println("1: OBRERO - 2: FUMIGADOR - 3: PINTOR - 4: ELECTRICISTA");
                    tipo = teclado.nextInt();
                    while (opcion < 1 && opcion > 4) {
                        System.out.println("Opcion incorrecta, ingrese otra: ");
                        System.out.println("1: OBRERO - 2: FUMIGADOR - 3: PINTOR - 4: ELECTRICISTA");
                        tipo = teclado.nextInt();
                    }

                    switch (tipo) {
                        case 1:
                            return Trabajadores.MANTENIMIENTO.getAbreviaturas(0);
                        case 2:
                            return Trabajadores.MANTENIMIENTO.getAbreviaturas(1);
                        case 3:
                            return Trabajadores.MANTENIMIENTO.getAbreviaturas(2);
                        case 4:
                            return Trabajadores.MANTENIMIENTO.getAbreviaturas(3);
                    }

                    break;
                default:
                    System.out.println("Opcion Incorrecta");
                    break;
            }
        } while (opcion > 8);
        return null;
    }

    /**
     * Registro de las características básicas del nuevo empleado, define si el empleado es recepcionista o de servicio.
     * @param DNI el DNI del nuevo empleado.
     * @param opcion si el nuevo empleado es recepcionista o de servicio.
     * @return el nuevo empleado con su nombre, apellido y DNI.
     */
    private Empleado registrarEmpleado(String DNI, int opcion) {
        Servicio servicio = new Servicio();
        Recepcionista recepcionista = new Recepcionista();
        boolean registrado;
        teclado.useDelimiter("\n");
        do {
            if (opcion == 1) {
                System.out.println("Ingrese el primer nombre del recepcionista: ");
                recepcionista.setNombre(teclado.next());
                System.out.println("Ingrese el apellido del recepcionista: ");
                recepcionista.setApellido(teclado.next());
                recepcionista.setDNI(DNI);
                registrado = true;
                return recepcionista;
            } else if (opcion == 2) {
                System.out.println("Ingrese el primer nombre del empleado: ");
                servicio.setNombre(teclado.next());
                System.out.println("Ingrese el apellido del empleado: ");
                servicio.setApellido(teclado.next());
                servicio.setDNI(DNI);
                registrado = true;
                return servicio;
            } else {
                System.out.println("Opcion incorrecta");
                registrado = true;
            }
        } while (!registrado);
        return null;
    }

    /**
     * Permite al administrador eliminar un usuario de un empleado.
     * @param listaUsuarios lista de usuarios para eliminar a uno de estos.
     * @param mapEmpleados mapa de empleados para eliminar uno de estos.
     */
    public void eliminarUsuario(ArrayList<Usuario> listaUsuarios, TreeMap<String, Empleado> mapEmpleados) {
        teclado.nextLine();
        System.out.println("Lista de empleados:");
        for (String e : mapEmpleados.keySet()) {
            System.out.println(mapEmpleados.get(e));
        }
        System.out.printf("Ingrese el documento del empleado que desea eliminar: ");
        String documento = teclado.next();
        if (mapEmpleados.containsKey(documento)) {
            mapEmpleados.remove(documento);
            for (Usuario u : listaUsuarios) {
                if (u.getPersona().getDNI().equals(documento)) {
                    listaUsuarios.remove(u);
                }
            }
        } else {
            System.out.println("No existe ese documento");
        }

    }

    /**
     * Permite al administrador generar un BackUp con toda la información del hotel
     * @param lester es la variable del hotel que se quiere realizar el BackUp
     * @param ruta es la ruta del archivo BackUp
     */
    public void generarBackUp(Hotel lester, String ruta) {
        File backUp = new File(ruta + "BackUp.json");
        try {
            if (lester != null) {
                mapper.writeValue(backUp, lester);
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            System.out.println("El hotel no existe");
        }
    }

    /**
     * Permite cambiar el tipo de trabajo que ejerce un empleado.
     * {@link Administrador#opcionesTrabajador()}.
     * {@link Administrador#menuRegistroTrabajador(int)}.
     * @param mapEmpleados es el mapa de empleados, en el cual se busca cierto empleado para darle determinado permiso.
     */
    public void darPermisos(TreeMap<String, Empleado> mapEmpleados) {
        teclado.nextLine();
        int cual;
        for (String e : mapEmpleados.keySet()) {
            System.out.println(mapEmpleados.get(e));
        }
        System.out.printf("Ingrese el documento del empleado que desea cambiar: ");
        String documento = teclado.next();
        teclado.nextLine();
        if (mapEmpleados.containsKey(documento)) {
            opcionesTrabajador();
            System.out.printf("Que rol desea darle?: ");
            cual = teclado.nextInt();
            while (cual > 8) {
                System.out.println("Numero de rol invalido, vuelva a ingresarlo");
                opcionesTrabajador();
                cual = teclado.nextInt();
            }
            mapEmpleados.get(documento).setTrabajador(menuRegistroTrabajador(cual));
            System.out.println("Rol actualizado");
        } else {
            System.out.println("No existe ese documento");
        }
    }

    /**
     * Permite al administrador agregar un consumible al hotel.
     * @param listaConsumibles lista de consumibles, a la cual se le agregará uno nuevo.
     */
    public void agregarConsumibles(ArrayList<Consumible> listaConsumibles) {
        char seguir = 's';
        teclado.nextLine();
        teclado.useDelimiter("\n");
        do {
            Consumible consumible = new Consumible();
            System.out.printf("Ingrese el nombre: ");
            consumible.setNombre(teclado.next());
            System.out.printf("Ingrese el precio: ");
            consumible.setPrecio(teclado.nextDouble());
            System.out.printf("Ingrese la descripcion: ");
            consumible.setDescripcion(teclado.next());
            listaConsumibles.add(consumible);
            System.out.println("Desea agregar otro consumible? s/n");
            teclado.next().charAt(0);
        } while (seguir == 's');
    }

    @Override
    public String toString() {
        return "\n------------------------------------------------" +
                "\nAdministrador: " +
                "\n- Nombre: " + nombre + '\'' +
                "\n- Apellido: " + apellido + '\'' +
                "\n- DNI: " + DNI + '\''+
                "\n- Sueldo: " + sueldo +
                "\n- Dias de vacaciones: " + diasVacaciones +
                "\n- Antiguedad: " + antiguedad +
                "\n- Trabajador: " + trabajador;
    }
}
