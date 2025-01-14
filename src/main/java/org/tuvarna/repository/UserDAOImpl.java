package org.tuvarna.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.User;

import java.util.List;

public class UserDAOImpl implements TableDAO<User> {

    private final SessionFactory sessionFactory;

    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private final static Logger logger = LogManager.getLogger(UserDAOImpl.class);

    @Override
    public User findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for findById");
        try {
            User user = session.get(User.class, id);
            logger.info("Trip with id {} fetched successfully", id);
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return user;
        } catch (Exception e) {
            logger.error("Occurred with error in findByAll method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollback successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for findAll");
        try {
            List<User> userList = session.createQuery("from User", User.class).getResultList();
            logger.info("Users fetched successfully: {}", userList.size());
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return userList;
        } catch (Exception e) {
            logger.error("Occurred with error in findAll method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollback successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public User save(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for save");
        try {
            session.persist(user);
            logger.info("User {} saved successfully", user);
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return user;
        } catch (Exception e) {
            logger.error("Occurred with error in save method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollback successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public User update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for update");
        try {
            session.merge(user);
            logger.info("User {} updated successfully", user);
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return user;
        } catch (Exception e) {
            logger.error("Occurred with error in update method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollback successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }
}
