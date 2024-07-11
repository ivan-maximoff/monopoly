package org.monopoly.view;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.monopoly.model.casilla.Comprable;

public class BotonView {
    public static Button crearBoton(String text, String estilo, String estiloHover, EventHandler<MouseEvent> event){
        Button boton = new Button();
        boton.setStyle(estilo);
        boton.setOnMouseEntered(e -> boton.setStyle(estiloHover));
        boton.setOnMouseExited(e -> boton.setStyle(estilo));
        boton.setText(text);
        boton.setOnMouseClicked(event);
        return boton;
    }
}
