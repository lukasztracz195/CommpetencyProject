package pl.competencyproject.model.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.connection.SessionFactoryConfig;
import pl.competencyproject.model.entities.Stat;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ManageStat extends GeneralManager {

    private static ManageStat instance;

    private ManageStat(boolean test) {
        super(test);
    }
    public static final String TABLE = "STATS";
    public static ManageStat getInstance() {
        if (instance == null) {
            synchronized (ManageStat.class) {
                if (instance == null) {
                    instance = new ManageStat(false);
                }
            }
        }
        return instance;
    }

    public static ManageStat getTestInstance() {
        if (instance == null) {
            synchronized (ManageStat.class) {
                if (instance == null) {
                    instance = new ManageStat(true);
                }
            }
        }
        return instance;
    }

    public  int addStat(int IdLevel, double valueProgress) {
        Transaction tx = null;
        Integer idStat = -1;
        if (SessionLogon.IdLoggedUser > 0) {
            Session session = sessionFactory.openSession();
            try {
                tx = session.beginTransaction();
                Stat stat = new Stat(SessionLogon.IdLoggedUser, IdLevel, valueProgress);
                System.out.println(stat.toString());
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
    public  Stat getStat(int IdStat) {
        Stat stat = null;
        Transaction tx = null;
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        if (IdStat > 0) {
            tx = session.beginTransaction();
            stat = session.get(Stat.class, IdStat);
            tx.commit();
        }
        return stat;
    }

    /* UPDATE STAT */
    private  void updateStat(int IdStat, int valueProgress) {
        Stat stat = getStat(IdStat);
        Date now = new Date();
        long beetwenDays = beetwenDays(stat.getDateInput(), now);


    }

    /*  DELETE STAT */
    public void deleteStat(int IdStat) {
        Transaction tx = null;
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
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

    public int existStat(int idStat) throws HibernateException {

        Stat stat = null;
        int id = -1;
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        NativeQuery query = session.createSQLQuery("SELECT * FROM USERS WHERE idStat =  :idStat");
        query.addEntity(Stat.class);
        query.setParameter("idStat", idStat);
        List result = query.list();
        if (result.size() != 0) {
            stat = (Stat) result.get(0);
        }
        if (stat != null) {
            id = stat.getIdUser();
        }
        return id;

    }

    private  long beetwenDays(Date d1, Date d2) {
        long diffInMillies = Math.abs(d2.getTime() - d1.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff;
    }
}
