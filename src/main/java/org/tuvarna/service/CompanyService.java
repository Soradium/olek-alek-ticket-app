package org.tuvarna.service;

import org.tuvarna.entity.Company;
import org.tuvarna.factories.FactoryDAO;
import org.tuvarna.repository.TableDAO;

import java.util.List;

public class CompanyService {

    private final TableDAO<Company> companyDAO;

    public CompanyService() {
        this.companyDAO = FactoryDAO.getInstance().getDao(Company.class);
    }

    public List<Company> getAllCompanies() {
        return companyDAO.findAll();
    }

    public Company getCompanyByName(String name) {
        return companyDAO.findAll().stream()
                .filter(c -> c.getName().equals(name))
                .findFirst().orElse(null);
    }

    public Company getCompanyById(int id) {
        return companyDAO.findById(id);
    }

    public Company addCompany(Company company) {
        return companyDAO.save(company);
    }
}
