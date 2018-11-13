package pl.competencyproject.model.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.competencyproject.model.connection.SessionFactoryConfig;
import pl.competencyproject.model.entities.Level;
import pl.competencyproject.model.entities.Stat;

public class ManageLevels {

    private static ManageLevels instance;
    private static SessionFactory SessionFactory;
    private Session session;

    private ManageLevels(){
        SessionFactory = SessionFactoryConfig.getSessionFactory();
        session = SessionFactory.openSession();
    }

    public static ManageLevels getInstance(){
        if (instance == null) {
            synchronized (ManageLevels.class) {
                if (instance == null) {
                    instance = new ManageLevels();
                }
            }
        }
        return instance;
    }

    public  int createLevel(String levelName, String nameCategorie){
        Transaction tx = null;
        Integer idLevel = -1;
        if (SessionLogon.IdLoggedUser > 0) {
            if(!session.isOpen()){session = SessionFactory.openSession(); }
            try {
                tx = session.beginTransaction();
                Level level = new Level(levelName,nameCategorie);
                idLevel = (Integer) session.save(level);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return idLevel;
    }

}
