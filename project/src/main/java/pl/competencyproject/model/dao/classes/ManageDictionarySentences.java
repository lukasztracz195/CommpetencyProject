package pl.competencyproject.model.dao.classes;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import pl.competencyproject.model.dao.SessionLogon;
import pl.competencyproject.model.dao.interfaces.ManagingDictionarySentences;
import pl.competencyproject.model.entities.Dictionary_Sentence;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;
import pl.competencyproject.model.pollingMechanizm.PairOfCSV;

import java.util.ArrayList;
import java.util.List;

public class ManageDictionarySentences extends GeneralManager implements ManagingDictionarySentences {

    public static final String TABLE = "DICTIONARY_SENTENCES";

    public ManageDictionarySentences(TypeOfUsedDatabase type) {
        super(type);
    }


    public synchronized Integer insertDictionarySentece(int idLevel, String sentencesENG, String sentencesPL) {
        Transaction tx = null;
        int idDictionary = -1;
        if (SessionLogon.IdLoggedUser > 0 && existDictionarySentences(idLevel, sentencesENG, sentencesPL) == -1) {
            reset();
            try {
                tx = session.beginTransaction();
                Dictionary_Sentence dictionary = new Dictionary_Sentence(idLevel, sentencesENG, sentencesPL);
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

    public synchronized Integer existDictionarySentences(int idLevel, String sentencesENG, String sentencesPL) {
        reset();
        NativeQuery query = session.createSQLQuery("SELECT * FROM DICTIONARY_SENTENCES WHERE idLevel = :idLevel AND sentencesENG = :sentencesENG AND sentencesPL = " +
                ":sentencesPL");
        query.addEntity(Dictionary_Sentence.class);
        query.setParameter("idLevel", idLevel);
        query.setParameter("sentencesENG", sentencesENG);
        query.setParameter("sentencesPL", sentencesPL);
        List result = query.list();
        if (result.size() != 0) {
            Dictionary_Sentence dicSentency = (Dictionary_Sentence) result.get(0);
            return dicSentency.getIdDictionary();
        }
        return -1;
    }

    public synchronized Dictionary_Sentence getDictionary_Sentences(int idDictionary) {
        Transaction tx = null;
        Dictionary_Sentence dicSentency = null;
        try {
            reset();
            tx = session.beginTransaction();
            dicSentency = session.get(Dictionary_Sentence.class, idDictionary);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return dicSentency;
    }

    public synchronized List<PairOfCSV> getListbyLevel(Integer idLevel) {
        reset();
        NativeQuery query = session.createSQLQuery("SELECT * FROM DICTIONARY_SENTENCES WHERE idLevel =  :idLevel");
        query.addEntity(Dictionary_Sentence.class);
        query.setParameter("idLevel", idLevel);
        List<Dictionary_Sentence> list = query.list();
        List<PairOfCSV> listOfPair = new ArrayList<>();
        for (Dictionary_Sentence row : list) {
            listOfPair.add(new PairOfCSV( row.getSentencesENG(),  row.getSentencesPL()));
        }
        return listOfPair;
    }

    public synchronized Integer countSentencys(Integer idLevel){
        reset();
        Query query = session.createSQLQuery("SELECT COUNT(idDictionary) AS suma FROM DICTIONARY_SENTENCES WHERE idLevel = :idLevel");
        query.setParameter("idLevel", idLevel);
        ((NativeQuery) query).addScalar( "suma", StandardBasicTypes.INTEGER);
        return (Integer) query.getResultList().get(0);
    }
}
