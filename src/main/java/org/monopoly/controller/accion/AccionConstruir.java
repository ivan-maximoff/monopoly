package org.monopoly.controller.accion;

import org.monopoly.model.Config;
import org.monopoly.model.Juego;
import org.monopoly.model.casilla.Casilla;
import org.monopoly.model.casilla.Propiedad;
import java.util.List;

public class AccionConstruir extends AccionCasilla{

    private final Config.TipoAcciones tipo = Config.TipoAcciones.CONSTRUIR;

    public AccionConstruir(Juego juego, List<Casilla> opciones){
        super(juego, opciones);
    }

    public void accionar(){
        this.juego.construirConstruccion((Propiedad) this.casilla);
    }

    public String getNombre(){
        return "Construir Construccion";
    }

    public Config.TipoAcciones getTipo(){return this.tipo;}

}
