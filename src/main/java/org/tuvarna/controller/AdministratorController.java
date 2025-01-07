package org.tuvarna.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.tuvarna.entity.*;
import org.tuvarna.observer.Observer;
import org.tuvarna.observer.Subject;
import org.tuvarna.service.CompanyService;
import org.tuvarna.service.DistributorService;
import org.tuvarna.service.TripService;
import org.tuvarna.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class AdministratorController implements Subject {
    @FXML
    public TextField companyName;
    @FXML
    public TextField distributorName;
    @FXML
    public TextField userName;

    private CompanyService companyService;
    private DistributorService distributorService;
    private UserService userService;

    private Observer observer;

    private String lastAdded;
    private String lastAddedType;

    public void insertCompany(ActionEvent actionEvent) {
        String companyName = this.companyName.getText();
        companyService.addCompany(new Company(companyName));
        lastAdded = companyName;
        lastAddedType = "Companies";
        notifyObservers();
    }

    public void insertDistributor(ActionEvent actionEvent) {
        String distributorName = this.distributorName.getText();
        Distributor distributor = new Distributor();
        distributor.setName(distributorName);
        distributorService.addDistributor(distributor);
        lastAdded = distributorName;
        lastAddedType = "Distributors";
        notifyObservers();
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void setDistributorService(DistributorService distributorService) {
        this.distributorService = distributorService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observer = observer; //main controller
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observer = null;
    }

    @Override
    public void notifyObservers() {
        List<String> valueAndType = new ArrayList<>();
        valueAndType.add(lastAdded);
        valueAndType.add(lastAddedType);
        this.observer.update(valueAndType);
    }

    public void insertUser() {
        String userNameText = this.userName.getText();
        userService.addUser(new User(userNameText));
        lastAdded = userNameText;
        lastAddedType = "Users";
        notifyObservers();
    }
}