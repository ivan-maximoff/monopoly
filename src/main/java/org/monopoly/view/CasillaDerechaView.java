package org.monopoly.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.monopoly.model.Jugador;
import org.monopoly.model.casilla.Casilla;
import java.util.List;

public class CasillaDerechaView extends CasillaView {
    public CasillaDerechaView(Casilla casilla, List<Jugador> jugadores) {
        super(casilla, jugadores);
    }

    protected int getInfoMaxHeight() {
        return 40;
    }
    protected int getInfoMaxWidth() {
        return 100;
    }
    protected Node getCasillaView(VBox jugadores, Label info){
        HBox cView = new HBox(info, jugadores);
        cView.setAlignment(Pos.CENTER_LEFT);
        cView.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        cView.setOnMouseClicked(e -> {
            this.mostrarTarjeta();
        });
        VBox.setVgrow(cView, Priority.ALWAYS);
        return cView;
    }
    private void mostrarTarjeta() {
        this.stage.show();
    }
}
