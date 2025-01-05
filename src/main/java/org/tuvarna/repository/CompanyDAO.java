package org.tuvarna.repository;

import org.tuvarna.entity.Company;

import java.util.List;

public interface CompanyDAO {
    Company getCompanyById(int id);
    List<Company> getCompanies();
    Company addCompany(Company company);
}
