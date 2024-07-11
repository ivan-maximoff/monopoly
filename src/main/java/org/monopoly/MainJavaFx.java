package org.monopoly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.monopoly.controller.SeleccionadorController;


public class MainJavaFx extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception{
        MainJavaFx.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/escena-inicio.fxml"));
        AnchorPane root = loader.load();

        SeleccionadorController juegoController = loader.getController();
        juegoController.setSeleccionador();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Monopoly");
        stage.setMaxHeight(400);
        stage.setResizable(true);
        stage.show();
    }

    public static void resizeStage(){stage.setMinHeight(630);}

    public static void closeStage(){
        stage.close();
    }
}
