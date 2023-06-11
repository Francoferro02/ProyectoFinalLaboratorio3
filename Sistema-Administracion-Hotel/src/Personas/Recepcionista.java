package Personas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.Scanner;
@JsonTypeName("Personas.Recepcionista")
public class Recepcionista extends Empleado implements Gerenciamiento{
    Scanner teclado = new Scanner(System.in);

    public Recepcionista(@JsonProperty("nombre")String nombre, @JsonProperty("apellido")String apellido, @JsonProperty("DNI")String DNI, @JsonProperty("sueldo")double sueldo, @JsonProperty("diasVacaciones")int diasVacaciones, @JsonProperty("antiguedad")int antiguedad) {
        super(nombre, apellido, DNI, sueldo, diasVacaciones, antiguedad);
        this.usuario = new Usuario();
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
    public void mostrarDatosUsuario() {
        super.mostrarDatosUsuario();
    }



    public void informarCheckIn(){
        ArrayList<Pasajero> pasajeros = new ArrayList<>();//registrarPasajero();
        System.out.printf("\nCuantos días se va a hospedar?: ");
        int cant = teclado.nextInt();
        for (Pasajero pasajero: pasajeros) {
            pasajero.setCantDias(cant);
        }

    }

    public void informarCheckOut(){

    }

    private void reservarHabitacion(){

    }

    public void informarCantHabitaciones(){

    }

    public void verOcupaciones(){

    }

    public void verDesocupadas(){

    }

    private double calcularPrecio(){
        return 0;
    }

    public void generarFactura(){

    }



    @Override
    public String toString() {
        return "Recepcionista{" +
                "sueldo=" + sueldo +
                ", diasVacaciones=" + diasVacaciones +
                ", antiguedad=" + antiguedad +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", DNI=" + DNI +
                '}';
    }

    public void cerrarTeclado(){
        this.teclado.close();
    }

}

