package org.tuvarna.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Company;

import java.util.List;

public class CompanyDAOImpl implements TableDAO<Company> {

    private final SessionFactory sessionFactory;

    public CompanyDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private final static Logger logger = LogManager.getLogger(CompanyDAOImpl.class);

    @Override
    public Company findById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for findById");
        try {
            Company company = session.get(Company.class, id);
            logger.info("Company with id {} fetched successfully", id);
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return company;
        } catch (Exception e) {
            logger.error("Occurred error in findById method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollback successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public List<Company> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for findAll");
        try {
            List<Company> companyList = session.createQuery("from Company", Company.class).getResultList();
            logger.info("Companies fetched successfully: {}", companyList.size());
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return companyList;
        } catch (Exception e) {
            logger.error("Occurred error in findAll method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollback successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public Company save(Company company) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for save");
        try {
            session.persist(company);
            logger.info("Company {} saved successfully", company);
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return company;
        } catch (Exception e) {
            logger.error("Occurred error in save method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollback successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public Company update(Company company) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for update");
        try {
            session.merge(company);
            logger.info("Company {} updated successfully", company);
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return company;
        } catch (Exception e) {
            logger.error("Occurred error in update method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollback successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }
}
