package Personas;

import Contable.Reserva;
import Servicios.Consumible;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Clase de los pasajeros que desean hospedarse en el hotel.
 */
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


    /**
     * Clase que le permite a un pasajero pedir un consumible, siempre y cuando hospedado en el hotel.
     * @param listaConsumibles es la lista de consumibles disponibles para pedir.
     * @param mapReservas es el mapa de reservas y es necesario para ver si el pasajero tiene una reserva vigente.
     * @return la cantidad de dinero gastada en consumibles.
     */
    public double pedirConsumible(ArrayList<Consumible> listaConsumibles, TreeMap<String, Reserva> mapReservas) {
        int opcion = 0;
        boolean encontrado = false;
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
                        encontrado = true;
                    }
                }
            }
        }
        if (encontrado){
            System.out.println("Su reserva ya finalizo o todavia no es valida");
        }
        return 0;
    }

    @Override
    public String toString() {
        return "\n------------------------------------------------"+
                "\nPasajero: " +
                "\n- Nombre: " + nombre +
                "\n- Apellido: " + apellido +
                "\n- DNI: " + DNI +
                "\n- Origen: " + origen +
                "\n- Domicilio: " + domicilioOrigen +
                "\n- Historia: " + historia +
                "\n- Cantidad de dias: " + cantDias;
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
