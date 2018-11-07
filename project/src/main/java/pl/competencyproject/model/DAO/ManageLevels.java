package pl.competencyproject.model.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.competencyproject.model.connection.SessionFactoryConfig;
import pl.competencyproject.model.entities.Level;
import pl.competencyproject.model.entities.Stat;

public class ManageLevels {

    public static int createLevel(String levelName, String categorie){
        Transaction tx = null;
        int idLevel = -1;
        if (SessionLogon.IdLoggedUser > 0) {
            Session session = SessionFactoryConfig.getSessionFactory().openSession();
            try {
                tx = session.beginTransaction();
                Level level = new Level(levelName,categorie);
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
