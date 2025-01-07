package org.tuvarna.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.User;

import java.util.List;

public class UserDAOImpl implements TableDAO<User> {
    private final SessionFactory sessionFactory;

    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            User user = session.get(User.class, id);
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            List<User> userList = session.createQuery("from User", User.class).getResultList();
            session.getTransaction().commit();
            return userList;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public User save(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            session.persist(user);
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public User update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            session.merge(user);
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
