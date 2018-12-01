package pl.competencyproject.model.DAO.classes;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.DAO.SessionLogon;
import pl.competencyproject.model.DAO.interfaces.ManagingStats;
import pl.competencyproject.model.entities.Stat;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ManageStats extends GeneralManager implements ManagingStats {

    private static ManageStats instance;

    private ManageStats(boolean test) {
        super(test);
    }

    public static final String TABLE = "STATS";

    public static ManageStats getInstance() {
        if (instance == null) {
            synchronized (ManageStats.class) {
                if (instance == null) {
                    instance = new ManageStats(false);
                }
            }
        }
        return instance;
    }

    public static ManageStats getTestInstance() {
        if (instance == null) {
            synchronized (ManageStats.class) {
                if (instance == null) {
                    instance = new ManageStats(true);
                }
            }
        }
        return instance;
    }

    public Integer addStat(int IdLevel, double valueProgress) {
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
    public Stat getStat(int IdStat) {
        Stat stat = null;
        Transaction tx;
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
    public void updateStat(int IdStat, double valueProgress) {

        Transaction tx = null;
        try {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
            tx = session.beginTransaction();
            Stat stat = session.get(Stat.class, IdStat);
            Date now = new Date();
            long beetwenDays = beetwenDays(stat.getDateInput(), now);
            double newValueProgres;
            if(beetwenDays != 0) {
                 newValueProgres = (stat.getValueProgress() / beetwenDays) + valueProgress/2;
            }else {
                 newValueProgres = (stat.getValueProgress() / valueProgress) + (valueProgress*2);
            }
            stat.setDateInput(now);
            stat.setValueProgress(newValueProgres);
            session.update(stat);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

    }




    /*  DELETE STAT */
    public void deleteStat(int idStat) {
        Transaction tx = null;
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        try {
            tx = session.beginTransaction();
            Stat stat = (Stat) session.get(Stat.class, idStat);
            session.delete(stat);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Integer existStat(int idStat) throws HibernateException {

        Stat stat = null;
        int id = -1;
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        NativeQuery query = session.createSQLQuery("SELECT * FROM STATS WHERE idStat = :idStat");
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

    private long beetwenDays(Date d1, Date d2) {
        long diffInMillies = Math.abs(d2.getTime() - d1.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff;
    }
}
