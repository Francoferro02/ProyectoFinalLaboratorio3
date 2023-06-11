package Personas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Personas.Administrador")
public class Administrador extends Empleado implements Gerenciamiento{

 public Administrador(@JsonProperty("nombre")String nombre, @JsonProperty("apellido")String apellido, @JsonProperty("DNI")String DNI, @JsonProperty("sueldo")double sueldo, @JsonProperty("diasVacaciones")int diasVacaciones, @JsonProperty("antiguedad")int antiguedad) {
  super(nombre, apellido, DNI, sueldo, diasVacaciones, antiguedad);
  this.usuario = new Usuario();
 }

    @Override
    public void mostrarDatosUsuario() {
        super.mostrarDatosUsuario();
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

    public void crearUsuario(){

    }

    // Preguntar si se puede hacer en un archivo, de un archivo ya creado y con informacion. (Clonar archivo)
    public void generarBackUp(){

    }

    //Preguntar si hay que crear un usuario para cada pasajero o simplemente los permisos al recepcionista.
    public void darPermisos(){

    }

    public void agregarConsumibles(){

    }

    @Override
    public String toString() {
        return "Administrador{" +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", DNI=" + DNI +
                ", sueldo=" + sueldo +
                ", diasVacaciones=" + diasVacaciones +
                ", antiguedad=" + antiguedad +
                '}';
    }
}
