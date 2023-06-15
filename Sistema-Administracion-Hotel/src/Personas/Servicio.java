package Personas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Personas.Servicio")
public class Servicio extends Empleado{

    Trabajadores trabajadores;

    public Servicio(@JsonProperty("nombre")String nombre, @JsonProperty("apellido")String apellido, @JsonProperty("DNI")String DNI, @JsonProperty("sueldo")double sueldo, @JsonProperty("diasVacaciones")int diasVacaciones, @JsonProperty("antiguedad")int antiguedad, @JsonProperty("rol")Trabajadores rol) {
        super(nombre, apellido, DNI, sueldo, diasVacaciones, antiguedad);
        this.trabajadores = rol;
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

    public void agregarTrabajadores(){

    }

    @Override
    public String toString() {
        return "Servicios{" +
                "trabajadores=" + trabajadores+
                ", sueldo=" + sueldo +
                ", diasVacaciones=" + diasVacaciones +
                ", antiguedad=" + antiguedad +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", DNI=" + DNI +
                '}';
    }
}
