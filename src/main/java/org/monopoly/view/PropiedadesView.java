package org.monopoly.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.monopoly.model.Config;
import org.monopoly.model.Jugador;
import org.monopoly.model.RegistroComprables;
import org.monopoly.model.casilla.Comprable;
import org.monopoly.model.casilla.Propiedad;
import java.io.IOException;

public class PropiedadesView {

    private VBox tarjeta;

    private VBox propiedades;

    private Jugador jugador;

    private RegistroComprables registroComprables;

    private Stage stage;

    private Scene scene;

    public PropiedadesView(Jugador jugador, RegistroComprables registroComprables) {
        this.jugador = jugador;
        this.registroComprables = registroComprables;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/propiedades.fxml"));
            this.tarjeta = fxmlLoader.load();
            this.propiedades = (VBox) this.tarjeta.getChildren().get(1);
        } catch (IOException e) {}


        this.scene = new Scene(this.getPropiedades());
        this.stage = new Stage();
        this.stage.setScene(this.scene);
        this.stage.resizableProperty().setValue(true);
    }

    public void actualizarPropiedades() {
        this.propiedades.getChildren().clear();
        int height = 0;
        for (Comprable comprable : registroComprables.getTablaPropiedades().keySet()){
            if (registroComprables.obtenerDuenio(comprable) == this.jugador) {
                Label color = new Label();
                color.setBackground(new Background(new BackgroundFill(Paint.valueOf(comprable.getColor().toString()), null, null)));
                Label propiedad = new Label("| "+ comprable.getNombre().toUpperCase() + " |\t   | HIPOTECADA: " + ((comprable.estaHipotecada()) ? " SI |" : " NO |\t  | ALQUILER: " + comprable.getValorAlquiler()+" |" ));
                if (comprable.getTipo() == Config.TiposCasillas.PROPIEDAD)
                    propiedad.setText(propiedad.getText() + "\t| CONST.: " + ((Propiedad)comprable).getCantConstruidos() +" |" );
                color.setStyle("-fx-min-width: 80; -fx-min-height: 50;-fx-border-color: grey; -fx-border-width: 2px;");
                propiedad.setStyle("-fx-min-width: 616; -fx-min-height: 50;-fx-border-color: grey; -fx-border-width: 2px;");
                HBox marco = new HBox(color, propiedad);
                marco.setStyle("-fx-border-color: grey; -fx-border-width: 2px;");

                this.propiedades.getChildren().add(marco);
                height += 50;
            }
        }
        this.stage.setMinHeight(height + 120);
    }

    public VBox getPropiedades() {
        return tarjeta;
    }

    public void mostrarPropiedades(){
        this.stage.show();
    }
    public void cerrarPropiedades(){
        this.stage.close();
    }
}
