package org.tuvarna.service;

import org.hibernate.Session;
import org.tuvarna.entity.Cashier;
import org.tuvarna.entity.Company;
import org.tuvarna.entity.Distributor;
import org.tuvarna.entity.Trip;
import org.tuvarna.repository.CompanyDAO;

import java.util.ArrayList;
import java.util.List;

public class CompanyService {
    private CompanyDAO companyDAO;

    public CompanyService(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public List<Company> getAllCompanies() {
        return companyDAO.getCompanies();
    }

    public Company getCompanyByName(String name) {
        return companyDAO.getCompanies().stream()
                .filter(c -> c.getName().equals(name))
                .findFirst().orElse(null);
    }

    public Company getCompanyById(int id) {
        return companyDAO.getCompanyById(id);
    }

    public void addCompany(Company company) {
        companyDAO.addCompany(company);
    }

    // RE-MAKE IT ALL !!!!! ///
    public void createCompany(Session session, String name) {
        session.beginTransaction();
        List<Company> companyList = session.createQuery("from Company where Company.name=name").getResultList();
        if(companyList.isEmpty()) {
            List<Trip> trips = new ArrayList<Trip>();
            session.persist(new Company(name, trips));
        }  else {
            System.out.println("this company already exists");
        }
        session.getTransaction().commit();
    }

    public void createDistributor(Session session, String name) {
        session.beginTransaction();
        List<Distributor> distributors = session.createQuery("from Distributor where Distributor.name=name").getResultList();
        if(distributors.isEmpty()) {
            List<Cashier> cashiers = new ArrayList<>();
            session.persist(new Distributor(name, cashiers));
        }  else {
            System.out.println("this company already exists");
        }
        session.getTransaction().commit();
    }

    public void deleteCompany(Session session, String name) {
        session.beginTransaction();
        session.createQuery("delete from Company where Company.name=name").executeUpdate();
        session.getTransaction().commit();
    }

    public void deleteDistributor(Session session, String name) {
        session.beginTransaction();
        session.createQuery("delete from Distributor where Distributor.name=name").executeUpdate();
        session.getTransaction().commit();
    }
}
