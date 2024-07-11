package org.monopoly.controller.accion;

import org.monopoly.model.Config;
import org.monopoly.model.Juego;

abstract class AccionInicio implements Accion {
    protected Juego juego;
    private final Config.EtapaAcciones etapa = Config.EtapaAcciones.INICIO;
    public AccionInicio(Juego juego){
        this.juego = juego;
    }

    public Config.EtapaAcciones getEtapa() {return this.etapa;}

    public abstract void accionar();

    public abstract String getNombre();
    public abstract Config.TipoAcciones getTipo();
}
