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
import org.tuvarna.service.TripService;

import java.util.ArrayList;
import java.util.List;

public class AdministratorController {
    @FXML
    public TextField companyName;

    private CompanyService companyService;

    //    public void createCompany(Session session, String name) {
//        session.beginTransaction();
//        List<Company> companyList = session.createQuery("from Company where Company.name=name").getResultList();
//        if(companyList.isEmpty()) {
//            List<Trip> trips = new ArrayList<Trip>();
//            session.persist(new Company(name, trips));
//        }  else {
//            System.out.println("this company already exists");
//        }
//        session.getTransaction().commit();
//    }
//
//    public void createDistributor(Session session, String name) {
//        session.beginTransaction();
//        List<Distributor> distributors = session.createQuery("from Distributor where Distributor.name=name").getResultList();
//        if(distributors.isEmpty()) {
//            List<Cashier> cashiers = new ArrayList<>();
//            session.persist(new Distributor(name, cashiers));
//        }  else {
//            System.out.println("this company already exists");
//        }
//        session.getTransaction().commit();
//    }
//
//    public void deleteCompany(Session session, String name) {
//        session.beginTransaction();
//        session.createQuery("delete from Company where Company.name=name").executeUpdate();
//        session.getTransaction().commit();
//    }
//
//    public void deleteDistributor(Session session, String name) {
//        session.beginTransaction();
//        session.createQuery("delete from Distributor where Distributor.name=name").executeUpdate();
//        session.getTransaction().commit();
//    }
//

    public void insertCompany(ActionEvent actionEvent) {
        String companyName = this.companyName.getText();
        companyService.addCompany(new Company(companyName));
    }

    public void insertDistributor(ActionEvent actionEvent) {

    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }
}