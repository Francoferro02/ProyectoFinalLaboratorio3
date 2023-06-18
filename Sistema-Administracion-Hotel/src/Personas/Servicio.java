package Personas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Personas.Servicio")
public class Servicio extends Empleado{

    public Servicio() {
    }

    public Servicio(@JsonProperty("nombre")String nombre, @JsonProperty("apellido")String apellido, @JsonProperty("DNI")String DNI, @JsonProperty("sueldo")double sueldo, @JsonProperty("antiguedad")int antiguedad, @JsonProperty("trabajador")String  trabajador) {
        super(nombre, apellido, DNI, sueldo, antiguedad,trabajador);
    }


    @Override
    public void realizarAcción() {

    }

    @Override
    public void calcularSueldo() {

    }

    @Override
    public void calcularDiasVacaciones() {
        super.calcularDiasVacaciones();
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
