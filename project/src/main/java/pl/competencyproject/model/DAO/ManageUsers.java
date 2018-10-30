package pl.competencyproject.model.DAO;

import org.hibernate.*;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.connection.SessionFactoryConfig;
import pl.competencyproject.model.entities.User;

import javax.persistence.TypedQuery;
import java.util.List;

public class ManageUsers {

    private static SessionFactory factory;

    public ManageUsers() {
        try {
            factory = SessionFactoryConfig.getSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /* Method to CREATE an user in the database */
    public Integer addUser(String email, String password, boolean active) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer idUser = null;

        try {
            tx = session.beginTransaction();
            User user = new User(email, password, active);
            idUser = (Integer) session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return idUser;
    }

    /* Method to  READ all the users */
   /* public List<User> listUsers( ){
        Session session = factory.openSession();
        Transaction tx = null;
        List users = null;
        try {
            tx = session.beginTransaction();
             users = session.createQuery("FROM USERS").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users;
    }
    */

    /* Method to UPDATE salary for an employee */
    public void updateUser(Integer UserID, String password ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            User user = (User)session.get(User.class, UserID);
            user.setPassword( password );
            session.update(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to DELETE an user from the records */
    public void deleteUser(Integer UserID){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            User user = (User)session.get(User.class, UserID);
            session.delete(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
/*
    public boolean existUser(String email, String password)throws HibernateException{
        Session session = factory.openSession();
        NativeQuery query = session.createSQLQuery("SELECT * FROM USERS u WHERE u.email ='" + email + "'",User.class);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);


    }
*/

}
