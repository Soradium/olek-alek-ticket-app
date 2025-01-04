package org.tuvarna.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Company;

import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {
    private final SessionFactory sessionFactory;

    public CompanyDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Company getCompanyByName(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Company company = session.get(Company.class, name);
        session.getTransaction().commit();
        session.close();
        return company;

    }

    @Override
    public List<Company> getCompanies() {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Company> companyList = session.createQuery("from Company").getResultList();
        session.getTransaction().commit();
        session.close();
        return companyList;
    }

    @Override
    public void addCompany(Company company) {
        sessionFactory.getCurrentSession().persist(company);
    }
}
