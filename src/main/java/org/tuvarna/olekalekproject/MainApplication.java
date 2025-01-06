package org.tuvarna.olekalekproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.tuvarna.controller.AdministratorController;
import org.tuvarna.controller.CompanyController;
import org.tuvarna.controller.UserController;
import org.tuvarna.entity.*;
import org.tuvarna.repository.BusDAOImpl;
import org.tuvarna.repository.CompanyDAOImpl;
import org.tuvarna.repository.TripDAOImpl;
import org.tuvarna.service.BusService;
import org.tuvarna.service.CompanyService;
import org.tuvarna.service.TripService;

import java.io.IOException;

public class MainApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-pane.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}