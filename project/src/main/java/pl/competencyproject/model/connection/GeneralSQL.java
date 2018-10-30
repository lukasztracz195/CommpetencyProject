package pl.competencyproject.model.connection;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.competencyproject.model.entities.User;

public class GeneralSQL {

    private static SessionFactory factory;
    private SessionFactory sessionFactory;

    public GeneralSQL() {
        sessionFactory = SessionFactoryConfig.getSessionFactory();


    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Integer addUser(User users) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Integer userId = null;

        try {
            tx = session.beginTransaction();
            userId = (Integer) session.save(users);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userId;
    }

}