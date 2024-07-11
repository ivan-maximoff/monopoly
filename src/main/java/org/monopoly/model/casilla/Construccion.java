package org.monopoly.model.casilla;

import org.monopoly.model.Config;

public class Construccion {
    private final Config.TiposConstrucciones tipo;
    private final int valorAlquiler;

    public Construccion (Config.TiposConstrucciones tipo, int valorAlquiler){
        this.tipo = tipo;
        this.valorAlquiler = valorAlquiler;
    }

    public Config.TiposConstrucciones getTipo() {
        return this.tipo;
    }

    public int getValorAlquiler() {
        return this.valorAlquiler;
    }
}