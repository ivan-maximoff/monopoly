package org.monopoly.controller.accion;

import org.monopoly.model.Config;
import org.monopoly.model.Juego;
import org.monopoly.model.casilla.Casilla;
import org.monopoly.model.casilla.Propiedad;
import java.util.List;

public class AccionVender extends AccionCasilla{
    private final Config.TipoAcciones tipo = Config.TipoAcciones.VENDER;

    public AccionVender(Juego juego, List<Casilla> opciones){
        super(juego, opciones);
    }

    public void accionar(){
        this.juego.venderConstruccion((Propiedad) this.casilla);
    }

    public String getNombre(){
        return "Vender Construccion";
    }

    public Config.TipoAcciones getTipo(){return this.tipo;}
}
