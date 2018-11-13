package pl.competencyproject.model.DAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.connection.SessionFactoryConfig;
import pl.competencyproject.model.entities.Stat;
import pl.competencyproject.model.entities.User;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ManageStat {

    private static ManageStat instance;
    private static org.hibernate.SessionFactory SessionFactory;
    private Session session;

    private ManageStat() {
        SessionFactory = SessionFactoryConfig.getSessionFactory();
        session = SessionFactory.openSession();
    }

    public static ManageStat getInstance() {
        if (instance == null) {
            synchronized (ManageFamilie.class) {
                if (instance == null) {
                    instance = new ManageStat();
                }
            }
        }
        return instance;
    }
     private static SessionFactory factory = SessionFactoryConfig.getSessionFactory();

    /* CREATE STAT*/
    public static int addStat(int IdLevel, double valueProgress) {
        Transaction tx = null;
        Integer idStat = -1;
        if (SessionLogon.IdLoggedUser > 0) {
            Session session = factory.openSession();
            try {
                tx = session.beginTransaction();
                Stat stat = new Stat(SessionLogon.IdLoggedUser, IdLevel, valueProgress);
                idStat = (Integer) session.save(stat);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return idStat;
    }

    /* GET STAT */
    public static Stat getStat(int IdStat) {
        Stat stat = null;
        Transaction tx = null;
        Session session = (Session) SessionFactoryConfig.getSessionFactory();
        if (IdStat > 0) {
            tx = session.beginTransaction();
            stat = session.get(Stat.class, IdStat);
            tx.commit();
        }
        return stat;
    }

    /* UPDATE STAT */
    public static void updateStat(int IdStat, int valueProgress){
        Stat stat = getStat(IdStat);
        Date now = new Date();
        long beetwenDays = beetwenDays(stat.getDateInput(),now);

    }
    /*  DELETE STAT */
    public  void deleteStat(int IdStat) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Stat stat = (Stat) session.get(Stat.class, IdStat);
            session.delete(stat);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static long beetwenDays(Date d1, Date d2){
        long diffInMillies = Math.abs(d2.getTime() - d1.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff;
    }
}
