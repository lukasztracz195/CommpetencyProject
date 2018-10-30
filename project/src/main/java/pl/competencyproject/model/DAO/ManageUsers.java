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
    public Integer addUser(String email, String password) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer idUser = null;

        try {
            tx = session.beginTransaction();
            User user = new User(email, password, true);
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


    /* Method to UPDATE password for an user */
    public void updatePasswordUser(Integer UserID, String password ){
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

    /* Method to UPDATE active status for an user */
    public void updateActiveUser(Integer UserID, boolean active ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            User user = (User)session.get(User.class, UserID);
            user.setActive(active);
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

    public int existUser(String email)throws HibernateException{
        Session session = factory.openSession();
        NativeQuery query = session.createSQLQuery("SELECT * FROM USERS WHERE email =  :email");
        query.addEntity(User.class);
        query.setParameter("email",email);
        List result = query.list();
        if(result .size() != 0) {
            User user = (User) result.get(0);
            return user.getIdUser();
        }
        return -1;

    }

    public boolean checkUserPassword(int IdUser, String password){
        Session session = factory.openSession();
        NativeQuery query = session.createSQLQuery("SELECT * FROM USERS WHERE idUser =  :idUser");
        query.addEntity(User.class);
        query.setParameter("idUser",IdUser).getFirstResult();
        List result = query.list();
        User user = (User) result.get(0);
        if(user.getPassword().equals(password)) return true;
        return false;


    }


}
