package Controladora;

import Servicios.Cochera;

public class Auxiliar {

    private double dineroTotal;
    private Cochera cochera;

    public Auxiliar() {
    }

    public Auxiliar(double dineroTotal, Cochera cochera) {
        this.dineroTotal = dineroTotal;
        this.cochera = cochera;
    }

    public double getDineroTotal() {
        return dineroTotal;
    }

    public void setDineroTotal(double dineroTotal) {
        this.dineroTotal = dineroTotal;
    }

    public Cochera getCochera() {
        return cochera;
    }

    public void setCochera(Cochera cochera) {
        this.cochera = cochera;
    }
}
