package org.monopoly.model;

public class CuentaBancaria {
    private int dinero;

    public CuentaBancaria(int dineroInicial){
        this.dinero = dineroInicial;
    }

    public int getDinero(){
        return this.dinero;
    }

    public void sumarDinero(int dinero){
        this.dinero += dinero;
    }

    public void retirarDinero(int dinero){
        if (this.poseeDinero(dinero)) this.dinero -= dinero;
    }

    public boolean poseeDinero(int dinero){
        return this.dinero >= dinero;
    }
}
