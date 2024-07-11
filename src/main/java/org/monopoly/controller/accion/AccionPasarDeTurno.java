package org.monopoly.controller.accion;

import org.monopoly.model.Config;
import org.monopoly.model.Juego;

public class AccionPasarDeTurno extends AccionFinal{
    private final Config.TipoAcciones tipo = Config.TipoAcciones.PASAR_TURNO;
    public AccionPasarDeTurno(Juego juego) {
        super(juego);
    }

    public void accionar(){
        juego.siguienteTurno();
    }

    public String getNombre(){
        return "Pasar De Turno";
    }

    public Config.TipoAcciones getTipo(){return this.tipo;}

}
