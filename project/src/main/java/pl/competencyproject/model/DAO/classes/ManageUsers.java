package pl.competencyproject.model.DAO.classes;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.DAO.interfaces.ManagingUsers;
import pl.competencyproject.model.entities.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ManageUsers extends GeneralManager implements ManagingUsers {

    private static ManageUsers instance;

    public ManageUsers(boolean test) {
        super(test);
    }

    public static final String TABLE = "USERS";
    public static ManageUsers getInstance() {
        if (instance == null) {
            synchronized (ManageUsers.class) {
                if (instance == null) {
                    instance = new ManageUsers(false);
                }
            }
        }
        return instance;
    }

    public static ManageUsers getTestInstance() {
        if (instance == null) {
            synchronized (ManageUsers.class) {
                if (instance == null) {
                    instance = new ManageUsers(true);
                }
            }
        }
        return instance;
    }



    /* Method to CREATE an user in the database */
    public Integer addUser(String email, String password) {
        Transaction tx = null;
        Integer idUser = -1;

        if (this.existUser(email) == -1) {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
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
    public void updatePasswordUser(Integer UserID, String password) {
        Transaction tx = null;

        try {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
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
    public void updateActiveUser(Integer UserID, boolean active) {
        Transaction tx = null;

        try {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, UserID);user.setActive(active);
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
    public void deleteUser(Integer UserID) {
        Transaction tx = null;
        try {
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

    public int existUser(String email) throws HibernateException {

        User user = null;
        int id = -1;
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        NativeQuery query = session.createSQLQuery("SELECT * FROM USERS WHERE email =  :email");
        query.addEntity(User.class);
        query.setParameter("email", email);
        List result = query.list();
        if (result.size() != 0) {
            user = (User) result.get(0);
        }
        if (user != null) {
            id = user.getIdUser();
        }
        return id;

    }

    public void updateEmail(Integer UserID, String email) {
        Transaction tx = null;
        try {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
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

    public User getUser(String email) {

        User user = null;
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        NativeQuery query = session.createSQLQuery("SELECT * FROM USERS WHERE email =  :email");
        query.addEntity(User.class);
        query.setParameter("email", email);
        List result = query.list();
        if (result.size() != 0) {
            user = (User) result.get(0);
        }
        return user;
    }

    public User getUser(int IdUser) {
        Transaction tx = null;
        User user = null;
        try {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
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

    public String getPassword(int IdUser) {
        User user = getUser(IdUser);
        return user.getPassword();
    }


    public boolean checkUserPassword(int IdUser, String password) {

        User user = getUser(IdUser);
        String passwordUser = encryptSHA1(password);
        return user.getPassword().equals(passwordUser);
    }

    public boolean checkUserEmail(int IdUser, String email) {
        User user = getUser(IdUser);
        return user.getEmail().equals(email);
    }


    public boolean checkLogedUser(int IdUser) {

        User user = getUser(IdUser);
        if (user.isActive()) return true;
        return false;
    }

    public String encryptSHA1(String password) {
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
