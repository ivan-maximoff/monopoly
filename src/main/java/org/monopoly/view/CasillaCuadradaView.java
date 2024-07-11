package org.monopoly.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import org.monopoly.model.Config;
import org.monopoly.model.Jugador;
import org.monopoly.model.casilla.Casilla;
import org.monopoly.model.casilla.Comprable;

import java.util.List;

public class CasillaCuadradaView extends CasillaView {
    public CasillaCuadradaView(Casilla casilla, List<Jugador> jugadores) {
        super(casilla, jugadores);
    }
    protected int getInfoMaxHeight() {
        return 100;
    }
    protected int getInfoMaxWidth() {
        return 100;
    }

    protected Node getCasillaView(VBox jugadores, Label info){
        VBox infoBox = new VBox();
        return infoBox;
    }
    @Override
    public Node crearCasilla(Casilla casilla) {
        VBox jugadores = crearContenedorJugadores();
        HBox cView = new HBox(jugadores);
        cView.setAlignment(Pos.CENTER);
        cView.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        cView.setOnMouseClicked(e -> {
            this.mostrarTarjeta();
        });
        HBox.setHgrow(cView, Priority.ALWAYS);
        if (casilla.getTipo() == Config.TiposCasillas.PROPIEDAD || casilla.getTipo() == Config.TiposCasillas.ESTACION){
            Label info = new Label();
            info.setAlignment(Pos.CENTER);
            info.setMinSize(50, 50);
            info.setBackground(new Background(new BackgroundFill(Paint.valueOf(((Comprable) casilla).getColor().toString()), null, null)));
            cView.getChildren().add(info);
        }
        else if (casilla.getTipo() == Config.TiposCasillas.MULTA || casilla.getTipo() == Config.TiposCasillas.LOTERIA){
            Label img = new Label();
            ImageView imageView = new ImageView(new Image(this.getClass().getResourceAsStream("/images/MONETARIA.png")));
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            img.setGraphic(imageView);
            StackPane stackPane = new StackPane(img, cView);
            HBox.setHgrow(stackPane, Priority.ALWAYS);
            VBox.setVgrow(stackPane, Priority.ALWAYS);
            return stackPane;
        }else {
            Label img = new Label();
            ImageView imageView = new ImageView(new Image(this.getClass().getResourceAsStream("/images/" + casilla.getTipo().toString() + ".png")));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            img.setGraphic(imageView);
            img.setMaxSize(100, 100);

            StackPane stackPane = new StackPane(img, cView);
            return stackPane;
        }
        return cView;
    }
    private void mostrarTarjeta() {
        this.stage.show();
    }
}
