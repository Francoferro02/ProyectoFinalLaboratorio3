package Personas;

import Habitaciones.Habitacion;

import java.util.TreeMap;

public interface Mantenimiento {
    void mostrarHabitacionesConProblemas(TreeMap<String, Habitacion> mapa);
    String darReporte();

}
