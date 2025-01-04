package org.tuvarna.olekalekproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        ArrayList<String> a = new ArrayList<>();
        a.add("Hello World");
        MenuStrip menuStrip = new MenuStrip
                .MenuStripControllerBuilder()
                .withCashiers("ab", "cd")
                .withCashiers(a)
                .build();

        BorderPane root = new BorderPane();
        root.setTop(menuStrip.getMenuBar());
        Scene scene = new Scene(root, 600, 800);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}