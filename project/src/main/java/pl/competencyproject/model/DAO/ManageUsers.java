package pl.competencyproject.model.DAO;

import org.hibernate.*;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.connection.SessionFactoryConfig;
import pl.competencyproject.model.entities.User;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

public class ManageUsers {

    private static SessionFactory factory = SessionFactoryConfig.getSessionFactory();

    /* Method to CREATE an user in the database */
    public static Integer addUser(String email, String password) {
        org.hibernate.Session session = factory.openSession();
        Transaction tx = null;
        Integer idUser = null;
        try {
            tx = session.beginTransaction();
            String passwordEncrypted = encryptMD5(password);
            User user = new User(email, passwordEncrypted, false);
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
    public static void updatePasswordUser(Integer UserID, String password ){
            Transaction tx = null;
        try (Session session = factory.openSession()) {

            tx = session.beginTransaction();
            User user = session.get(User.class, UserID);
            String passwordEncrypted = encryptMD5(password);
            user.setPassword(passwordEncrypted);
            session.update(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    /* Method to UPDATE active status for an user */
    public static void updateActiveUser(Integer UserID, boolean active ){
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
    public static void deleteUser(Integer UserID){
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

    public static int existUser(String email)throws HibernateException{
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

    public static boolean checkUserPassword(int IdUser, String password){
        Session session = factory.openSession();
        NativeQuery query = session.createSQLQuery("SELECT * FROM USERS WHERE idUser =  :idUser");
        query.addEntity(User.class);
        query.setParameter("idUser",IdUser).getFirstResult();
        List result = query.list();
        User user = (User) result.get(0);
        String passwordUser = encryptMD5(password);
        return user.getPassword().equals(passwordUser);
    }

    public static boolean checkLogedUser(int IdUser){
        Session session = factory.openSession();
        NativeQuery query = session.createSQLQuery("SELECT * FROM USERS WHERE idUser =  :idUser");
        query.addEntity(User.class);
        query.setParameter("idUser",IdUser).getFirstResult();
        List result = query.list();
        User user = (User) result.get(0);
        if(user.isActive()) return true;
        return false;
    }

    private static String encryptMD5(String password)   {
        String hash = "35454B055CC325EA1AF2126E27707052";
        MessageDigest md = null;
        String myHash = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
             myHash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return myHash;

    }

    public static String genereateHash(){
        StringBuilder sb = new StringBuilder();
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        for(int i=0;i<32;i++){
            if(random.nextInt(2) % 2 == 0)sb.append(String.valueOf(random.nextInt(9)));
            else sb.append(base.charAt(random.nextInt(25)));
        }
        return sb.toString();
    }
}
