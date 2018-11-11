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

    public int insertFamilie(int idLevel, String headFamilie){
        Transaction tx = null;
        int idDictionary = -1;
        if (SessionLogon.IdLoggedUser > 0 && !existFamilie(idLevel,headFamilie)) {
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

    public boolean existFamilie(int idLevel, String headFamilie){
        if (!session.isOpen()) {
            session = SessionFactory.openSession();
        }
        NativeQuery query = session.createSQLQuery("SELECT * FROM FAMILIE WHERE idLevel = :idLevel AND headFamilie = : headFamilie");
        query.setParameter("idLevel", idLevel);
        query.setParameter("sentencesENG", headFamilie);
        List resutl = query.list();
        if (resutl.size() != 0)
            return true;
        return false;
    }
}
