package org.tuvarna.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Company;

import java.util.List;

public class CompanyDAOImpl implements TableDAO<Company> {
    private final SessionFactory sessionFactory;

    public CompanyDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Company findById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            Company company = session.get(Company.class, id);
            session.getTransaction().commit();
            return company;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Company> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            List<Company> companyList = session.createQuery("from Company", Company.class).getResultList();
            session.getTransaction().commit();
            return companyList;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Company save(Company company) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            session.persist(company);
            session.getTransaction().commit();
            return company;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
