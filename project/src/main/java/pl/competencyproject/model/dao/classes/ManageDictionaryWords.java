package pl.competencyproject.model.dao.classes;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.dao.SessionLogon;
import pl.competencyproject.model.dao.interfaces.ManagingDictionaryWords;
import pl.competencyproject.model.entities.Dictionary_Word;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.util.List;

public class ManageDictionaryWords extends GeneralManager implements ManagingDictionaryWords {

    private static ManageDictionaryWords instance;
    public static final String TABLE = "DICTIONARY_WORDS";

    private ManageDictionaryWords(TypeOfUsedDatabase type) {
        super(type);
    }

    public static ManageDictionaryWords getInstance(TypeOfUsedDatabase type) {
        if (instance == null) {
            synchronized (ManageDictionaryWords.class) {
                if (instance == null) {
                    instance = new ManageDictionaryWords(type);
                }
            }
        }
        return instance;
    }

    public Integer insertDictionaryWords(Integer idLevel, Integer idFamily, Integer idWordENG, Integer idWordPL) {

        Transaction tx = null;
        Integer idDictionary = -1;
        if (SessionLogon.IdLoggedUser > 0) {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
            try {
                tx = session.beginTransaction();
                Dictionary_Word dictionary = new Dictionary_Word(idLevel, idFamily, idWordENG, idWordPL);
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

    public Integer insertDictionaryWordswithoutFamilie(Integer idLevel, Integer idWordENG, Integer idWordPL) {

        Transaction tx = null;
        Integer idDictionary = -1;
        if (SessionLogon.IdLoggedUser > 0) {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
            try {
                tx = session.beginTransaction();
                Dictionary_Word dictionary = new Dictionary_Word(idLevel, null, idWordENG, idWordPL);
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

    /*
        public Integer insertDictionaryWordswithoutLevel(Integer idFamily, Integer idWordENG, Integer idWordPL) {

            Transaction tx = null;
            int idDictionary = -1;
            if (SessionLogon.IdLoggedUser > 0) {
                if (!session.isOpen()) {
                    session = sessionFactory.openSession();
                }
                try {
                    tx = session.beginTransaction();
                    Dictionary_Word dictionary = new Dictionary_Word(null, idFamily, idWordENG, idWordPL);
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
    */
    public List<Dictionary_Word> getDictionaryByLevel(Integer idLevel) {

        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        NativeQuery query = session.createSQLQuery("SELECT * FROM DICTIONARY_WORDS WHERE idLevel =  :idLevel");
        query.addEntity(Dictionary_Word.class);
        query.setParameter("idLevel", idLevel);
        List result = query.list();
        return result;
    }

    public List<Dictionary_Word> getDictionaryByFamilie(Integer idFamily) {

        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        NativeQuery query = session.createSQLQuery("SELECT * FROM DICTIONARY_WORDS WHERE idFamily =  :idFamily");
        query.addEntity(Dictionary_Word.class);
        query.setParameter("idFamily", idFamily);
        List result = query.list();
        /*
        Map<Integer, Dictionary_Word> resultMap = new HashMap<>();
        for(int i=0;i<result.size();i++){
            Dictionary_Word value = result.get(i);
            resultMap.put(i,value);
        }
        */
        return result;
    }

    public Integer existDictionaryWords(Integer idLevel, Integer idFamily, Integer idWordENG, Integer idWordPL) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        NativeQuery query = session.createSQLQuery(
                "SELECT * FROM DICTIONARY_WORDS WHERE idLevel =  :idLevel AND idFamily = :idFamily AND idWordENG = :idWordENG AND idWordPL + :idWordPL");
        query.addEntity(Dictionary_Word.class);
        query.setParameter("idLevel", idLevel);
        query.setParameter("idFamily", idFamily);
        query.setParameter("idWordENG", idWordENG);
        query.setParameter("idWordPL", idWordPL);
        List result = query.list();
        if (!result.isEmpty()) {
            System.out.println("Nie pusta");
            Dictionary_Word dic = (Dictionary_Word) result.get(0);
            return dic.getIdDictionaryWords();
        }
        return -1;
    }

    public Integer existDictionaryWordsWithoutFamilie(Integer idLevel, Integer idWordENG, Integer idWordPL) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        NativeQuery query = session.createSQLQuery(
                "SELECT * FROM DICTIONARY_WORDS WHERE idLevel =  :idLevel AND idFamily IS NULL AND idWordENG = :idWordENG AND idWordPL + :idWordPL");
        query.addEntity(Dictionary_Word.class);
        query.setParameter("idLevel", idLevel);
        query.setParameter("idWordENG", idWordENG);
        query.setParameter("idWordPL", idWordPL);
        List result = query.list();
        if (!result.isEmpty()) {
            Dictionary_Word dic = (Dictionary_Word) result.get(0);
            return dic.getIdDictionaryWords();
        }
        return -1;
    }
}
