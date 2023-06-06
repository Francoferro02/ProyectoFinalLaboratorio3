package Servicios;

public class Cochera {

    private int espacioTotal;
    private int espacioDisponible;
    public final double precioDia = 2500;

    public Cochera() {
    }

    public Cochera(int espacioTotal, int espacioDisponible, int precioDia) {
        this.espacioTotal = espacioTotal;
        this.espacioDisponible = espacioDisponible;
    }

    public int getEspacioTotal() {
        return espacioTotal;
    }

    public int getEspacioDisponible() {
        return espacioDisponible;
    }


    public void setEspacioDisponible(int espacioDisponible) {
        this.espacioDisponible = espacioDisponible;
    }



    @Override
    public String toString() {
        return "Cochera{" +
                "espacioTotal=" + espacioTotal +
                ", espacioDisponible=" + espacioDisponible +
                ", precio por Dia=" + precioDia +
                '}';
    }
}
