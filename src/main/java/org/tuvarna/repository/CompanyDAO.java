package org.tuvarna.repository;

import org.tuvarna.entity.Company;

import java.util.List;

public interface CompanyDAO {
    Company getCompanyByName(String name);
    List<Company> getCompanies();
    void addCompany(Company company);
}
