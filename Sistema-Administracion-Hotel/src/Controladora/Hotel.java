package Controladora;
import Contable.Factura;
import Habitaciones.Habitacion;
import Personas.Empleado;
import Personas.Gerenciamiento;
import Personas.Pasajero;
import Servicios.Cochera;
import Servicios.Consumibles;
import java.util.ArrayList;
import java.util.HashMap;



public class Hotel <T> {

    public String nombre;
    public String direccion;
    public String ciudad;
    public int cantidadEstrellas;
    private Cochera cochera;
    public ArrayList<Pasajero> listaPasajeros = new ArrayList<>();
    public ArrayList<Empleado> listaEmpleados = new ArrayList<>();
    public ArrayList<Gerenciamiento> listaGerentes = new ArrayList<>();
    public HashMap<Integer, Habitacion> mapHabitaciones = new HashMap<>();
    public HashMap<String, Factura> mapFacturas = new HashMap<>();
    public ArrayList<Consumibles> listaConsumibles = new ArrayList<>();
    private HashMap<String, String> nombreYcontrasena = new HashMap<>();

    public Hotel() {
    }


    public void login() {

    }

    private void menuRecepcionista() {

    }

    private void menuAdministrador() {
    }

    public void menuCliente(){

    }
    private void registrarUsuario(){

    }

    private boolean verificarNombre(){

    }

    private boolean verificarContrasena(){

    }

    public void reservarHabitacion(){

    }

    public void solicitarConsumo(){

    }

}

