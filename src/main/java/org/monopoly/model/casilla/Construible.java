package org.monopoly.model.casilla;

public interface Construible {
    void construir();
    void destruir();
    int getCantConstruidos();
    int getValorConstruir();
    int getValorDestruir();
    void demolerConstrucciones();
}
