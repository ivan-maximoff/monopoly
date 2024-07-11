package org.monopoly.controller.accion;

import org.monopoly.model.Config;
import org.monopoly.model.Juego;
import org.monopoly.model.casilla.Casilla;
import java.util.List;

public abstract class AccionCasilla implements Accion {
    protected Juego juego;
    protected Casilla casilla;
    private final Config.EtapaAcciones etapa = Config.EtapaAcciones.CASILLA;
    protected List<Casilla> opciones;

    public AccionCasilla(Juego juego, List<Casilla> opciones){
        this.juego = juego;
        this.opciones = opciones;
    }

    public void setCasilla(Casilla casilla){
        this.casilla = casilla;
    }

    public List<Casilla> getOpciones(){
        return this.opciones;
    }

    public Config.EtapaAcciones getEtapa() {return this.etapa;}
    public abstract void accionar();
    public abstract String getNombre();

    public abstract Config.TipoAcciones getTipo();
}
