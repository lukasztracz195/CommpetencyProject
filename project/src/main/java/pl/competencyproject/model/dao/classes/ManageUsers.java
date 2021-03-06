package pl.competencyproject.model.dao.classes;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import pl.competencyproject.model.dao.interfaces.ManagingUsers;
import pl.competencyproject.model.entities.User;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ManageUsers extends GeneralManager implements ManagingUsers {

    public ManageUsers(TypeOfUsedDatabase type) {
        super(type);
    }
    public static final String TABLE = "USERS";

    /* Method to CREATE an user in the database */
    public synchronized Integer addUser(String email, String password) {
        Transaction tx = null;
        Integer idUser = -1;

        if (this.existUser(email) == -1) {
            try {
                reset();
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
    public synchronized void updatePasswordUser(Integer UserID, String password) {
        Transaction tx = null;
        try {
            reset();
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
    public synchronized void updateActiveUser(Integer UserID, boolean active) {
        Transaction tx = null;

        try {
            reset();
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
    public synchronized void deleteUser(Integer UserID) {
        Transaction tx = null;
        try {
            reset();
            tx = session.beginTransaction();
            User user = session.get(User.class, UserID);
            session.delete(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public synchronized int existUser(String email) throws HibernateException {

        User user = null;
        int id = -1;
        user = createQueryForGetUser(user, email);
        if (user != null) {
            id = user.getIdUser();
        }
        return id;

    }

    public synchronized void updateEmail(Integer UserID, String email) {
        Transaction tx = null;
        try {
            reset();
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

    public synchronized User getUser(String email) {

        User user = null;
        user = createQueryForGetUser(user, email);
        return user;
    }

    private synchronized User createQueryForGetUser(User user, String email) {
        reset();
        NativeQuery query = session.createSQLQuery("SELECT * FROM USERS WHERE email =  :email");
        query.addEntity(User.class);
        query.setParameter("email", email);
        List result = query.list();
        if (result.size() != 0) {
            user = (User) result.get(0);
        }
        return user;
    }

    public synchronized User getUser(int IdUser) {
        Transaction tx = null;
        User user = null;
        try {
            reset();
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

    public synchronized String getPassword(int IdUser) {
        User user = getUser(IdUser);
        return user.getPassword();
    }


    public synchronized boolean checkUserPassword(int IdUser, String password) {

        User user = getUser(IdUser);
        String passwordUser = encryptSHA1(password);
        return user.getPassword().equals(passwordUser);
    }

    public synchronized boolean checkUserEmail(int IdUser, String email) {
        User user = getUser(IdUser);
        return user.getEmail().equals(email);
    }


    public synchronized boolean checkLogedUser(int IdUser) {

        User user = getUser(IdUser);
        if (user.isActive()) return true;
        return false;
    }

    public synchronized String encryptSHA1(String password) {
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
