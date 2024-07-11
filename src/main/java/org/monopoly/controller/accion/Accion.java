package org.monopoly.controller.accion;

import org.monopoly.model.Config;

public interface Accion {
    void accionar();
    Config.EtapaAcciones getEtapa();
    Config.TipoAcciones getTipo();
    String getNombre();
}
