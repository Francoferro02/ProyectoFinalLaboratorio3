package Personas;

import Habitaciones.Habitacion;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Clase que contiene ciertas funciones de los empleados del hotel.
 */
@JsonTypeName("Personas.Servicio")
public class Servicio extends Empleado implements Mantenimiento {

    public Servicio() {
        calcularSueldo();
    }

    public Servicio(@JsonProperty("nombre") String nombre, @JsonProperty("apellido") String apellido, @JsonProperty("DNI") String DNI, @JsonProperty("sueldo") double sueldo, @JsonProperty("antiguedad") int antiguedad, @JsonProperty("trabajador") String trabajador) {
        super(nombre, apellido, DNI, sueldo, antiguedad, trabajador);
        calcularSueldo();
    }

    /**
     * Función que permite a los empleados informar que van a realizar determinada acción dentro de una habitación.
     * @param mapaHabitaciones todas las habitaciones del hotel.
     */
    public void realizarAccion(TreeMap<String, Habitacion> mapaHabitaciones) {
        for (String num : mapaHabitaciones.keySet()) {
            if (mapaHabitaciones.get(num).getEstado().equals("En reparacion funcional")) {
                if (this.trabajador.equals("OBRERO")) {
                    System.out.println("Arregla la habitacion.");
                    mapaHabitaciones.get(num).setEstado("Disponible");
                }
            } else if (mapaHabitaciones.get(num).getEstado().equals("En desinfección")) {
                if (this.trabajador.equals("FUMIGADOR")) {
                    System.out.println("Fumiga la habitacion.");
                    mapaHabitaciones.get(num).setEstado("Disponible");
                }
            } else if (mapaHabitaciones.get(num).getEstado().equals("En reparacion electrica")) {
                if (this.trabajador.equals("ELECTRICISTA")) {
                    System.out.println("Arregla el sistema electrico.");
                    mapaHabitaciones.get(num).setEstado("Disponible");
                }
            } else if (mapaHabitaciones.get(num).getEstado().equals("En reparacion estetica")) {
                if (this.trabajador.equals("PINTOR")) {
                    System.out.println("Pinta la habitacion.");
                    mapaHabitaciones.get(num).setEstado("Disponible");
                }
            } else if (mapaHabitaciones.get(num).getEstado().equals("En limpieza")) {
                if (this.trabajador.equals("LIMPIEZA")) {
                    System.out.println("Ordena y limpia la habitacion.");
                    mapaHabitaciones.get(num).setEstado("Disponible");
                }
            }
        }
        if (this.trabajador.equals("CHEF")) {
            System.out.println("Cocina comida.");
        } else if (this.trabajador.equals("MESERO")) {
            System.out.println("Sirve comida.");
        } else if (this.trabajador.equals("BACHERO")) {
            System.out.println("Lava la bacha.");
        } else if (this.trabajador.equals("SUBJEFE_DE_COCINA")) {
            System.out.println("Ayuda al chef.");
        } else if (this.trabajador.equals("SERVICIO_A_LA_HABITACION")) {
            System.out.println("Lleva insumos a una habitacion.");
        } else if (this.trabajador.equals("GUARDAVIDAS")) {
            System.out.println("Cuida la piscina.");
        } else if (this.trabajador.equals("PORTERO")) {
            System.out.println("Da la bienvenida.");
        } else if (this.trabajador.equals("VALET_PARKING")) {
            System.out.println("Estaciona el vehículo.");
        }

    }

    /**
     * Método que cuenta cuantas habitaciones hay en cada estado.
     * @param mapa todas las habitaciones del hotel.
     * @param listaReportes lista de reportes de cada habitación.
     */
    public void notificacionesServicio(TreeMap<String, Habitacion> mapa, ArrayList<String> listaReportes) {
        int contador = 0;
        for (int i = 0; i < listaReportes.size(); i++) {
            for (String numero : mapa.keySet()) {
                if (mapa.get(numero).getEstado().equals("En reparacion funcional") && listaReportes.get(i).equals("Habitacion " + mapa.get(numero).getNumero() + " en " + "En reparacion funcional informar a mantenimiento")) {
                    if (this.trabajador.equals("OBRERO")) {
                        contador++;
                    }
                } else if (mapa.get(numero).getEstado().equals("En desinfección") && listaReportes.get(i).equals("Habitacion " + mapa.get(numero).getNumero() + " en " + "En desinfeccion informar a mantenimiento")) {
                    if (this.trabajador.equals("FUMIGADOR")) {
                        contador++;
                    }
                } else if (mapa.get(numero).getEstado().equals("En reparacion electrica") && listaReportes.get(i).equals("Habitacion " + mapa.get(numero).getNumero() + " en " + "En reparacion electrica informar a mantenimiento")) {
                    if (this.trabajador.equals("ELECTRICISTA")) {
                        contador++;
                    }
                } else if (mapa.get(numero).getEstado().equals("En reparacion estetica") && listaReportes.get(i).equals("Habitacion " + mapa.get(numero).getNumero() + " en " + "En reparacion estetica informar a mantenimiento")) {
                    if (this.trabajador.equals("PINTOR")) {
                        contador++;
                    }
                } else if (mapa.get(numero).getEstado().equals("En limpieza") && listaReportes.get(i).equals("Habitacion " + mapa.get(numero).getNumero() + " en " + "En limpieza informar a mantenimiento")) {
                    if (this.trabajador.equals("LIMPIEZA")) {
                        contador++;
                    }
                }
            }
        }
        System.out.println("Tienes " + contador + " habitaciones con problemas");
    }

    @Override
    public void calcularSueldo() {
        double sueldo = this.sueldo;
        sueldo += sueldo * (this.antiguedad / 100);
        this.setSueldo(sueldo);
    }

    @Override
    public int calcularDiasVacaciones() {
        this.diasVacaciones = super.calcularDiasVacaciones();
        return this.diasVacaciones;
    }

    @Override
    public void fichaje() {
        super.fichaje();
    }

    @Override
    public void desFichaje() {
        super.desFichaje();
    }

    /**
     * Método que muestra todas las habitaciones las cuales no esten disponibles por determinado problema.
     * @param mapa todas las habitaciones en el hotel.
     */
    @Override
    public void mostrarHabitacionesConProblemas(TreeMap<String, Habitacion> mapa) {
        boolean encontrado = false;
        for (String num : mapa.keySet()) {
            if (mapa.get(num).getEstado().equals("En reparacion funcional")) {
                if (this.trabajador.equals("OBRERO")) {
                    System.out.println(mapa.get(num));
                    encontrado = true;
                }
            } else if (mapa.get(num).getEstado().equals("En desinfeccion")) {
                if (this.trabajador.equals("FUMIGADOR")) {
                    System.out.println(mapa.get(num));
                    encontrado = true;
                }
            } else if (mapa.get(num).getEstado().equals("En reparacion electrica")) {
                if (this.trabajador.equals("ELECTRICISTA")) {
                    System.out.println(mapa.get(num));
                    encontrado = true;
                }
            } else if (mapa.get(num).getEstado().equals("En reparacion estetica")) {
                if (this.trabajador.equals("PINTOR")) {
                    System.out.println(mapa.get(num));
                    encontrado = true;
                }
            } else if (mapa.get(num).getEstado().equals("En limpieza")) {
                if (this.trabajador.equals("LIMPIEZA")) {
                    System.out.println(mapa.get(num));
                    encontrado = true;
                }
            }
        }
        if (!encontrado){
            System.out.println("Esa funcion no se encuentra disponible para su trabajo.");
        }
    }

    /**
     * Función la cual guarda en la lista de reportes, todas las habitaciones con sus respectivos reportes.
     * {@link Servicio#realizarAccion(TreeMap)}
     * @param mapa todas las habitaciones del hotel.
     * @param listaReportes lista de las habitaciones con sus respectivos reportes.
     */
    @Override
    public void darReporte(TreeMap<String, Habitacion> mapa, ArrayList<String> listaReportes) {
        for (int i = 0; i < listaReportes.size(); i++) {
            for (String numero : mapa.keySet()) {
                if (mapa.get(numero).getEstado().equals("En reparacion funcional") && listaReportes.get(i).equals("Habitacion " + mapa.get(numero).getNumero() + " en " + "En reparacion funcional informar a mantenimiento")) {
                    ArrayList<String> reporte = new ArrayList<>();
                    reporte.add("Habitacion " + mapa.get(numero).getNumero() + " en " + mapa.get(numero).getEstado() + " ,reparada");
                    if (this.trabajador.equals("OBRERO")) {
                        realizarAccion(mapa);
                        listaReportes.addAll(reporte);
                    }
                } else if (mapa.get(numero).getEstado().equals("En desinfeccion") && listaReportes.get(i).equals("Habitacion " + mapa.get(numero).getNumero() + " en " + "En desinfeccion informar a mantenimiento")) {
                    ArrayList<String> reporte = new ArrayList<>();
                    reporte.add("Habitacion " + mapa.get(numero).getNumero() + " en " + mapa.get(numero).getEstado() + " ,desinfectada");
                    if (this.trabajador.equals("FUMIGADOR")) {
                        realizarAccion(mapa);
                        listaReportes.addAll(reporte);
                    }
                } else if (mapa.get(numero).getEstado().equals("En reparacion electrica") && listaReportes.get(i).equals("Habitacion " + mapa.get(numero).getNumero() + " en " + "En reparacion electrica informar a mantenimiento")) {
                    ArrayList<String> reporte = new ArrayList<>();
                    reporte.add("Habitacion " + mapa.get(numero).getNumero() + " en " + mapa.get(numero).getEstado() + " ,reparada");
                    if (this.trabajador.equals("ELECTRICISTA")) {
                        realizarAccion(mapa);
                        listaReportes.addAll(reporte);
                    }
                } else if (mapa.get(numero).getEstado().equals("En reparacion estetica") && listaReportes.get(i).equals("Habitacion " + mapa.get(numero).getNumero() + " en " + "En reparacion estetica informar a mantenimiento")) {
                    ArrayList<String> reporte = new ArrayList<>();
                    reporte.add("Habitacion " + mapa.get(numero).getNumero() + " en " + mapa.get(numero).getEstado() + " ,reparada");
                    if (this.trabajador.equals("PINTOR")) {
                        realizarAccion(mapa);
                        listaReportes.addAll(reporte);
                    }
                } else if (mapa.get(numero).getEstado().equals("En limpieza") && listaReportes.get(i).equals("Habitacion " + mapa.get(numero).getNumero() + " en " + "En limpieza informar a mantenimiento")) {
                    ArrayList<String> reporte = new ArrayList<>();
                    reporte.add("Habitacion " + mapa.get(numero).getNumero() + " en " + mapa.get(numero).getEstado() + " ,limpiada");
                    if (this.trabajador.equals("LIMPIEZA")) {
                        realizarAccion(mapa);
                        listaReportes.addAll(reporte);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "\n------------------------------------------------" +
                "\nServicio: " +
                "\n- Nombre: " + nombre +
                "\n- Apellido: " + apellido +
                "\n- DNI: " + DNI +
                "\n- Sueldo: " + sueldo +
                "\n- Dias de vacaciones: " + diasVacaciones +
                "\n- Antiguedad: " + antiguedad +
                "\n- Trabajador: " + trabajador;
    }
}
