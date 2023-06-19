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

@JsonTypeName("Personas.Administrador")
public class Administrador extends Empleado implements Gerenciamiento {
    private Scanner teclado = new Scanner(System.in);
    private ObjectMapper mapper = new ObjectMapper();

    public Administrador(@JsonProperty("nombre") String nombre, @JsonProperty("apellido") String apellido, @JsonProperty("DNI") String DNI, @JsonProperty("sueldo") double sueldo, @JsonProperty("antiguedad") int antiguedad, @JsonProperty("trabajador") String trabajador) {
        super(nombre, apellido, DNI, sueldo, antiguedad, trabajador);
        calcularSueldo();
    }


    @Override
    public void calcularSueldo() {
        double sueldo = this.sueldo;
        sueldo += sueldo*(this.antiguedad/100);
        this.setSueldo(sueldo);
    }

    @Override
    public int calcularDiasVacaciones() {
        super.calcularDiasVacaciones();
        return diasVacaciones;
    }

    @Override
    public void fichaje() {
        super.fichaje();
    }

    @Override
    public void desFichaje() {
        super.desFichaje();
    }

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

    private Empleado registrarEmpleado(String DNI, int opcion) {
        Servicio servicio = new Servicio();
        Recepcionista recepcionista = new Recepcionista();
        boolean registrado;
        do {
            if (opcion == 1) {
                System.out.println("Ingrese el nombre del empleado");
                recepcionista.setNombre(teclado.next());
                System.out.println("Ingrese el apellido del empleado");
                recepcionista.setApellido(teclado.next());
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
                System.out.println("Numero de rol invalido, vuelve a ingresarlo");
                opcionesTrabajador();
                cual = teclado.nextInt();
            }
            mapEmpleados.get(documento).setTrabajador(menuRegistroTrabajador(cual));
            System.out.println("Rol actualizado");
        } else {
            System.out.println("No existe ese documento");
        }
    }

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
