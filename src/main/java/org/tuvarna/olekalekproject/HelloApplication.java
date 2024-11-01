package org.tuvarna.olekalekproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.tuvarna.entity.Company;
import org.tuvarna.entity.Trip;


import java.io.IOException;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        Session session = new Configuration().
                configure("hibernate.cfg.xml")
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(Trip.class)
                .buildSessionFactory().
                getCurrentSession();


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 600, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        HelloController controller = fxmlLoader.getController();
        controller.setSession(session);
    }

    public static void main(String[] args) {
        launch();
    }
}