package pl.competencyproject.model.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.entities.Dictionary_Words;

import java.util.List;

public class ManageDictionaryWords extends GeneralManager {

    private static ManageDictionaryWords instance;


    private ManageDictionaryWords() {
        super();
    }

    public static ManageDictionaryWords getInstance() {
        if (instance == null) {
            synchronized (ManageDictionaryWords.class) {
                if (instance == null) {
                    instance = new ManageDictionaryWords();
                }
            }
        }
        return instance;
    }

    public static int insertDictionaryWords(int idLevel, int idFamilie, int idWordENG, int idWordPL) {

        Transaction tx = null;
        int idDictionary = -1;
        if (SessionLogon.IdLoggedUser > 0) {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
            try {
                tx = session.beginTransaction();
                Dictionary_Words dictionary = new Dictionary_Words(idLevel, idFamilie, idWordENG, idWordPL);
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

    public List<Dictionary_Words> getDictionaryByLevel(int idLevel) {

        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        NativeQuery query = session.createSQLQuery("SELECT * FROM DICTIONARY_WORDS WHERE idLevel =  :idLevel");
        query.addEntity(Dictionary_Words.class);
        query.setParameter("idLevel", idLevel);
        List result = query.list();
        return result;
    }

    public List<Dictionary_Words> getDictionaryByFamilie(int idFamilie) {

        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        NativeQuery query = session.createSQLQuery("SELECT * FROM DICTIONARY_WORDS WHERE idFamilie =  :idFamilie");
        query.addEntity(Dictionary_Words.class);
        query.setParameter("idFamilie", idFamilie);
        List result = query.list();
        return result;
    }
}
