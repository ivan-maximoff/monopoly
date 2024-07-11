package org.monopoly.controller.accion;

import org.monopoly.model.Config;
import org.monopoly.model.Juego;
import org.monopoly.model.casilla.Casilla;
import org.monopoly.model.casilla.Comprable;
import java.util.List;

public class AccionComprar extends AccionCasilla{
    private final Config.TipoAcciones tipo = Config.TipoAcciones.COMPRAR;

    public AccionComprar(Juego juego, List<Casilla> opciones){
        super(juego, opciones);
    }

    public void accionar(){
        this.juego.comprar((Comprable) this.casilla);
    }

    public String getNombre(){
        return "Comprar";
    }

    public Config.TipoAcciones getTipo(){return this.tipo;}

}
