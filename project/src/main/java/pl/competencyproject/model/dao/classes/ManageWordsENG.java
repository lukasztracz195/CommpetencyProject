package pl.competencyproject.model.dao.classes;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.dao.interfaces.ManagingWordsENG;
import pl.competencyproject.model.entities.Word_ENG;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.util.List;

public class ManageWordsENG extends GeneralManager implements ManagingWordsENG {

    private static ManageWordsENG instance;
    public static final String TABLE = "WORDS_ENG";

    private ManageWordsENG(TypeOfUsedDatabase type) {
        super(type);
    }

    public static void delete(){
        instance = null;
    }

    public static ManageWordsENG getInstance(TypeOfUsedDatabase type) {
        if (instance == null) {
            synchronized (ManageWordsENG.class) {
                if (instance == null) {
                    instance = new ManageWordsENG(type);
                }
            }
        }
        return instance;
    }

    public Integer addWordENG(String strENG) {
        Transaction tx = null;
        int idWordENG = -1;

        if (this.existWordENG(strENG) == -1) {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
            try {
                tx = session.beginTransaction();
                Word_ENG wordENG = new Word_ENG(strENG);
                idWordENG = (Integer) session.save(wordENG);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return idWordENG;
    }

    public Integer existWordENG(String strENG) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        NativeQuery query = session.createSQLQuery("SELECT * FROM WORDS_ENG WHERE wordENG =  :wordENG");
        query.addEntity(Word_ENG.class);
        query.setParameter("wordENG", strENG);
        List result = query.list();
        if (result.size() != 0) {
            Word_ENG word = (Word_ENG) result.get(0);
            return word.getIdWordENG();
        }
        return -1;
    }

    public void deleteWordENG(Integer idWordENG) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Word_ENG user = (Word_ENG) session.get(Word_ENG.class, idWordENG);
            session.delete(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Word_ENG getWordENG(int idWordENG) {
        Transaction tx = null;
        Word_ENG word = null;
        try {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
            tx = session.beginTransaction();
            word = (Word_ENG) session.get(Word_ENG.class, idWordENG);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return word;
    }


}
