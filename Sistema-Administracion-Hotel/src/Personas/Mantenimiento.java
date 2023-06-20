package Personas;

import Habitaciones.Habitacion;

import java.util.ArrayList;
import java.util.TreeMap;

public interface Mantenimiento {
    void mostrarHabitacionesConProblemas(TreeMap<String, Habitacion> mapa);
    void darReporte(TreeMap<String, Habitacion> mapa,ArrayList<String> listaReportes);

}
