package pl.competencyproject.model.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.jboss.util.Null;
import pl.competencyproject.model.entities.Dictionary_Words;

import java.util.List;

public class ManageDictionaryWords extends GeneralManager {

    private static ManageDictionaryWords instance;
    public static final String TABLE = "DICTIONARY_WORDS";

    private ManageDictionaryWords(boolean test) {
        super(test);
    }

    public static ManageDictionaryWords getInstance() {
        if (instance == null) {
            synchronized (ManageDictionaryWords.class) {
                if (instance == null) {
                    instance = new ManageDictionaryWords(false);
                }
            }
        }
        return instance;
    }

    public static ManageDictionaryWords getTestInstance() {
        if (instance == null) {
            synchronized (ManageDictionaryWords.class) {
                if (instance == null) {
                    instance = new ManageDictionaryWords(true);
                }
            }
        }
        return instance;
    }

    public  Integer insertDictionaryWordswithoutFamilie(Integer idLevel, Integer idWordENG, Integer idWordPL) {

        Transaction tx = null;
        Integer idDictionary = -1;
        if (SessionLogon.IdLoggedUser > 0) {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
            try {
                tx = session.beginTransaction();
                Dictionary_Words dictionary = new Dictionary_Words(idLevel, null,idWordENG, idWordPL);
                System.out.println(dictionary.toString());
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

    public  Integer insertDictionaryWordswithoutLevel(int idFamilie, int idWordENG, int idWordPL) {

        Transaction tx = null;
        int idDictionary = -1;
        if (SessionLogon.IdLoggedUser > 0) {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
            try {
                tx = session.beginTransaction();
                Dictionary_Words dictionary = new Dictionary_Words(null,idFamilie, idWordENG, idWordPL);
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

    public Integer existDictionaryWords(Integer idLevel, Integer idFamilie, Integer idWordENG, Integer idWordPL){
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        NativeQuery query = session.createSQLQuery("SELECT * FROM DICTIONARY_WORDS WHERE idLevel =  :idLevel AND idFamilie = :idFamilie AND idWordENG = :idWordENG AND idWordPL =" +
                " " +
                ":idWordPL");
        query.addEntity(Dictionary_Words.class);
        query.setParameter("idLevel", idLevel);
        query.setParameter("idFamilie", idFamilie);
        query.setParameter("idWordENG", idWordENG);
        query.setParameter("idWordPL", idWordPL);
        List result = query.list();
        if(result.size() != 0){
            Dictionary_Words dic = (Dictionary_Words) result.get(0);
            return dic.getIdDictionaryWords();
        }
        return -1;
    }
}
