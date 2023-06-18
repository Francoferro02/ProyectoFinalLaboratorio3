package Personas;

import java.util.Arrays;

public enum Trabajadores {
    RECEPCIONISTA(new String[]{"RECEPCIONISTA"}),
    ADMINISTRADOR(new String[]{"ADMINISTRADOR"}),
    MUCAMA(new String[]{"LIMPIEZA"}), //Limpia
    CHEF(new String[]{"CHEF"}),
    AYUDANTE_COCINA(new String[]{"MESERO", "BACHERO","SUBJEFE_DE_COCINA", "SERVICIO_A_LA_HABITACION"}),
    GUARDAVIDAS(new String[]{"GUARDAVIDAS"}),
    PORTERO(new String[]{"PORTERO"}),
    VALET_PARKING(new String[]{"VALET_PARKING"}),
    MANTENIMIENTO(new String[]{"OBRERO", "FUMIGADOR", "PINTOR", "ELECTRICISTA"});//Reparacion

    public String[] abreviatura;

    Trabajadores(String[] abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String[] getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String[] abreviatura) {
        this.abreviatura = abreviatura;
    }
    public String getAbreviaturas(int index) {
        if (index >= 0 && index < abreviatura.length) {
            return abreviatura[index];
        }
        return null;
    }
}
