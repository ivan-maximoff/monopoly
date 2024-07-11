package org.monopoly.controller.accion;

import org.monopoly.model.Config;
import org.monopoly.model.Juego;

public class AccionTirarDados extends AccionInicio{
    private final Config.TipoAcciones tipo = Config.TipoAcciones.TIRAR_DADOS;

    public AccionTirarDados(Juego juego){
        super(juego);
    }

    public void accionar(){
        this.juego.tirarDados();
        this.juego.mover();
    }

    public String getNombre(){
        return "Tirar Dados";
    }

    public Config.TipoAcciones getTipo(){return this.tipo;}

}
