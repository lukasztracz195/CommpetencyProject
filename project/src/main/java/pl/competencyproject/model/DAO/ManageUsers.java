package pl.competencyproject.model.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.connection.SessionFactoryConfig;
import pl.competencyproject.model.entities.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ManageUsers {


    /* Method to CREATE an user in the database */


    public static int addUser(String email, String password) {
        Transaction tx = null;
        Integer idUser = -1;

        if (ManageUsers.existUser(email) == -1) {
            org.hibernate.Session session = SessionFactoryConfig.getSessionFactory().openSession();
            try {
                tx = session.beginTransaction();
                String passwordEncrypted = encryptSHA1(password);
                User user = new User(email, passwordEncrypted, false);
                idUser = (Integer) session.save(user);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return idUser;
    }


    /* Method to UPDATE password for an user */
    public static void updatePasswordUser(Integer UserID, String password) {
        Transaction tx = null;

        try (Session session = SessionFactoryConfig.getSessionFactory().openSession()) {

            tx = session.beginTransaction();
            User user = session.get(User.class, UserID);
            String passwordEncrypted = encryptSHA1(password);
            user.setPassword(passwordEncrypted);
            session.update(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    /* Method to UPDATE active status for an user */
    public static void updateActiveUser(Integer UserID, boolean active) {
        Session session = SessionFactoryConfig.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, UserID);
            user.setActive(active);
            session.update(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to DELETE an user from the records */
    public static void deleteUser(Integer UserID) {
        Session session = SessionFactoryConfig.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, UserID);
            session.delete(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static int existUser(String email) throws HibernateException {

        Session session = factory.openSession();
        User user = null;
        int id = -1;
        NativeQuery query = session.createSQLQuery("SELECT * FROM USERS WHERE email =  :email");
        query.addEntity(User.class);
        query.setParameter("email", email);
        List result = query.list();
        if (result.size() != 0) {
            user = (User) result.get(0);
        }
        if(user != null) { id = user.getIdUser(); }
        return id;

    }

    public static void updateEmail(Integer UserID, String email) {
        Session session = SessionFactoryConfig.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            User user = session.get(User.class, UserID);
            user.setEmail(email);
            session.update(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public static User getUser(String email) {
        Session session = SessionFactoryConfig.getSessionFactory().openSession();
        User user = null;
        NativeQuery query = session.createSQLQuery("SELECT * FROM USERS WHERE email =  :email");
        query.addEntity(User.class);
        query.setParameter("email", email);
        List result = query.list();
        if (result.size() != 0) {
            user = (User) result.get(0);
        }
        return user;
    }

    public static User getUser(int IdUser) {
        Session session = SessionFactoryConfig.getSessionFactory().openSession();
        Transaction tx = null;
        User user= null;
        try {
            tx = session.beginTransaction();
             user = (User) session.get(User.class, IdUser);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    public static String getPassword(int IdUser) {
        User user = getUser(IdUser);
        return user.getPassword();
    }




    public static boolean checkUserPassword(int IdUser, String password) {

        User user = getUser(IdUser);
        String passwordUser = encryptSHA1(password);
        return user.getPassword().equals(passwordUser);
    }



    public static boolean checkLogedUser(int IdUser) {

        User user = getUser(IdUser);
        if (user.isActive()) return true;
        return false;
    }

    public static String encryptSHA1(String password) {
        MessageDigest mDigest = null;
        StringBuffer sb = new StringBuffer();
        try {
            mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(password.getBytes());

            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}
