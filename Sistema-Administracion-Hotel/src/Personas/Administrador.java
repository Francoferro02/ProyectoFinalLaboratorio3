package Personas;

import Controladora.Rol;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

@JsonTypeName("Personas.Administrador")
public class Administrador extends Empleado implements Gerenciamiento {
    private Scanner teclado = new Scanner(System.in);

    public Administrador(@JsonProperty("nombre") String nombre, @JsonProperty("apellido") String apellido, @JsonProperty("DNI") String DNI, @JsonProperty("sueldo") double sueldo, @JsonProperty("diasVacaciones") int diasVacaciones, @JsonProperty("antiguedad") int antiguedad, @JsonProperty("trabajador") Trabajadores trabajador) {
        super(nombre, apellido, DNI, sueldo, diasVacaciones, antiguedad, trabajador);
    }


    @Override
    public void realizarAcción() {

    }

    @Override
    public void calcularSueldo() {

    }

    @Override
    public void calcularDiasVacaciones() {

    }

    @Override
    public void fichaje() {

    }

    @Override
    public void desFichaje() {

    }

    public Usuario crearUsuarioEmpleado(ArrayList<Usuario> listaUsuario, TreeMap<String, Empleado> mapEmpleado) {
        String DNI = null;
        Usuario user = new Usuario();
        boolean verif = true;
        while (verif) {
            System.out.printf("Ingrese un nombre de usuario: ");
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
            System.out.println("Que servicio esta registrando?");
            opcionRol = teclado.nextInt();
            ((Servicio) user.getPersona()).setTrabajador(menuRegistroTrabajador(opcionRol));
        } else if (user.getPersona() instanceof Recepcionista) {
            ((Recepcionista) user.getPersona()).setTrabajador(Trabajadores.RECEPCIONISTA);
        }
        return user;
    }

    private Trabajadores menuRegistroTrabajador(int opcion) {
        System.out.println("1:ADMINISTRADOR");
        System.out.println("2:MUCAMA");
        System.out.println("3:CHEF");
        System.out.println("4:AYUDANTE COCINA");
        System.out.println("5:GUARDAVIDAS");
        System.out.println("6:PORTERO");
        System.out.println("7:VALET PARKING");
        do {
            switch (opcion) {
                case 1:
                    return Trabajadores.ADMINISTRADOR;
                case 2:
                    return Trabajadores.MUCAMA;
                case 3:
                    return Trabajadores.CHEF;
                case 4:
                    return Trabajadores.AYUDANTE_COCINA;
                case 5:
                    return Trabajadores.GUARDAVIDAS;
                case 6:
                    return Trabajadores.PORTERO;
                case 7:
                    return Trabajadores.VALET_PARKING;
                default:
                    System.out.println("Opcion Incorrecta");
                    break;
            }
        } while (opcion > 8);
        return null;
    }

    private Empleado registrarEmpleado(String DNI, int opcion) {
        Servicio servicio = new Servicio();
        Recepcionista recepcionista = new Recepcionista();
        boolean registrado;
        do {
            System.out.println("aaaaaaaaaaaa");
            if (opcion == 1) {
                System.out.println("Ingrese el nombre del empleado");
                recepcionista.setNombre(teclado.next());
                System.out.println("Ingrese el apellido del empleado");
                recepcionista.setApellido(teclado.next());
                System.out.println("Ingresando el DNI del empleado");
                recepcionista.setDNI(DNI);
                registrado = true;
                return recepcionista;
            } else if (opcion == 2) {
                System.out.println("Ingrese el nombre del empleado");
                servicio.setNombre(teclado.next());
                System.out.println("Ingrese el apellido del empleado");
                servicio.setApellido(teclado.next());
                System.out.println("Ingresando el DNI del empleado");
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

    public void eliminarUsuario() {

    }

    // Preguntar si se puede hacer en un archivo, de un archivo ya creado y con informacion. (Clonar archivo)
    public void generarBackUp() {

    }

    //Preguntar si hay que crear un usuario para cada pasajero o simplemente los permisos al recepcionista.
    public void darPermisos() {

    }

    public void agregarConsumibles() {

    }

    @Override
    public String toString() {
        return "\n------------------------------------------------" +
                "\nAdministrador{" +
                "\nsueldo=" + sueldo +
                "\n, diasVacaciones=" + diasVacaciones +
                "\n, antiguedad=" + antiguedad +
                "\n, trabajador=" + trabajador +
                "\n, nombre='" + nombre + '\'' +
                "\n, apellido='" + apellido + '\'' +
                "\n, DNI='" + DNI + '\'';
    }
}
