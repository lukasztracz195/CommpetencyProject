package pl.competencyproject.model.dao.classes;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import pl.competencyproject.model.dao.SessionLogon;
import pl.competencyproject.model.dao.interfaces.ManagingDictionaryWords;
import pl.competencyproject.model.entities.Dictionary_Word;
import pl.competencyproject.model.enums.TypeOfDictionaryLanguage;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;
import pl.competencyproject.model.pollingMechanizm.PairOfCSV;

import java.util.ArrayList;
import java.util.List;

public class ManageDictionaryWords extends GeneralManager implements ManagingDictionaryWords {

    public static final String TABLE = "DICTIONARY_WORDS";

    public ManageDictionaryWords(TypeOfUsedDatabase type) {
        super(type);
    }


    public synchronized Integer insertDictionaryWords(Integer idLevel, Integer idFamily, Integer idWordENG, Integer idWordPL) {

        Transaction tx = null;
        Integer idDictionary = -1;
        if (SessionLogon.IdLoggedUser > 0) {
            reset();
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

    public synchronized Integer insertDictionaryWordswithoutFamilie(Integer idLevel, Integer idWordENG, Integer idWordPL) {

        Transaction tx = null;
        Integer idDictionary = -1;
        if (SessionLogon.IdLoggedUser > 0) {
            reset();
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

    public synchronized List<Dictionary_Word> getDictionaryByLevel(Integer idLevel) {

        reset();
        NativeQuery query = session.createSQLQuery("SELECT * FROM DICTIONARY_WORDS WHERE idLevel = :idLevel");
        query.addEntity(Dictionary_Word.class);
        query.setParameter("idLevel", idLevel);
        List result = query.list();
        return result;
    }

    public synchronized List<Dictionary_Word> getDictionaryByFamilie(Integer idFamily) {

        reset();
        NativeQuery query = session.createSQLQuery("SELECT * FROM DICTIONARY_WORDS WHERE idFamily = :idFamily");
        query.addEntity(Dictionary_Word.class);
        query.setParameter("idFamily", idFamily);
        List result = query.list();
        return result;
    }

    public synchronized Integer existDictionaryWords(Integer idLevel, Integer idFamily, Integer idWordENG, Integer idWordPL) {
        reset();
        NativeQuery query = session.createSQLQuery(
                "SELECT * FROM DICTIONARY_WORDS WHERE  idLevel =  :idLevel AND idFamily = :idFamily AND idWordENG = :idWordENG AND idWordPL = :idWordPL");
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

    public synchronized Integer existDictionaryWordsWithoutFamilie(Integer idLevel, Integer idWENG, Integer idWPL) {
        reset();
        NativeQuery query = session.createSQLQuery(
                "SELECT * FROM DICTIONARY_WORDS WHERE idLevel = :ID_LEVEL AND idFamily IS NULL AND idWordENG = :ID_WENG AND idWordPL = :ID_WPL");
        query.setParameter("ID_WENG", idWENG);
        query.setParameter("ID_WPL", idWPL);
        query.setParameter("ID_LEVEL", idLevel);
        query.addEntity(Dictionary_Word.class);
        List result = query.list();
        if (!result.isEmpty()) {
            Dictionary_Word dic = (Dictionary_Word) result.get(0);
            return dic.getIdDictionaryWords();
        }
        return -1;
    }

    public synchronized void setFamilyinExistedDictionaryWord(Integer idDictionaryWord, Integer idFamily) {
        reset();
        NativeQuery query = session.createNativeQuery("UPDATE DICTIONARY_WORDS SET idFamily = :ID_Family WHERE idDictionaryWords = :ID_Dictionary");
        query.setParameter("ID_Family", idFamily);
        query.setParameter("ID_Dictionary", idDictionaryWord);
        int result = query.executeUpdate();
    }

    public synchronized Integer countDictionaryMap(Integer idLevel) {
        reset();
        Query query = session.createSQLQuery("SELECT COUNT(idDictionaryWords) FROM DICTIONARY_WORDS WHERE idLevel = :idLevel");
        query.setParameter("idLevel", idLevel);
        return Integer.valueOf(query.getSingleResult().toString());
    }

    public synchronized Integer countFamilys(Integer idLevel) {
        reset();
        Query query = session.createSQLQuery("SELECT COUNT(idDictionaryWords) FROM DICTIONARY_WORDS WHERE idLevel = :idLevel AND idFamily IS NOT NULL");
        query.setParameter("idLevel", idLevel);
        return Integer.valueOf(query.getSingleResult().toString());
    }

    public synchronized List<PairOfCSV> getDictionary(Integer idLevel, TypeOfDictionaryLanguage type) {
        reset();
        Query query = null;
        if (type.equals(TypeOfDictionaryLanguage.ENGtoPL)) {
            query = session.createSQLQuery(" SELECT WE.wordENG,WP.wordPL FROM DICTIONARY_WORDS AS DW LEFT JOIN WORDS_PL AS WP ON DW.idWordPL = WP.idWordPL JOIN WORDS_ENG AS WE ON DW.idWordENG = WE.idWordENG WHERE DW.idLevel = :idLevel ORDER BY WE.wordENG ");
        } else {
            query = session.createSQLQuery("SELECT WP.wordPL, WE.wordENG FROM DICTIONARY_WORDS AS DW LEFT JOIN WORDS_PL AS WP ON DW.idWordPL = WP.idWordPL JOIN WORDS_ENG AS WE ON DW.idWordENG = WE.idWordENG WHERE DW.idLevel = :idLevel ORDER BY WP.wordPL ");
        }
        query.setParameter("idLevel", idLevel);
        List<Object[]> list = query.list();
        List<PairOfCSV> listOfPair = new ArrayList<>();
        for (Object[] row : list) {
            listOfPair.add(new PairOfCSV((String) row[0], (String) row[1]));
        }
        return listOfPair;
    }

    public synchronized List<PairOfCSV> getDictionaryOfFamily(Integer idFamily, TypeOfDictionaryLanguage type) {
        reset();
        Query query = null;
        if (type.equals(TypeOfDictionaryLanguage.ENGtoPL)) {
            query = session.createSQLQuery(" SELECT WE.wordENG,WP.wordPL FROM DICTIONARY_WORDS AS DW LEFT JOIN WORDS_PL AS WP ON DW.idWordPL = WP.idWordPL JOIN WORDS_ENG AS WE ON DW.idWordENG = WE.idWordENG WHERE DW.idFamily = :idFamily ORDER BY WE.wordENG ");
        } else {
            query = session.createSQLQuery("SELECT WP.wordPL, WE.wordENG FROM DICTIONARY_WORDS AS DW LEFT JOIN WORDS_PL AS WP ON DW.idWordPL = WP.idWordPL JOIN WORDS_ENG AS WE ON DW.idWordENG = WE.idWordENG WHERE DW.idFamily = :idFamily ORDER BY WP.wordPL ");
        }
        query.setParameter("idFamily", idFamily);
        List<Object[]> list = query.list();
        List<PairOfCSV> listOfPair = new ArrayList<>();
        for (Object[] row : list) {
            listOfPair.add(new PairOfCSV((String) row[0], (String) row[1]));
        }
        return listOfPair;


    }
}
