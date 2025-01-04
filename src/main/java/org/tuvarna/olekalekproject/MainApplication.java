package org.tuvarna.olekalekproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.tuvarna.entity.Trip;

import java.io.IOException;

public class MainApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("company.fxml"));

        Session session = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Trip.class)
                .buildSessionFactory()
                .getCurrentSession();

        Scene scene = new Scene(fxmlLoader.load(), 600, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        CompanyController companyController = fxmlLoader.getController();
        companyController.setSession(session);
    }

    public static void main(String[] args) {
        launch();
    }
}