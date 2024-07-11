package org.monopoly.view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.monopoly.model.Config;
import org.monopoly.model.Jugador;
import org.monopoly.model.casilla.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class CasillaView {
    private VBox tarjetaCasilla;

    private Label info;

    private List<ImageView> imgJugadores = new ArrayList<>(){{
        add(new ImageView());
        add(new ImageView());
        add(new ImageView());
        add(new ImageView());
    }};


    private Label valor;

    protected Stage stage;

    protected Casilla casilla;

    public CasillaView(Casilla casilla, List<Jugador> jugadores) {
        VBox tarjeta = new VBox();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/casilla.fxml"));
            tarjeta = fxmlLoader.load();
            this.info = (Label) tarjeta.getChildren().get(0);
            this.valor = (Label) tarjeta.getChildren().get(1);
            this.valor.setFont(new Font("Arial", 30));
            this.casilla = casilla;
            if (casilla.getTipo() == Config.TiposCasillas.PROPIEDAD || casilla.getTipo() == Config.TiposCasillas.ESTACION) this.setComprable((Comprable) casilla);
            else if (casilla.getTipo() == Config.TiposCasillas.MULTA || casilla.getTipo() == Config.TiposCasillas.LOTERIA) this.setMonetaria((Monetaria) casilla);
            else valor.setText(casilla.getTipo().toString());
            this.tarjetaCasilla = tarjeta;

        } catch (IOException e) {}
        this.setJugadores(jugadores);
        Scene escena = new Scene(this.getTarjetaCasilla());
        Stage stage = new Stage();
        stage.setScene(escena);
        this.stage = stage;
    }

    public void setJugadores(List<Jugador> jugadores) {
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getCasillaActual() == this.casilla)
                this.imgJugadores.get(i).setImage(new Image(getClass().getResourceAsStream("/images/"+jugadores.get(i).getColor()+".png")));
        }
    }

    public void setComprable(Comprable comprable){
        this.info.setBackground(new Background(new BackgroundFill(Paint.valueOf(comprable.getColor().toString()), null, null)));
        this.valor.setText(comprable.getNombre()+ "\n\nValor Compra: "+comprable.getValorCompra() + "\n\nValor Hipoteca: " + comprable.getValorHipoteca());
        if (comprable.getTipo() == Config.TiposCasillas.PROPIEDAD) this.valor.setText(this.valor.getText() + "\n\nValor Construir: " + ((Propiedad) comprable).getValorConstruir());
    }

    public void setMonetaria(Monetaria monetaria){
        String text = ((monetaria.getTipo() == Config.TiposCasillas.MULTA) ? "MULTA" : "LOTERIA") + "\n" +
                (((monetaria.getTipo() == Config.TiposCasillas.MULTA) ? "PAGAS: " : "GANAS: ")+ monetaria.getMonto());
        this.valor.setText(text);
    }

    public VBox getTarjetaCasilla() {
        return tarjetaCasilla;
    }

    public Node crearCasilla(Casilla casilla){
        Label info = new Label();
        info.setAlignment(Pos.CENTER);
        info.setMaxSize(getInfoMaxHeight(), getInfoMaxWidth());
        HBox.setHgrow(info, Priority.ALWAYS);
        VBox.setVgrow(info, Priority.ALWAYS);

        VBox jugadores = crearContenedorJugadores();
        Node cView = getCasillaView(jugadores, info);
        cView.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        cView.setOnMouseClicked(e -> {
            this.mostrarTarjeta();
        });
        HBox.setHgrow(cView, Priority.ALWAYS);
        VBox.setVgrow(cView, Priority.ALWAYS);
        if (casilla.getTipo() == Config.TiposCasillas.PROPIEDAD || casilla.getTipo() == Config.TiposCasillas.ESTACION)
            info.setBackground(new Background(new BackgroundFill(Paint.valueOf(((Comprable)casilla).getColor().toString()), null, null)));
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
        } else {
            Label img = new Label();
            ImageView imageView = new ImageView(new Image(this.getClass().getResourceAsStream("/images/"+casilla.getTipo().toString()+".png")));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            img.setGraphic(imageView);
            img.autosize();
            img.setMaxSize(70,70);

            StackPane stackPane = new StackPane(img, cView);
            HBox.setHgrow(stackPane, Priority.ALWAYS);
            VBox.setVgrow(stackPane, Priority.ALWAYS);
            return stackPane;
        }
        return cView;
    }

    protected abstract int getInfoMaxHeight();
    protected abstract int getInfoMaxWidth();
    protected abstract Node getCasillaView(VBox jugadores, Label info);

    protected VBox crearContenedorJugadores() {
        List<ImageView> miniImgViews = new ArrayList<>(this.imgJugadores);
        for (ImageView imageView : miniImgViews) {
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
        }
        HBox jugadores1 = new HBox(miniImgViews.get(0), miniImgViews.get(1));
        HBox jugadores2 = new HBox(miniImgViews.get(2), miniImgViews.get(3));
        jugadores1.setSpacing(5);
        jugadores2.setSpacing(5);
        jugadores1.setAlignment(Pos.CENTER);
        jugadores2.setAlignment(Pos.CENTER);
        VBox contenedor = new VBox(jugadores1, jugadores2);
        contenedor.setAlignment(Pos.CENTER);

        return contenedor;
    }

    private void mostrarTarjeta() {
        this.stage.show();
    }

}
