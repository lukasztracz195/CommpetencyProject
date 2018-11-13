package pl.competencyproject.model.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.connection.SessionFactoryConfig;
import pl.competencyproject.model.entities.Familie;

import java.util.List;

public class ManageFamilie {

    private static ManageFamilie instance;
    private static org.hibernate.SessionFactory SessionFactory;
    private Session session;

    private ManageFamilie() {
        SessionFactory = SessionFactoryConfig.getSessionFactory();
        session = SessionFactory.openSession();
    }

    public static ManageFamilie getInstance() {
        if (instance == null) {
            synchronized (ManageFamilie.class) {
                if (instance == null) {
                    instance = new ManageFamilie();
                }
            }
        }
        return instance;
    }

    public int addFamilie(int idLevel, String headFamilie) {
        Transaction tx = null;
        int idDictionary = -1;
        if (SessionLogon.IdLoggedUser > 0 && existFamilie(idLevel, headFamilie) == -1) {
            if (!session.isOpen()) {
                session = SessionFactory.openSession();
            }
            try {
                tx = session.beginTransaction();
                Familie dictionary = new Familie(idLevel, headFamilie);
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

    public int existFamilie(int idLevel, String headFamilie) {
        if (!session.isOpen()) {
            session = SessionFactory.openSession();
        }
        Familie familie = null;
        NativeQuery query = session.createSQLQuery("SELECT * FROM FAMILIE WHERE idLevel = :idLevel AND headFamilie = : headFamilie");
        query.addEntity(Familie.class);
        query.setParameter("idLevel", idLevel);
        query.setParameter("sentencesENG", headFamilie);
        List result = query.list();
        if (result.size() != 0) {
            familie = (Familie) result.get(0);
            return familie.getIdFamilie();
        }
        return -1;
    }

    public Familie getFamili(int idFamili) {
        Transaction tx = null;
        Familie familie = null;
        try {
            if (!session.isOpen()) {
                session = SessionFactory.openSession();
            }
            tx = session.beginTransaction();
            familie = (Familie) session.get(Familie.class, idFamili);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return familie;
    }

    public  void deleteFamilie(Integer idFamilie) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Familie familie = (Familie) session.get(Familie.class, idFamilie);
            session.delete(familie);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
