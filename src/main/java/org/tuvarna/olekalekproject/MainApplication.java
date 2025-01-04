package org.tuvarna.olekalekproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.tuvarna.controller.MenuStripController;
import org.tuvarna.entity.Company;
import org.tuvarna.entity.Trip;

import java.io.IOException;
import java.util.ArrayList;

public class MainApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        ArrayList<String> a = new ArrayList<>();

        Session session = new Configuration().
                configure("hibernate.cfg.xml")
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(Trip.class)
                .buildSessionFactory().
                getCurrentSession();

        // create all controller classes here
        a.add("Hello World");
        MenuStripController menuStripController = new MenuStripController
                .MenuStripControllerBuilder()
                .withCashiers("ab", "cd")
                .withCashiers(a)
                .build();

        BorderPane root = new BorderPane();
        root.setTop(menuStripController.getMenuBar());
        Scene scene = new Scene(root, 600, 800);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}