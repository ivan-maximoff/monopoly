package org.monopoly.controller.accion;

import org.monopoly.model.Config;
import org.monopoly.model.Juego;
import org.monopoly.model.casilla.Casilla;
import org.monopoly.model.casilla.Comprable;
import java.util.List;

public class AccionDeshipotecar extends AccionCasilla{
    private final Config.TipoAcciones tipo = Config.TipoAcciones.DESHIPOTECAR;
    public AccionDeshipotecar(Juego juego, List<Casilla> opciones){
        super(juego, opciones);
    }

    public void accionar(){
        this.juego.deshipotecar((Comprable)casilla);
    }
    public String getNombre(){
        return "Deshipotecar propiedad";
    }

    public Config.TipoAcciones getTipo(){return this.tipo;}

}
