package org.monopoly.controller.accion;

import org.monopoly.model.Config;
import org.monopoly.model.Juego;

public class AccionPagarFianza extends AccionInicio{
    private final Config.TipoAcciones tipo = Config.TipoAcciones.PAGAR_FIANZA;
    public AccionPagarFianza(Juego juego){
        super(juego);
    }

    public void accionar(){
        this.juego.pagarFianza();
    }

    public String getNombre(){
        return "Pagar Fianza";
    }

    public Config.TipoAcciones getTipo(){return this.tipo;}

}
