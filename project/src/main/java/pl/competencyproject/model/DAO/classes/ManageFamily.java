package pl.competencyproject.model.DAO.classes;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.DAO.SessionLogon;
import pl.competencyproject.model.DAO.interfaces.ManagingFamily;
import pl.competencyproject.model.entities.Family;

import java.util.List;

public class ManageFamily extends GeneralManager implements ManagingFamily {


    private static ManageFamily instance;
    public static final String TABLE = "FAMILIES";

    private ManageFamily(boolean test) {
        super(test);
    }

    public static ManageFamily getInstance() {
        if (instance == null) {
            synchronized (ManageFamily.class) {
                if (instance == null) {
                    instance = new ManageFamily(false);
                }
            }
        }
        return instance;
    }

    public static ManageFamily getTestInstance() {
        if (instance == null) {
            synchronized (ManageFamily.class) {
                if (instance == null) {
                    instance = new ManageFamily(true);
                }
            }
        }
        return instance;
    }

    public Integer addFamily(int idLevel, String headFamily) {
        Transaction tx = null;
        int idDictionary = -1;
        // SessionLogon.IdLoggedUser = 1;  // BROBLEM Z SESSIONLOGON
        if (SessionLogon.IdLoggedUser > 0 && existFamily(idLevel, headFamily) == -1) {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
            try {
                tx = session.beginTransaction();
                Family dictionary = new Family(idLevel, headFamily);
                idDictionary = (Integer) session.save(dictionary);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return idDictionary;
    }

    public Integer existFamily(int idLevel, String headFamily) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        Family family = null;
        NativeQuery query = session.createSQLQuery("SELECT * FROM FAMILIES WHERE idLevel = :idLevel AND headFamily = :headFamily");
        query.addEntity(Family.class);
        query.setParameter("idLevel", idLevel);
        query.setParameter("headFamily", headFamily);
        List result = query.list();
        if (result.size() != 0) {
            family = (Family) result.get(0);
            return family.getIdFamily();
        }
        return -1;
    }

    public Family getFamily(int idFamily) {
        Transaction tx = null;
        Family family = null;
        try {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
            tx = session.beginTransaction();
            family = (Family) session.get(Family.class, idFamily);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return family;
    }

    public void deleteFamily(Integer idFamily) {
        if (getFamily(idFamily) != null) {
            Transaction tx = null;
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
            try {
                tx = session.beginTransaction();
                Family family = session.get(Family.class, idFamily);
                session.delete(family);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
    }
}
