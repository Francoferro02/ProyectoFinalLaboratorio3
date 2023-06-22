package Personas;

import Habitaciones.Habitacion;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Interfaz implementada para todos los trabajadores de mantenimiento en el hotel.
 */
public interface Mantenimiento {

    /**
     * Método que muestra todas las habitaciones las cuales no esten disponibles por determinado problema.
     * @param mapa todas las habitaciones en el hotel.
     */
    void mostrarHabitacionesConProblemas(TreeMap<String, Habitacion> mapa);

    /**
     * Función la cual guarda en la lista de reportes, todas las habitaciones con sus respectivos reportes.
     * @param mapa todas las habitaciones del hotel.
     * @param listaReportes lista de las habitaciones con sus respectivos reportes.
     */
    void darReporte(TreeMap<String, Habitacion> mapa,ArrayList<String> listaReportes);

}
