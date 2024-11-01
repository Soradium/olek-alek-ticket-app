package org.tuvarna.olekalekproject;

import jakarta.persistence.Query;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.tuvarna.entity.Administrator;
import org.tuvarna.entity.Company;

import java.util.List;

public class HelloController {
    @FXML
    private TextField nameText;

    @FXML
    private Label welcomeText;

    private Session session;

    public HelloController() {
    }

    Administrator admin = new Administrator();

    public void setSession(Session session) {
        this.session = session;
    }

    @FXML
    protected void onHelloButtonClick() {
        session.beginTransaction();
        Query query = session.createQuery("from Company c WHERE c.name = :name");
        query.setParameter("name", nameText.getText());
        List<Company> companyList = query.getResultList();
        if(companyList.isEmpty()) {
            admin.createCompany(session, new Company(nameText.getText()));
        }
    }
}