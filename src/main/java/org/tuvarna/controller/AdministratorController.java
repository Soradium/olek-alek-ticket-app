package org.tuvarna.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.tuvarna.entity.Cashier;
import org.tuvarna.entity.Company;
import org.tuvarna.entity.Distributor;
import org.tuvarna.entity.Trip;
import org.tuvarna.service.CompanyService;
import org.tuvarna.service.DistributorService;
import org.tuvarna.service.TripService;

import java.util.ArrayList;
import java.util.List;

public class AdministratorController {
    @FXML
    public TextField companyName;
    @FXML
    public TextField distributorName;

    private CompanyService companyService;
    private DistributorService distributorService;

    public void insertCompany(ActionEvent actionEvent) {
        String companyName = this.companyName.getText();
        companyService.addCompany(new Company(companyName));
    }

    public void insertDistributor(ActionEvent actionEvent) {
        String distributorName = this.distributorName.getText();
        Distributor distributor = new Distributor();
        distributor.setName(distributorName);
        distributorService.addDistributor(distributor);
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void setDistributorService(DistributorService distributorService) {
        this.distributorService = distributorService;
    }
}