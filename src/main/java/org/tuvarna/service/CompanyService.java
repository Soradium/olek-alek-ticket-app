package org.tuvarna.service;

import org.hibernate.Session;
import org.tuvarna.entity.*;
import org.tuvarna.repository.CompanyDAO;

import java.util.ArrayList;
import java.util.List;

public class CompanyService {

    private final CompanyDAO companyDAO;

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

    public Company addCompany(Company company) {
        return companyDAO.addCompany(company);
    }
}
