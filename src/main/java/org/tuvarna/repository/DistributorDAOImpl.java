package org.tuvarna.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Distributor;

import java.util.List;

public class DistributorDAOImpl implements TableDAO<Distributor> {

    private final SessionFactory sessionFactory;

    public DistributorDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private final static Logger logger = LogManager.getLogger(DistributorDAOImpl.class);

    @Override
    public Distributor findById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for findById");
        try {
            Distributor distributor = session.get(Distributor.class, id);
            logger.info("Distributor with id {} fetched successfully", id);
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return distributor;
        } catch (Exception e) {
            logger.error("Occurred error in method findById, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollbacked successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public List<Distributor> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for findAll");
        try {
            List<Distributor> distributorList = session.createQuery("from Distributor", Distributor.class).getResultList();
            logger.info("Distributors fetched successfully: {}", distributorList.size());
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return distributorList;
        } catch (Exception e) {
            logger.error("Occurred error in method findAll, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.error("Transaction rollbacked successfully");
        } finally {
            session.close();
            logger.info("Transaction closed successfully");
        }
        return null;
    }

    @Override
    public Distributor save(Distributor distributor) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for save");
        try {
            session.persist(distributor);
            logger.info("Distributor {} saved successfully", distributor.getId());
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return distributor;
        } catch (Exception e) {
            logger.error("Occurred error in method save, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollbacked successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public Distributor update(Distributor distributor) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for update");
        try {
            session.merge(distributor);
            logger.info("Distributor {} updated successfully", distributor.getId());
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return distributor;
        } catch (Exception e) {
            logger.error("Occurred error in method update, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollbacked successfully");
        } finally {
            session.close();
            logger.info("Transaction closed successfully");
        }
        return null;
    }
}
