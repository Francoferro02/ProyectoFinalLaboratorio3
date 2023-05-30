package Servicios;

public class Cochera {

    private int espacioTotal;
    private int espacioDisponible;
    private int precioDia;

    public Cochera(int espacioTotal, int espacioDisponible, int precioDia) {
        this.espacioTotal = espacioTotal;
        this.espacioDisponible = espacioDisponible;
        this.precioDia = precioDia;
    }

    public int getEspacioTotal() {
        return espacioTotal;
    }

    public int getEspacioDisponible() {
        return espacioDisponible;
    }

    public int getPrecioDia() {
        return precioDia;
    }

    public void setEspacioDisponible(int espacioDisponible) {
        this.espacioDisponible = espacioDisponible;
    }
}
