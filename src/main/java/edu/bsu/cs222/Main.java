package edu.bsu.cs222;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.net.URI;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URI("file:src/main/resources/movieFinderUI.fxml").toURL());
        BorderPane pane = loader.load();

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
