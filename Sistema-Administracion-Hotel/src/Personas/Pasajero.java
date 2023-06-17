package Personas;

import Contable.Reserva;
import Servicios.Consumible;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@JsonTypeName("Personas.Pasajero")
public class Pasajero extends Persona {

    private String origen;
    private String domicilioOrigen;
    private String historia; //opcional
    private int cantDias;


    public Pasajero(@JsonProperty("nombre") String nombre, @JsonProperty("apellido") String apellido, @JsonProperty("DNI") String DNI, @JsonProperty("origen") String origen, @JsonProperty("domicilioOrigen") String domicilioOrigen, @JsonProperty("historia") String historia) {
        super(nombre, apellido, DNI);
        this.origen = origen;
        this.domicilioOrigen = domicilioOrigen;
        this.historia = historia;
    }

    public Pasajero() {
        super();
    }

    public String getOrigen() {
        return origen;
    }

    public String getDomicilioOrigen() {
        return domicilioOrigen;
    }

    public String getHistoria() {
        return historia;
    }

    public int getCantDias() {
        return cantDias;
    }

    public void setCantDias(int cantDias) {
        this.cantDias = cantDias;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setDomicilioOrigen(String domicilioOrigen) {
        this.domicilioOrigen = domicilioOrigen;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }


    public double pedirConsumible(ArrayList<Consumible> listaConsumibles, TreeMap<String, Reserva> mapReservas) {
        int opcion = 0;
        Scanner teclado = new Scanner(System.in);
        for (String k : mapReservas.keySet()) {
            for (Pasajero p : mapReservas.get(k).getPasajeros()) {
                if (p.equals(this)) {
                    if ((mapReservas.get(k).getFechaEntrada().compareTo(LocalDateTime.now()) <= 0) && (mapReservas.get(k).getFechaSalida().compareTo(LocalDateTime.now())) >= 0) {
                        System.out.println("Se le mostrara la lista de consumibles");

                        for (Consumible c : listaConsumibles) {
                            System.out.printf(" " + opcion + ":");
                            System.out.println(c);
                            opcion++;
                        }
                        System.out.printf("Elija el consumible: ");
                        opcion = teclado.nextInt();
                        System.out.println("En minutos te estara llegando tu " + listaConsumibles.get(opcion).getNombre());
                        return listaConsumibles.get(opcion).getPrecio();
                    } else {
                        System.out.println("Su reserva ya finalizo o todavia no es valida");
                    }
                }
            }

        }

        return 0;
    }

    @Override
    public String toString() {
        return "\n------------------------------------------------"+
                "\nPasajero{" +
                "\norigen='" + origen + '\'' +
                "\n, domicilioOrigen='" + domicilioOrigen + '\'' +
                "\n, historia='" + historia + '\'' +
                "\n, cantDias=" + cantDias +
                "\n, nombre='" + nombre + '\'' +
                "\n, apellido='" + apellido + '\'' +
                "\n, DNI='" + DNI + '\'';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pasajero other = (Pasajero) obj;
        // Comparar los atributos relevantes para determinar si son iguales
        return Objects.equals(nombre, other.nombre) && Objects.equals(apellido, other.apellido);
    }
}
