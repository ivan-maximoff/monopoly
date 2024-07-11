package org.monopoly.controller.accion;

import org.monopoly.model.Config;
import org.monopoly.model.Juego;

public class AccionEntrarEnQuiebra extends AccionFinal {

    private final Config.TipoAcciones tipo = Config.TipoAcciones.ENTRAR_EN_QUIEBRA;

    public AccionEntrarEnQuiebra(Juego juego){
        super(juego);
    }

    public void accionar(){
        this.juego.entrarEnQuiebra();
    }

    public String getNombre(){
        return "Aceptar La Muerte";
    }

    public Config.TipoAcciones getTipo(){return this.tipo;}

}
