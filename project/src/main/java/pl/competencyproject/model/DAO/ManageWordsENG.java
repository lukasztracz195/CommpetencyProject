package pl.competencyproject.model.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.entities.Word_ENG;

import java.util.List;

public class ManageWordsENG extends GeneralManager {

    private static ManageWordsENG instance;

    private ManageWordsENG() {
        super();
    }

    private ManageWordsENG getInstance() {
        if (instance == null) {
            synchronized (ManageWordsENG.class) {
                if (instance == null) {
                    instance = new ManageWordsENG();
                }
            }
        }
        return instance;
    }

    public int addWordENG(String strENG) {
        Transaction tx = null;
        int idWordENG = -1;

        if (this.existWordENG(strENG) == -1) {
            if (!session.isOpen()) {
                session = SessionFactory.openSession();
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

    public int existWordENG(String strENG) {
        if (!session.isOpen()) {
            session = SessionFactory.openSession();
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


}
