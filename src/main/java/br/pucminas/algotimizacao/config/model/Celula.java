package br.pucminas.algotimizacao.config.model;

/**
 * Created by aninh on 17/05/2016.
 */
public class Celula {
    double n1;
    double n2;

    public Celula(double n1, double n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public double getN1() {
        return n1;
    }

    public void setN1(double n1) {
        this.n1 = n1;
    }

    public double getN2() {
        return n2;
    }

    public void setN2(double n2) {
        this.n2 = n2;
    }
}
