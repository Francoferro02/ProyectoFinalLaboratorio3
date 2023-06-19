package Personas;

import Habitaciones.Habitacion;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.TreeMap;

@JsonTypeName("Personas.Servicio")
public class Servicio extends Empleado{

    public Servicio() {
        calcularSueldo();
    }

    public Servicio(@JsonProperty("nombre")String nombre, @JsonProperty("apellido")String apellido, @JsonProperty("DNI")String DNI, @JsonProperty("sueldo")double sueldo, @JsonProperty("antiguedad")int antiguedad, @JsonProperty("trabajador")String  trabajador) {
        super(nombre, apellido, DNI, sueldo, antiguedad,trabajador);
        calcularSueldo();
    }


    public void realizarAccion(TreeMap<String, Habitacion> mapaHabitaciones) {
        for(String num : mapaHabitaciones.keySet()){
            if(mapaHabitaciones.get(num).getEstado().equals("En reparación funcional")){
                if(this.trabajador.equals("OBRERO")){
                    System.out.println("Arregla la habitación.");
                    mapaHabitaciones.get(num).setEstado("Disponible");
                }
            } else if (mapaHabitaciones.get(num).getEstado().equals("En desinfección")){
                if(this.trabajador.equals("FUMIGADOR")){
                    System.out.println("Fumiga la habitación.");
                    mapaHabitaciones.get(num).setEstado("Disponible");
                }
            }else if (mapaHabitaciones.get(num).getEstado().equals("En reparación eléctrica")){
                if(this.trabajador.equals("ELECTRICISTA")){
                    System.out.println("Arregla el sistema eléctrico.");
                    mapaHabitaciones.get(num).setEstado("Disponible");
                }
            }else if (mapaHabitaciones.get(num).getEstado().equals("En reparación estética")){
                if(this.trabajador.equals("PINTOR")){
                    System.out.println("Pinta la habitación.");
                    mapaHabitaciones.get(num).setEstado("Disponible");
                }
            } else if (mapaHabitaciones.get(num).getEstado().equals("En limpieza")){
                if(this.trabajador.equals("LIMPIEZA")){
                    System.out.println("Ordena y limpia la habitación.");
                    mapaHabitaciones.get(num).setEstado("Disponible");
                }
            }
        }
        if(this.trabajador.equals("CHEF")){
            System.out.println("Cocina comida.");
        } else if (this.trabajador.equals("MESERO")) {
            System.out.println("Sirve comida.");
        }else if (this.trabajador.equals("BACHERO")) {
            System.out.println("Lava la bacha.");
        }else if (this.trabajador.equals("SUBJEFE_DE_COCINA")) {
            System.out.println("Ayuda al chef.");
        }else if (this.trabajador.equals("SERVICIO_A_LA_HABITACION")) {
            System.out.println("Lleva insumos a una habitación.");
        }else if (this.trabajador.equals("GUARDAVIDAS")) {
            System.out.println("Cuida la piscina.");
        }else if (this.trabajador.equals("PORTERO")) {
            System.out.println("Da la bienvenida.");
        }else if (this.trabajador.equals("VALET_PARKING")) {
            System.out.println("Estaciona el vehículo.");
        }

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



    @Override
    public String toString() {
        return "\n------------------------------------------------"+
                "\nServicio{" +
                "\nsueldo=" + sueldo +
                "\n, diasVacaciones=" + diasVacaciones +
                "\n, antiguedad=" + antiguedad +
                "\n, trabajador=" + trabajador +
                "\n, nombre='" + nombre + '\'' +
                "\n, apellido='" + apellido + '\'' +
                "\n, DNI='" + DNI + '\'';
    }
}
